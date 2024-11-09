package nefu.graduation_async.Repository;

import nefu.graduation_async.dox.Process;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface ProcessRepository extends ReactiveCrudRepository<Process,String> {
    @Query("select * from `process` p where p.depId=:depId")
    Flux<Process> findByDepId(String depId);



    @Query("delete from process where id=:pid and depId=:did")
    Mono<Void> deleteByIdAndDepId(String pid, String did);
}
