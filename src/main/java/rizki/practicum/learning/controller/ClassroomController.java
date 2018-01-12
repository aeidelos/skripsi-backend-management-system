package rizki.practicum.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.service.classroom.ClassroomService;
import rizki.practicum.learning.service.course.CourseService;
import rizki.practicum.learning.service.practicum.PracticumService;
import rizki.practicum.learning.service.user.UserService;
import rizki.practicum.learning.util.response.ResponseBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClassroomController {

    private HttpStatus httpStatus = null;
    private String location = "";
    private int statusResponse = 0;
    private String message = "";
    private Object body = null;

    @Autowired
    private PracticumService practicumService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClassroomService classroomService;

    private void init(){
        httpStatus = HttpStatus.OK;
        location = "";
        statusResponse = 0;
        message = "";
        body = null;
    }

    private ResponseEntity<Map<String,Object>> response(){
        return new ResponseBuilder()
                .setMessage(message)
                .setLocation(location)
                .setStatusCode(httpStatus)
                .setStatusResponse(statusResponse)
                .setBody(body)
                .build();
    }


    @GetMapping("/classroom/practicum/{idpracticum}")
    public ResponseEntity<Map<String,Object>> getClassByPracticum(
            @PathVariable("idpracticum") String idPracticum
    ){
        this.init();
        try{
            List<Classroom> classrooms = classroomService.getByPracticum(idPracticum);
            Map<String, Object> map = new HashMap<>();
            map.put("classrooms", classrooms);
            body = map;
            statusResponse = 1;
        }catch(IllegalArgumentException e){
            message = "Praktikum tidak terdefinisi :"+ e;
        }
        return this.response();
    }


    @PostMapping("/classroom")
    public ResponseEntity<Map<String,Object>> addClassroom(
        @RequestParam("idpracticum") String idPracticum,
        @RequestParam("classroomname") String classroomName
    ){
        this.init();
        Classroom classroom = new Classroom();
        classroom.setName(classroomName);
        Classroom result = classroomService.addClassroom(classroom);
        if(result!=null){
            Map<String, Object> map = new HashMap<>();
            map.put("classroom", result);
            body = map;
            httpStatus = HttpStatus.CREATED;
            statusResponse = 1;
        }
        return this.response();
    }

    @DeleteMapping("/classroom/{idclassroom}")
    public ResponseEntity<Map<String, Object>> deleteClassroom(
            @PathVariable("idclassroom") String idClassroom
    ){
        this.init();
        try{
            classroomService.deleteClassroom(idClassroom);
            Classroom checker = classroomService.getClassroom(idClassroom);
            if(checker==null) statusResponse = 1;
        }catch(IllegalArgumentException e){
            message = "Kelas tidak terdefinisi :"+ e;
        }
        return this.response();
    }

    @PostMapping("/classroom/name/{idclassroom}")
    public ResponseEntity<Map<String, Object>> updateClassroom(
            @PathVariable("idclassroom") String idClassroom,
            @RequestParam("classroomname") String classroomName
    ){
        this.init();
        try{
            Classroom classroom = classroomService.getClassroom(idClassroom);
            classroom.setName(classroomName);
            Classroom result = classroomService.updateClassroom(classroom);
            if(result!=null){
                message = "Data kelas berhasil diubah";
                Map<String, Object> map = new HashMap<>();
                map.put("classroom", result);
                body = map;
                httpStatus = HttpStatus.CREATED;
                statusResponse = 1;
            }else{
                message = "Data kelas gagal diubah";
            }
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = "Id classroom tidak sesuai :"+e.getMessage().toString();
        }
        return this.response();
    }

    @PostMapping("/classroom/assign/{idclassroom}")
    public ResponseEntity<Map<String,Object>> assignAssistance(
            @PathVariable("idclassroom") String idClassroom,
            @RequestParam("idassistance") String idAssistance
    ){
        this.init();
        try{
            classroomService.addAssistance(idClassroom,idAssistance);
            message = "Data kelas berhasil diubah";
            statusResponse = 1;
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = "Parameter tidak lengkap : "+e.getMessage().toString();
        }catch(DataIntegrityViolationException e){
            e.printStackTrace();
            message = e.getMessage().toString();
        }
        return this.response();
    }

}
