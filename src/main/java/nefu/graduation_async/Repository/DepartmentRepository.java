package nefu.graduation_async.Repository;

import nefu.graduation_async.dox.Department;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DepartmentRepository extends ReactiveCrudRepository<Department,String> {

}
