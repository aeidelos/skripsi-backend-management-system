package rizki.practicum.learning.controller.course;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rizki.practicum.learning.entity.Course;
import rizki.practicum.learning.repository.CourseRepository;

import static org.junit.Assert.*;

public class CourseControllerTest {

    @Autowired
    CourseRepository courseRepository;

    @Before
    public void setUp() throws Exception {
        Course course = new Course();
        course.setCourseName("COURSETESTING1");
        course.setCourseCode("COURSETESTING1");

        Course course2 = new Course();
        course2.setCourseCode("COURSETESTING2");
        course2.setCourseName("COURSETESTING2");

        courseRepository.save(course);
        courseRepository.save(course2);
    }

    @After
    public void tearDown() throws Exception {
        courseRepository.delete("COURSETESTING1");
        courseRepository.delete("COURSETESTING2");
    }

    @Test
    public void getSearchQuery() throws Exception {

    }

    @Test
    public void getCourses() throws Exception {
    }

    @Test
    public void addCourse() throws Exception {
    }

    @Test
    public void updateCourse() throws Exception {
    }

    @Test
    public void deleteCourse() throws Exception {
    }

}