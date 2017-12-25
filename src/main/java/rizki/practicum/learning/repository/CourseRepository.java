package rizki.practicum.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.Course;
@Repository
public interface CourseRepository extends CrudRepository<Course, String> {
}
