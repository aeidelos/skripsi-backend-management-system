package rizki.practicum.learning.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Course;
import rizki.practicum.learning.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Course addCourse(Course course) throws Exception {
        return courseRepository.save(course);
    }

    @Override
    public boolean removeCourse(String course) throws Exception {
        courseRepository.delete(course);
        if(courseRepository.findOne(course).equals(null)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Course updateCourse(Course course) throws Exception {
        return courseRepository.save(course);
    }

    @Override
    public Course getCourse(String course) throws Exception {
        return courseRepository.findOne(course);
    }

    @Override
    public List<Course> getAllCourse() throws Exception {
        return (ArrayList<Course>) courseRepository.findAll();
    }
}
