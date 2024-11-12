package nefu.graduation_async.service;

import lombok.RequiredArgsConstructor;
import nefu.graduation_async.Repository.ProcessRepository;
import nefu.graduation_async.Repository.ScoreRepository;
import nefu.graduation_async.Repository.UserRepository;
import nefu.graduation_async.dox.Process;
import nefu.graduation_async.dox.Score;
import nefu.graduation_async.dox.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final ProcessRepository processRepository;
    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;

    //添加过程
    @Transactional
    public Mono<Process> addProcess(Process process){
        return processRepository.save(process);
    }

    //删除过程
    @Transactional
    public Mono<Void> removeProcess(String pid,String did){
       return processRepository.deleteByIdAndDepId(pid,did);
    }
    //查看过程的在userService里
    //过程
    //得分

    @Transactional//添加得分 打分 前端需要传来的：学生id 过程id 老师id(Attribute里)
    public Mono<Score> addScore(Score score) {
        if (score.getId()!=null){
            return updateDetailScore(score);
        }
        return scoreRepository.save(score);
    }
    //为空时保存，不为空更新
    @Transactional
    public Mono<Score> updateDetailScore(Score score){
        if(score.getId()==null){
            return scoreRepository.save(score);
        }
        return scoreRepository.updateDetailById(score.getId(),score.getDetail());
    }
    //查看自己组学生的得分
    public Mono<List<Score>> scoreGroup(int group){
        return scoreRepository.findScoresByGroup(group).collectList();
    }
    //查看自己打的分
    public Mono<List<Score>> scoreBy(String tid){
        return scoreRepository.findScoresByTid(tid).collectList();
    }


    //得分
    //用户
    //查看专业所有学生
    public Mono<List<User>> studentList(String depId){
        return userRepository.findByDepIdAndRole(depId,User.ROLE_STUDENT).collectList();
    }

    //查看自己组的学生
    public Mono<List<User>> groupStudents(String depId,int group){
        return userRepository.findByDepIdAndRoleAndGroup(depId,User.ROLE_STUDENT,group).collectList();
    }
    //自己辅导的学生
    public Mono<List<User>> mentorStudents(String depId,String tid){
        return userRepository.mentorStudents(depId,tid).collectList();
    }

}
