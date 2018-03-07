package rizki.practicum.learning.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.entity.Practicum;
import rizki.practicum.learning.entity.Task;
import rizki.practicum.learning.entity.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;
@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, String> {

    List<Task> findAllByClassroomAndDueDateIsAfter(Classroom classroom, Date date);

    List<Task> findAllByClassroomAndDueDateIsBefore(Classroom classroom, Date date);

    List<Task> findAllByPracticumAndDueDateIsAfter(Practicum practicum, Date date);

    List<Task> findAllByPracticumAndDueDateIsBefore(Practicum practicum, Date date);

    List<Task> findAllByPracticumInAndDueDateIsAfter(Collection<Practicum> practicums, Date date);

    List<Task> findAllByPracticumInAndDueDateIsBefore(Collection<Practicum> practicums, Date date);

    List<Task> findAllByClassroomInAndDueDateIsAfter(Collection<Classroom> classrooms, Date date);

    List<Task> findAllByClassroomInAndDueDateIsBefore(Collection<Classroom> classrooms, Date date);

}
