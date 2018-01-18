package rizki.practicum.learning.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.entity.Practicum;

import java.util.List;

@Repository
public interface ClassroomRepository extends PagingAndSortingRepository<Classroom, String> {

    Classroom findByEnrollmentKey(String enrollmentKey);

    @Query("SELECT classroom FROM Classroom classroom WHERE classroom.practicum.id = ?1")
    List<Classroom> findAllByPracticum(String practicum);

}
