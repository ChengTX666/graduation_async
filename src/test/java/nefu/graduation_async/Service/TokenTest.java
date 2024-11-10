package nefu.graduation_async.Service;

import lombok.extern.slf4j.Slf4j;
import nefu.graduation_async.component.JWTComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class TokenTest {

    @Autowired
    private JWTComponent jwtComponent;

    @Test
    void tokenTest(){
    }
}
