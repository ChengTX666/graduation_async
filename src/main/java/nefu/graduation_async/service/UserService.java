package nefu.graduation_async.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nefu.graduation_async.Repository.ProcessRepository;
import nefu.graduation_async.Repository.UserRepository;
import nefu.graduation_async.dox.Process;
import nefu.graduation_async.dox.User;
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


    public Mono<User> getUser(String account){
        return userRepository.findByAccount(account);
    }


    public Mono<List<Process>> listProcess(String depId) {
        return processRepository.findByDepId(depId).collectList();
    }

    public Flux<Process> listProcess2(String depId) {
        return processRepository.findByDepId(depId);  // 返回 Flux<Process>
    }
}
