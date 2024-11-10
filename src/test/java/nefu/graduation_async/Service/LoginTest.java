package nefu.graduation_async.Service;

import lombok.extern.slf4j.Slf4j;
import nefu.graduation_async.component.JWTComponent;
import nefu.graduation_async.dto.Code;
import nefu.graduation_async.dto.Login;
import nefu.graduation_async.service.UserService;
import nefu.graduation_async.vo.RequestAttributeConstant;
import nefu.graduation_async.vo.ResultVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.util.Map;

@SpringBootTest
@Slf4j
public class LoginTest {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTComponent jwtComponent;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    void login(){
        Login login=Login.builder()
                .account("1")
                .password("1")
                .build();
        //String token = jwtComponent.encode(Map.of("uid", user.getId(), "role", user.getRole(), "department", user.getDepartment(), "group", user.getGroup()));
        userService.getUser(login.getAccount())
                    .filter(u -> passwordEncoder.matches(login.getPassword(), u.getPassword()))
                .map(ResultVO::success
                ).doOnNext(resultVO -> log.info("{}",resultVO)).block();

    }
}
