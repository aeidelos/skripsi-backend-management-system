package rizki.practicum.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.Classroom;

@Repository
public interface ClassroomRepository extends PagingAndSortingRepository<Classroom, String> {
    Classroom findByEnrollmentKey(String enrollmentKey);
}
