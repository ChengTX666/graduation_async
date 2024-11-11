package nefu.graduation_async.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nefu.graduation_async.Repository.ProcessRepository;
import nefu.graduation_async.Repository.UserRepository;
import nefu.graduation_async.dox.Process;
import nefu.graduation_async.dox.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final ProcessRepository processRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    //登录时拿user
    public Mono<User> getUser(String account){
        return userRepository.findByAccount(account);
    }

    //根据depId和role查看user
    public Mono<List<User>> listUser(String depId,String role){
        return userRepository.findByDepIdAndRole(depId,role).collectList();
    }

    //查看自己部门的过程
    public Mono<List<Process>> listProcess(String depId) {
        return processRepository.findByDepId(depId).collectList();
    }
    public Flux<Process> listProcess2(String depId) {
        return processRepository.findByDepId(depId);  // 返回 Flux<Process>
    }

    //修改密码
    public Mono<Void> resetPassword(String uid,String newPassword){
        return userRepository.updatePasswordById(uid,passwordEncoder.encode(newPassword)).then();
    }
}
