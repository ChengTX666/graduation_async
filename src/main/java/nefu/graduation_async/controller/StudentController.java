package nefu.graduation_async.controller;

import lombok.RequiredArgsConstructor;
import nefu.graduation_async.service.StudentService;
import nefu.graduation_async.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/student/")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    //展示所有导师
    @GetMapping("teachers")
    public Mono<ResultVO> listTeachers(@RequestAttribute String depId){
        return studentService.ListTeacher(depId).map(ResultVO::success);
    }

}
