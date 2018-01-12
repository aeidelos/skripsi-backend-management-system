package rizki.practicum.learning.service.course;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import rizki.practicum.learning.entity.Course;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface CourseService {
    Course addCourse(@Valid Course course);
    boolean removeCourse(@NotNull @NotBlank String course);
    Course updateCourse(@Valid Course course);
    Course getCourse(@NotNull @NotBlank String course);
    Page<Course> getAllCourse(Pageable pageable);
    List<Course> getSearchCourse(String query);
}
