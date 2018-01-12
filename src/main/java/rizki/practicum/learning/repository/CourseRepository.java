package rizki.practicum.learning.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.Course;

import java.util.List;

@Repository
public interface CourseRepository extends PagingAndSortingRepository<Course, String> {

    List<Course> findAllByCourseNameContainsOrCourseCodeContains(String courseName, String courseCode);

}
