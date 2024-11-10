package nefu.graduation_async.service;

import lombok.RequiredArgsConstructor;
import nefu.graduation_async.Repository.UserRepository;
import nefu.graduation_async.dox.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //添加用户
    public Mono<Integer> addUser(User user)
    {
        return userRepository.save(user).thenReturn(1);
    }
}
