//package rizki.practicum.learning.controller.practicum;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import rizki.practicum.learning.configuration.RoutesConfig;
//import rizki.practicum.learning.entity.Classroom;
//import rizki.practicum.learning.entity.Course;
//import rizki.practicum.learning.entity.Practicum;
//import rizki.practicum.learning.entity.User;
//import rizki.practicum.learning.exception.ExceptionMessage;
//import rizki.practicum.learning.service.classroom.ClassroomService;
//import rizki.practicum.learning.service.course.CourseService;
//import rizki.practicum.learning.service.practicum.PracticumService;
//import rizki.practicum.learning.service.user.UserService;
//import rizki.practicum.learning.util.response.ResponseWrapper;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//public class PracticumController {
//
//    @Autowired
//    private PracticumService practicumService;
//
//    @Autowired
//    private CourseService courseService;
//
//    @Autowired
//    private ClassroomService classroomService;
//
//    @Autowired
//    private ResponseWrapper responseWrapper;
//
//    public ResponseEntity<Map<String,Object>> addCoordinatorAssistance(
//            @PathVariable("id_practicum") String id_practicum,
//            @RequestParam("id_assistance") String id_assistance
//    ){
//        try {
//            Practicum practicum = practicumService.getDetailPracticum(id_practicum);
//            practicumService.setCoordinatorAssistance(id_practicum,id_assistance);
//            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
//                    RoutesConfig.PracticumRoutes.PRACTICUM_UPDATE_COORDINATOR_ASSISTANCE,
//                    0,ExceptionMessage.Practicum.PRACTICUM_UPDATED);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
//                    RoutesConfig.PracticumRoutes.PRACTICUM_UPDATE_COORDINATOR_ASSISTANCE,
//                    0,ExceptionMessage.Practicum.PRACTICUM_UPDATED_FAIL);
//        }
//    }
//
//    public ResponseEntity<Map<String,Object>> addClassroom(
//            @PathVariable("id_practicum") String id_practicum,
//            @RequestParam("name") String name,
//            @RequestParam("location") String location
//    )
//    {
//        try {
//            Classroom classroom = new Classroom();
//            classroom.setName(name);
//            classroom.setPracticum(practicumService.getDetailPracticum(id_practicum));
//            classroom.setLocation(location);
//            classroomService.addClassroom(classroom);
//            return responseWrapper.restResponseWrapper(HttpStatus.CREATED,null,
//                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_ADD,
//                    1,ExceptionMessage.Practicum.Classroom.CLASSROOM_CREATED);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
//                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_ADD,
//                    0,ExceptionMessage.Practicum.Classroom.CLASSROOM_CREATED_FAIL);
//        }
//    }
//
//    public ResponseEntity<Map<String,Object>> updateClassroomName(
//            @PathVariable("id_classroom") String id_classroom,
//            @RequestParam("classroom_name") String classroom_name
//    )
//    {
//        try{
//            updateClassroomName(id_classroom, classroom_name);
//            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
//                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_UPDATE,
//                    1,ExceptionMessage.Practicum.Classroom.CLASSROOM_UPDATED);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
//                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_UPDATE,
//                    0,ExceptionMessage.Practicum.Classroom.CLASSROOM_UPDATED_FAIL);
//        }
//    }
//
//    public ResponseEntity<Map<String,Object>> getAllClassroom()
//    {
//        try{
//            List<Classroom> classrooms = classroomService.getAllClassroom();
//            return responseWrapper.restResponseWrapper(HttpStatus.OK,classrooms,
//                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_UPDATE,
//                    1,"");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
//                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_UPDATE,
//                    0,"");
//        }
//    }
//
//    public ResponseEntity<Map<String,Object>> getDetailClassroom(
//            @PathVariable("id_classroom") String idClassroom
//    )
//    {
//        try{
//            Classroom classroom = classroomService.getClassroom(idClassroom);
//            return responseWrapper.restResponseWrapper(HttpStatus.OK,classroom,
//                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_UPDATE,
//                    1,"");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
//                    RoutesConfig.PracticumRoutes.ClassroomRoutes.CLASSROOM_UPDATE,
//                    0,"");
//        }
//    }
//
//    public ResponseEntity<Map<String,Object>> addClassroomAssistance(
//            @PathVariable("id_classroom") String id_classroom,
//            @RequestParam("id_assistance") String id_assistance
//    )
//    {
//        try{
//            classroomService.addAssistance(id_classroom,id_assistance);
//            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
//                    RoutesConfig.PracticumRoutes.ClassroomRoutes.ASSIGN_ASSISTANT,
//                    1,ExceptionMessage.Practicum.Classroom.CLASSROOM_UPDATED);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
//                    RoutesConfig.PracticumRoutes.ClassroomRoutes.ASSIGN_ASSISTANT,
//                    0,ExceptionMessage.Practicum.Classroom.CLASSROOM_UPDATED_FAIL);
//        }
//    }
//
//    public ResponseEntity<Map<String,Object>> enrollPractican(
//            @RequestParam("enrollment_key") String enrollment_key,
//            @RequestParam("id_assistance") String id_practican
//    )
//    {
//        try{
//            classroomService.enrollment(enrollment_key, id_practican);
//            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
//                    RoutesConfig.PracticumRoutes.ClassroomRoutes.ASSIGN_PRACTICAN,
//                    1,ExceptionMessage.Practicum.Classroom.CLASSROOM_UPDATED);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return responseWrapper.restResponseWrapper(HttpStatus.OK,e.getMessage(),
//                    RoutesConfig.PracticumRoutes.ClassroomRoutes.ASSIGN_PRACTICAN,
//                    0,ExceptionMessage.Practicum.Classroom.CLASSROOM_UPDATED_FAIL);
//        }
//    }
//
//}
