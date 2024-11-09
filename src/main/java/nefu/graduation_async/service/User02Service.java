package nefu.graduation_async.service;


import java.util.HashMap;
import java.util.Map;

import nefu.graduation_async.dox.User02;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class User02Service {
    // 模拟数据库存储
    private Map<Integer, User02> map = new HashMap<>();

    public User02Service() {
        map.put(1, new User02("zhangSan"));
        map.put(2, new User02("lisi"));
        map.put(3, new User02("wangWu"));
    }

    // 根据id查询
    public Mono<User02> getById(Integer id){
        // 返回数据或空值
        return Mono.justOrEmpty(map.get(id));
    }

    // 查询多个
    public Flux<User02> getAll(){
        return Flux.fromIterable(map.values());
    }

    // 保存
    public Mono<Void> save(Mono<User02> userMono){
        return userMono.doOnNext(user02 -> {
            int id = map.size() + 1;
            map.put(id, user02);
        }).thenEmpty(Mono.empty()); // 最后置空
    }
}

