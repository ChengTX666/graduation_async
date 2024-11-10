package nefu.graduation_async.Repository;

import nefu.graduation_async.dox.Score;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ScoreRepository extends ReactiveCrudRepository<Score,String> {

    @Query("SELECT * from score where student_id=:sid")
    Flux<Score> findByStudentId(String sid);


    @Query("SELECT * from score where `group`=:group")
    Flux<Score> findByGroup(int group);

    @Modifying
    @Query("UPDATE score s set s.detail=:detail where s.id=:sid")
    Mono<Integer> updateDetailById(String sid, String detail);

    @Query("SELECT * from score where `group`=:group")
    Flux<Score> findScoresByGroup(int group);
}
