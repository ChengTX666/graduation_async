package nefu.graduation_async.controller;



import lombok.RequiredArgsConstructor;
import nefu.graduation_async.component.JWTComponent;
import nefu.graduation_async.dto.Code;
import nefu.graduation_async.dto.Login;
import nefu.graduation_async.vo.ResultVO;
import nefu.graduation_async.service.UserService;
import nefu.graduation_async.vo.RequestAttributeConstant;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTComponent jwtComponent;
    @PostMapping("login")
    public Mono<ResultVO> login(@RequestBody Login login, ServerHttpResponse resp){
        return userService.getUser(login.getAccount())
                .filter(u->passwordEncoder.matches(login.getPassword(),u.getPassword()))
                .map(user -> {
                    String token=jwtComponent.encode(Map.of("uid", user.getId(), "role", user.getRole(),"department",user.getDepartment(),"group",user.getGroup()));
                    resp.getHeaders().add(RequestAttributeConstant.TOKEN,token);
                    resp.getHeaders().add(RequestAttributeConstant.ROLE,user.getRole());
                    return ResultVO.success(user);}
                ).defaultIfEmpty(ResultVO.error(Code.LOGIN_ERROR));
    }

    @GetMapping("test")
    public Mono<ResultVO> test(@RequestAttribute String depId, @RequestAttribute String role, @RequestAttribute String uid)
    {
        StringBuilder result=new StringBuilder("depId->>").append(depId).append("  ")
                .append("role->>").append(role).append("  ")
                .append("uid->>").append(uid);

        return Mono.just(ResultVO.success(result));
    }
}
