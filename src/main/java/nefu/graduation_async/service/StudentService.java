package nefu.graduation_async.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nefu.graduation_async.Repository.ProcessRepository;
import nefu.graduation_async.Repository.ScoreRepository;
import nefu.graduation_async.Repository.UserRepository;
import nefu.graduation_async.dox.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final UserRepository userRepository;
    private final ProcessRepository processRepository;
    private final ScoreRepository scoreRepository;


    //查看所有导师
    public Mono<List<User>> ListTeacher(String depId){
        return userRepository.findByDepIdAndRole(depId,User.ROLE_TEACHER).collectList();
    }
    //选择导师
    public Mono<Void> chooseMentor(String sid,String tid){
        return userRepository.updateStudentById(sid, tid);
    }
}
