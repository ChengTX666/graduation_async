package nefu.graduation_async.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
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

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTComponent jwtComponent;
    private final ObjectMapper objectMapper;
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
}