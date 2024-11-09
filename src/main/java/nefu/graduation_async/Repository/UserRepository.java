package nefu.graduation_async.Repository;

import nefu.graduation_async.dox.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User,String> {


    @Query("select * from user where account=:account")
    public Mono<User> findByAccount(String account);
}
