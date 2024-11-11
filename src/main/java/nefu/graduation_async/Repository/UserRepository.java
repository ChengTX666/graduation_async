package nefu.graduation_async.Repository;

import nefu.graduation_async.dox.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User,String> {

    Mono<User> findByAccount(String account);

    @Modifying
    @Query("UPDATE user u set u.password=:encodePassword where u.id=:uid")
    Mono<Integer> updatePasswordById(String uid,String encodePassword);

    @Modifying
    @Query("UPDATE user u set u.password=:encodePassword where u.account=:account")
    Mono<Integer> updatePasswordByAccount(String account,String encodePassword);


    @Query("select count(*) from user  where department->>'$.depId'=:did")
    Mono<Integer> countByDepartment(String did);


    @Query("select * from user where department->>'$.depId'=:depId and role=:role")
    Flux<User> findByDepIdAndRole(String depId, String role);

    @Query("select * from user where department->>'$.depId'=:depId and role=:role and `group`=:group")
    Flux<User> findByDepIdAndRoleAndGroup(String depId,String role,int group);


    @Query("select * from user u where department->>'$.depId'=:depId and student->>'$.teacherId'=:tid")
    Flux<User> mentorStudents(String depId,String tid);

    @Modifying
    @Query("update user u set student=json_set(student,'$.teacherId',:tid) where id=:sid")
    Mono<Integer> updateStudentById(String sid,String tid);
}
