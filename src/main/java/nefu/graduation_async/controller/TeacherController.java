package nefu.graduation_async.controller;

import lombok.RequiredArgsConstructor;
import nefu.graduation_async.dox.Process;
import nefu.graduation_async.dox.Score;
import nefu.graduation_async.service.TeacherService;
import nefu.graduation_async.service.UserService;
import nefu.graduation_async.vo.RequestAttributeConstant;
import nefu.graduation_async.vo.ResultVO;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/teacher/")
@RequiredArgsConstructor
public class TeacherController {

    private final UserService userService;
    private final TeacherService teacherService;
    //查看专业所有学生
    @GetMapping("students")
    public Mono<ResultVO> studentList(@RequestAttribute("depId") String depId){
        return teacherService.studentList(depId).map(ResultVO::success);
    }
    //组内学生
    @GetMapping("students/group")
    public Mono<ResultVO> studentGroup(@RequestAttribute("depId")String depId,@RequestAttribute("group")int group){
        return teacherService.groupStudents(depId,group).map(ResultVO::success);
    }
    //辅导的学生
    @GetMapping("students/mentor")
    public Mono<ResultVO> studentMentor(@RequestAttribute("depId")String depId,@RequestAttribute("uid") String uid){

        return teacherService.mentorStudents(depId,uid).map(ResultVO::success);
    }

    //查看所有过程
    @GetMapping("processes")
    public Mono<ResultVO> processList(@RequestAttribute("depId") String depId){
        return userService.listProcess(depId).map(ResultVO::success);
    }
    //添加过程并返回所有过程
    @PostMapping("processes")
    public Mono<ResultVO> addProcess(Process process, @RequestAttribute("depId") String depId){
        return teacherService.addProcess(process)
                .then(userService.listProcess(depId))
                .map(ResultVO::success);
    }
    //自己打的分
    @GetMapping("scores")
    public Mono<ResultVO> ScoreBy(@RequestAttribute(RequestAttributeConstant.UID) String tid){
        return teacherService.scoreBy(tid).map(ResultVO::success);
    }

    //添加分数
    @PostMapping("scores")
    public Mono<ResultVO> addScore(Score score){
        return teacherService.addScore(score)
                .thenReturn(ResultVO.ok());
    }
    //查看组内分数
    @GetMapping("scores/group")
    public Mono<ResultVO> groupScore(@RequestAttribute("group")int group)
    {
        return teacherService.scoreGroup(group).map(ResultVO::success);
    }


}
