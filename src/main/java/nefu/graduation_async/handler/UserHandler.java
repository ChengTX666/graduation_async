package nefu.graduation_async.handler;

import nefu.graduation_async.dox.User02;
import nefu.graduation_async.service.User02Service;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;



public class UserHandler {

    private final User02Service userService;

    public UserHandler(User02Service user02Service) {
        this.userService = user02Service;
    }

    // 根据id查询
    public Mono<ServerResponse> getById(ServerRequest request){
        // 获取id值
        String id = request.pathVariable("id");
        // 空值处理
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        // 调用Service方法得到数据
        Mono<User02> userMono = userService.getById(Integer.parseInt(id));
        // 把userMono进行转换返回
        return userMono.flatMap(user02 ->
                ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(userMono))
                        .switchIfEmpty(notFound)
        );
    }

    // 查询多个
    public Mono<ServerResponse> getAll(ServerRequest request){
        // 调用Service得到结果
        Flux<User02> users = userService.getAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(users, User02.class);

    }

    // 保存
    public Mono<ServerResponse> save(ServerRequest request){
        // 获取User对象
        Mono<User02> userMono = request.bodyToMono(User02.class);
        return ServerResponse.ok().build(userService.save(userMono));
    }


    public static void main(String[] args) {
        // 创建对象
        User02Service user02Service = new User02Service();
        UserHandler userHandler = new UserHandler(user02Service);
        // 创建路由
        RouterFunction<ServerResponse> route = RouterFunctions
                .route(RequestPredicates.GET("/user/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::getById)
                .andRoute(RequestPredicates.GET("/users").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::getAll);
        // 路由和handler适配
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        // 创建服务器
        HttpServer httpServer = HttpServer.create();
        httpServer.handle(adapter).bindNow();
    }
}

