package rizki.practicum.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.service.classroom.ClassroomService;
import rizki.practicum.learning.service.course.CourseService;
import rizki.practicum.learning.service.practicum.PracticumService;
import rizki.practicum.learning.service.user.UserService;
import rizki.practicum.learning.util.response.ResponseBuilder;

import javax.xml.ws.Response;
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

    @GetMapping("/classroom")
    public ResponseEntity<Map<String,Object>> getAllClassrooms(
            @PathVariable(required = false, value = "page") Integer page,
            @PathVariable(required = false,value = "limit") Integer limit,
            @PathVariable(required = false, value = "sort") String sort
    ){
        this.init();
        if(page == null) page = 0;
        if(limit == null) limit = 100;
        Pageable pageable = new PageRequest(page,limit);
        Page<Classroom> classrooms = classroomService.getAllClassroom(pageable);
        Map<String, Object> map = new HashMap<>();
        map.put("classrooms", classrooms);
        body = map;
        statusResponse = 1;
        return this.response();
    }

    @PostMapping("/classroom")
    public ResponseEntity<Map<String,Object>> addClassroom(
        @RequestBody Classroom classroom
    ){
        this.init();
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

    @PutMapping("/classroom/{idclassroom}")
    public ResponseEntity<Map<String, Object>> updateClassroom(
            @PathVariable("idclassroom") String id,
            @RequestBody Classroom classroom
    ){
        this.init();
        try{

            Classroom temp = classroomService.getClassroom(id);
            if(temp!=null){
                Classroom result = classroomService.updateClassroom(classroom);
                message = "Data kelas berhasil diubah";
                Map<String, Object> map = new HashMap<>();
                map.put("classroom", result);
                body = map;
                httpStatus = HttpStatus.CREATED;
                statusResponse = 1;
            }else{
                message = "Data kelas tidak ada";
            }
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = "Id classroom tidak sesuai :"+e.getMessage().toString();
        }
        return this.response();
    }

    @GetMapping("/classroom/enroll/{key}")
    public ResponseEntity<Map<String, Object>> enrollmentClassroom(
            @PathVariable("key") String enrollmentKey
    ){
        this.init();
        try{
            Classroom result = classroomService.searchByEnrollmentKey(enrollmentKey);
            Map<String, Object> map = new HashMap<>();
            map.put("classroom", result);
            body = map;
            if(result != null){
                statusResponse = 1;
            }
        }catch(IllegalArgumentException e){
            message = "Kode entrollment tidak boleh kosong";
        }
        return this.response();
    }

    @GetMapping("/classroom/assistance/{iduser}")
    public ResponseEntity<Map<String, Object>> getClassroomByAssistance(
            @PathVariable("iduser") String idUser
    ){
        this.init();
        try{
            List<Classroom> classrooms = classroomService.getByAssistance(idUser);
            statusResponse = 1;
            Map<String, Object> map = new HashMap<>();
            map.put("classrooms", classrooms);
            body = map;
        }catch(Exception e){

        }
        return this.response();
    }

    @GetMapping("/classroom/practican/{iduser}")
    public ResponseEntity<Map<String, Object>> getClassroomByPractican(
            @PathVariable("iduser") String idUser
    ){
        this.init();
        try{
            List<Classroom> classrooms = classroomService.getByPractican(idUser);
            statusResponse = 1;
            Map<String, Object> map = new HashMap<>();
            map.put("classrooms", classrooms);
            body = map;
        }catch(Exception e){

        }
        return this.response();
    }

}
