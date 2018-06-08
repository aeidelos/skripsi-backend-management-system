package rizki.practicum.learning.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import rizki.practicum.learning.entity.Announcement;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.entity.Practicum;
import rizki.practicum.learning.entity.User;

import java.util.List;

public interface AnnouncementRepository extends PagingAndSortingRepository<Announcement, String> {

    Announcement findByClassroom(Classroom classroom);


    Announcement findByPracticum(Practicum practicum);

    @Query("SELECT a FROM Announcement a, Classroom c WHERE " +
            " ?1 member of c.practican AND (a.practicum = c.practicum OR " +
            "a.classroom = c OR a.practicum = null OR a.classroom = null)")
    List<Announcement> getAnnouncementForUser(User user);

}