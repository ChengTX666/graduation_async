package nefu.graduation_async.Service;

import lombok.extern.slf4j.Slf4j;
import nefu.graduation_async.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
@Slf4j
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    void listProcess(){
//        userService.listProcess("2")
//                .flatMapMany(Flux::fromIterable)
//                .doOnNext(process->log.debug("Process ID: {}, Name: {}", process.getId(), process.getName()))
//                .then();

        userService.listProcess2("2")
                    .doOnNext(process -> {
                        // 你可以在这里添加日志或其他验证逻辑
                        System.out.println("Process: " + process.getName());
                    })
                    .blockLast();  // 阻塞直到 Flux 完成

    }
    @Test
    void listProcess2(){
        userService.listProcess("2")
                .doOnNext(
                processList -> processList.forEach(process -> log.info("Process ID: {}, Name: {}", process.getId(), process.getName()))
        ).block();
    }
    @Test
    void listProcess3(){
        userService.listProcess("2").flatMapMany(Flux::fromIterable).doOnNext(
                process -> log.info("ID:"+process.getId()+"Name:"+process.getName())
        ).blockLast();
    }
}
