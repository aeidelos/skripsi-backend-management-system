package rizki.practicum.learning.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.Task;

import java.util.List;
@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, String> {
    List<Task> findAllByClassroom(String idClassroom);
    List<Task> findAllByPracticum(String idPracticum);
    List<Task> findAllByCreatedBy(String idCreator);
}
