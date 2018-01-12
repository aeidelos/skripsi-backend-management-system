package rizki.practicum.learning.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Course;
import rizki.practicum.learning.repository.CourseRepository;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public boolean removeCourse(String course){
        courseRepository.delete(course);
        return true;
    }

    @Override
    public Course updateCourse(Course course){
        return courseRepository.save(course);
    }

    @Override
    public Course getCourse(String course) {
        return courseRepository.findOne(course);
    }

    @Override
    public Page<Course> getAllCourse(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public List<Course> getSearchCourse(String query) {
        return courseRepository.findAllByCourseNameContainsOrCourseCodeContains(query,query);
    }
}
