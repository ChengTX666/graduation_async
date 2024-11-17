package nefu.graduation_async.service;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nefu.graduation_async.Repository.DepartmentRepository;
import nefu.graduation_async.Repository.ProcessRepository;
import nefu.graduation_async.Repository.UserRepository;
import nefu.graduation_async.dox.Department;
import nefu.graduation_async.dox.Process;
import nefu.graduation_async.dox.User;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final ProcessRepository processRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedissonClient redissonClient;


//    @PostConstruct
//    void init(){
//        departmentRepository.findAll()
//                .map(Department::getDepId)
//                .doOnNext(
//                depId->{
//                    redissonClient.getBucket("process:"+depId,StringCodec.INSTANCE)
//                            .set(processRepository.findByDepId(depId).collectList());
//                }
//        ).subscribe();
//    }
//    @PostConstruct
//    void init() {
//        departmentRepository.findAll()
//                .map(Department::getDepId)
//                .flatMap(this::cacheProcesses)
//                .subscribe();
//    }
@PostConstruct
void init() {
    departmentRepository.findAll()
            .map(Department::getDepId)
            .flatMap(depId-> processRepository.findByDepId(depId)
                    .collectList()
                    .doOnNext(processList ->
                        redissonClient.getBucket("processes:" + depId, StringCodec.INSTANCE).set(Collections.singletonList(processList))
                    )
            ).subscribe();
}

//    private Mono<Void> cacheProcesses(String depId) {
//        return processRepository.findByDepId(depId)
//                .collectList()
//                .doOnNext(processList -> {
//                    RBucket<List<Object>> bucket = redissonClient.getBucket("process:" + depId, StringCodec.INSTANCE);
//                    bucket.set(Collections.singletonList(processList));
//                }).then();
//    }


    //登录时拿user
    public Mono<User> getUser(String account){
        return userRepository.findByAccount(account);
    }

    //根据depId和role查看user
    public Mono<List<User>> listUser(String depId,String role){
        return userRepository.findByDepIdAndRole(depId,role).collectList();
    }

    //查看自己部门的过程
//    public Mono<List<Process>> listProcess(String depId) {
//        RBucket<List<Process>> bucket = redissonClient
//                .getBucket("processes:" + depId, new TypedJsonJacksonCodec(new TypeReference<List<Process>>() {}));
//        return Mono.just(bucket.get())
//                .onErrorResume(throwable -> Mono.just(Collections.emptyList()));
//    }
    public Mono<Object> listProcess(String depId) {

      RBucket<List<Process>> bucket= redissonClient
              .getBucket("processes:" + depId,new TypedJsonJacksonCodec(new TypeReference<List<Process>>() {}));
        List<Process> processes = bucket.get();

        return Mono.just(processes);
//        return processRepository.findByDepId(depId).collectList();
    }
    public Flux<Process> listProcess2(String depId) {
        return processRepository.findByDepId(depId);  // 返回 Flux<Process>
    }

    //修改密码
    public Mono<Void> resetPassword(String uid,String newPassword){
        return userRepository.updatePasswordById(uid,passwordEncoder.encode(newPassword)).then();
    }
}
