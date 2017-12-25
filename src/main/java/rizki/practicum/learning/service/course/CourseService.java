package rizki.practicum.learning.service.course;

import rizki.practicum.learning.entity.Course;

import java.util.List;

public interface CourseService {
    Course addCourse(Course course) throws Exception;
    boolean removeCourse(String course) throws Exception;
    Course updateCourse(Course course) throws Exception;
    Course getCourse(String course) throws Exception;
    List<Course> getAllCourse() throws Exception;
}
