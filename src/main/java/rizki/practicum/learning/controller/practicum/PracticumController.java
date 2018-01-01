package rizki.practicum.learning.controller.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rizki.practicum.learning.configuration.RoutesConfig;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.entity.Course;
import rizki.practicum.learning.entity.Practicum;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.exception.ExceptionMessage;
import rizki.practicum.learning.service.classroom.ClassroomService;
import rizki.practicum.learning.service.course.CourseService;
import rizki.practicum.learning.service.practicum.PracticumService;
import rizki.practicum.learning.service.user.UserService;
import rizki.practicum.learning.util.response.ResponseWrapper;

import java.util.List;
import java.util.Map;

@RestController
public class PracticumController {

    @Autowired
    private PracticumService practicumService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private ResponseWrapper responseWrapper;

    @PostMapping(RoutesConfig.PracticumRoutes.PRACTICUM_ADD)
    public ResponseEntity<Map<String,Object>> addPracticum(
            @RequestParam("name") String practicum_name,
            @RequestParam("id_course") String id_course
    ){
        Practicum practicum = new Practicum();
        try {
            practicum.setCourse(courseService.getCourse(id_course));
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
                    RoutesConfig.PracticumRoutes.PRACTICUM_ADD,
                    0,ExceptionMessage.Practicum.PRACTICUM_CREATED_FAIL);
        }
        practicum.setName(practicum_name);
        try{
            practicumService.addPracticum(practicum);
            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                    RoutesConfig.PracticumRoutes.PRACTICUM_ADD,
                    1,ExceptionMessage.Practicum.PRACTICUM_CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
                    RoutesConfig.PracticumRoutes.PRACTICUM_ADD,
                    0,ExceptionMessage.Practicum.PRACTICUM_CREATED_FAIL);
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.PRACTICUM_UPDATE_COORDINATOR_ASSISTANCE)
    public ResponseEntity<Map<String,Object>> addCoordinatorAssistance(
            @PathVariable("id_practicum") String id_practicum,
            @RequestParam("id_assistance") String id_assistance
    ){
        try {
            Practicum practicum = practicumService.getDetailPracticum(id_practicum);
            practicumService.setCoordinatorAssistance(id_practicum,id_assistance);
            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                    RoutesConfig.PracticumRoutes.PRACTICUM_UPDATE_COORDINATOR_ASSISTANCE,
                    0,ExceptionMessage.Practicum.PRACTICUM_UPDATED);
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
                    RoutesConfig.PracticumRoutes.PRACTICUM_UPDATE_COORDINATOR_ASSISTANCE,
                    0,ExceptionMessage.Practicum.PRACTICUM_UPDATED_FAIL);
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_ADD)
    public ResponseEntity<Map<String,Object>> addClassroom(
            @PathVariable("id_practicum") String id_practicum,
            @RequestParam("name") String name,
            @RequestParam("location") String location
    )
    {
        try {
            Classroom classroom = new Classroom();
            classroom.setName(name);
            classroom.setPracticum(practicumService.getDetailPracticum(id_practicum));
            classroom.setLocation(location);
            classroomService.addClassroom(classroom);
            return responseWrapper.restResponseWrapper(HttpStatus.CREATED,null,
                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_ADD,
                    1,ExceptionMessage.Practicum.Classroom.CLASSROOM_CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_ADD,
                    0,ExceptionMessage.Practicum.Classroom.CLASSROOM_CREATED_FAIL);
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_UPDATE)
    public ResponseEntity<Map<String,Object>> updateClassroomName(
            @PathVariable("id_classroom") String id_classroom,
            @RequestParam("classroom_name") String classroom_name
    )
    {
        try{
            updateClassroomName(id_classroom, classroom_name);
            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_UPDATE,
                    1,ExceptionMessage.Practicum.Classroom.CLASSROOM_UPDATED);
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_UPDATE,
                    0,ExceptionMessage.Practicum.Classroom.CLASSROOM_UPDATED_FAIL);
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.ClassroomRoutes.PREFIX)
    public ResponseEntity<Map<String,Object>> getAllClassroom()
    {
        try{
            List<Classroom> classrooms = classroomService.getAllClassroom();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,classrooms,
                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_UPDATE,
                    1,"");
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_UPDATE,
                    0,"");
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_DETAIL)
    public ResponseEntity<Map<String,Object>> getDetailClassroom(
            @PathVariable("id_classroom") String idClassroom
    )
    {
        try{
            Classroom classroom = classroomService.getClassroom(idClassroom);
            return responseWrapper.restResponseWrapper(HttpStatus.OK,classroom,
                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_UPDATE,
                    1,"");
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_UPDATE,
                    0,"");
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.ClassroomRoutes.ASSIGN_ASSISTANT)
    public ResponseEntity<Map<String,Object>> addClassroomAssistance(
            @PathVariable("id_classroom") String id_classroom,
            @RequestParam("id_assistance") String id_assistance
    )
    {
        try{
            classroomService.addAssistance(id_classroom,id_assistance);
            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                    RoutesConfig.PracticumRoutes.ClassroomRoutes.ASSIGN_ASSISTANT,
                    1,ExceptionMessage.Practicum.Classroom.CLASSROOM_UPDATED);
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
                    RoutesConfig.PracticumRoutes.ClassroomRoutes.ASSIGN_ASSISTANT,
                    0,ExceptionMessage.Practicum.Classroom.CLASSROOM_UPDATED_FAIL);
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.ClassroomRoutes.ASSIGN_PRACTICAN)
    public ResponseEntity<Map<String,Object>> enrollPractican(
            @RequestParam("enrollment_key") String enrollment_key,
            @RequestParam("id_assistance") String id_practican
    )
    {
        try{
            classroomService.enrollment(enrollment_key, id_practican);
            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                    RoutesConfig.PracticumRoutes.ClassroomRoutes.ASSIGN_PRACTICAN,
                    1,ExceptionMessage.Practicum.Classroom.CLASSROOM_UPDATED);
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
                    RoutesConfig.PracticumRoutes.ClassroomRoutes.ASSIGN_PRACTICAN,
                    0,ExceptionMessage.Practicum.Classroom.CLASSROOM_UPDATED_FAIL);
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.CourseRoutes.PREFIX)
    public ResponseEntity<Map<String,Object>> getAllCourse()
    {
        try{
            List<Course> courses = courseService.getAllCourse();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,courses,
                    RoutesConfig.PracticumRoutes.CourseRoutes.PREFIX,
                    1, null);
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                    RoutesConfig.PracticumRoutes.CourseRoutes.PREFIX,
                    0,e.getMessage());
        }
    }

    @GetMapping(RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_GET)
    public ResponseEntity<Map<String,Object>> getCourse(
            @PathVariable("id_course") String id_course
    )
    {
        try{
            Course course = courseService.getCourse(id_course);
            return responseWrapper.restResponseWrapper(HttpStatus.OK,course,
                    RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_GET,
                    1, null);
        }catch(Exception e){
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                    RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_GET,
                    0,e.getMessage());
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_ADD)
    public ResponseEntity<Map<String,Object>> addCourse
            (@RequestParam("courseName") String courseName,
             @RequestParam("courseCode") String courseCode)
    {
        try{
            Course newCourse = new Course();
            newCourse.setCourseCode(courseCode);
            newCourse.setCourseName(courseName);
            Course result = courseService.addCourse(newCourse);
            if(result!=null){
                return responseWrapper.restResponseWrapper(HttpStatus.CREATED,null,
                        RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_ADD,
                        1, ExceptionMessage.Practicum.Course.COURSE_CREATED);
            }else{
                return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                        RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_ADD,
                        0, ExceptionMessage.Practicum.Course.COURSE_CREATED_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.NOT_ACCEPTABLE,e.getMessage(),
                    RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_ADD,
                    0,null);
        }
    }

    @GetMapping(RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_DELETE)
    public ResponseEntity<Map<String,Object>> removeCourse(
            @PathVariable("id_course") String id_course
    )
    {
        try {
            if(courseService.removeCourse(id_course)){
                return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                        RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_DELETE,
                        1, ExceptionMessage.Practicum.Course.COURSE_DELETED);
            }else{
                return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                        RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_DELETE,
                        0, ExceptionMessage.Practicum.Course.COURSE_DELETED_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
                    RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_DELETE,
                    0, ExceptionMessage.Practicum.Course.COURSE_DELETED_FAIL);
        }
    }

}
