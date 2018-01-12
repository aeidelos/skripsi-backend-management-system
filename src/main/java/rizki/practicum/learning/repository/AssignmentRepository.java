package rizki.practicum.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.Assignment;

import java.util.List;

@Repository
public interface AssignmentRepository extends CrudRepository<Assignment, String> {
    List<Assignment> findAllByTask(String idTask);
}
