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
    List<Task> findAllByClassroom(String idClassroom);
    List<Task> findAllByPracticum(String idPracticum);
    List<Task> findAllByCreatedBy(String idCreator);

    @Query("SELECT DISTINCT task FROM Task task, Practicum practicum, Classroom classroom, User practican" +
            " WHERE practican.id = ?1 AND practican IN (classroom.practican) " +
            " AND classroom.practicum = practicum" +
            " AND (task.practicum = practicum OR task.classroom = classroom)" +
            " AND task.dueDate < ?2")
    List<Task> findAllByPracticumOfPractican(String idPractican, Date date);

    @Query("SELECT DISTINCT task FROM Task task, Classroom classroom, User practican" +
            " WHERE practican.id = ?1 AND practican IN (classroom.practican) AND task.classroom = classroom" +
            " AND task.dueDate < ?2")
    List<Task> findAllByClassroomOfPractican(String idPractican, Date date);

    @Query("SELECT DISTINCT task FROM Task task, Practicum practicum, Classroom classroom, User practican" +
            " WHERE practican.id = ?1 AND classroom.practicum = practicum AND" +
            " practican IN (classroom.practican) AND task.practicum = practicum" +
            " AND task.dueDate > ?2")
    List<Task> findAllPastByClassroomOfPractican(String idPractican, Date date);

    @Query("SELECT DISTINCT task FROM Task task, Classroom classroom, User practican" +
            " WHERE practican IN (classroom.practican) AND task.classroom = classroom" +
            " AND task.dueDate > ?2 AND practican.id = ?1 ")
    List<Task> findAllPastByPracticumOfPractican(String idPractican, Date date);

    List<Task> findAllByClassroom_PracticanContainsAndDueDateIsAfter
            (Practicum practicum, User user, Date date);

    List<Task> findAllByClassroomAndDueDateIsAfter(Classroom classroom, Date date);

    List<Task> findAllByClassroomAndDueDateIsBefore(Classroom classroom, Date date);

    List<Task> findAllByPracticumAndDueDateIsAfter(Practicum practicum, Date date);

    List<Task> findAllByPracticumAndDueDateIsBefore(Practicum practicum, Date date);

    List<Task> findAllByPracticumInAndDueDateIsAfter(Collection<Practicum> practicums, Date date);

    List<Task> findAllByPracticumInAndDueDateIsBefore(Collection<Practicum> practicums, Date date);

    List<Task> findAllByClassroomInAndDueDateIsAfter(Collection<Classroom> classrooms, Date date);

    List<Task> findAllByClassroomInAndDueDateIsBefore(Collection<Classroom> classrooms, Date date);

}
