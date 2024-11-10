package nefu.graduation_async.controller;
import lombok.RequiredArgsConstructor;
import nefu.graduation_async.vo.ResultVO;
import nefu.graduation_async.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("processes")
    public Mono<ResultVO> processList(){//@RequestAttribute String depId
        return userService.listProcess("2").
                map(ResultVO::success);
    }

    @GetMapping("users")
    public Mono<ResultVO> userList(){
        return userService.listUser("1","1")
                .map(ResultVO::success);
    }
}