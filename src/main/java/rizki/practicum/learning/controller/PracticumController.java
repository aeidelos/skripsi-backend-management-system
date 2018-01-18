package rizki.practicum.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rizki.practicum.learning.entity.Practicum;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.service.course.CourseService;
import rizki.practicum.learning.service.practicum.PracticumService;
import rizki.practicum.learning.service.user.UserService;
import rizki.practicum.learning.util.response.ResponseBuilder;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PracticumController {
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

    @GetMapping("/practicum")
    public ResponseEntity<Map<String,Object>> getPracticum(
            @PathVariable(required = false, value = "page") Integer page,
            @PathVariable(required = false,value = "limit") Integer limit,
            @PathVariable(required = false, value = "sort") String sort
    ){
        if(page == null) page = 0;
        if(limit == null) limit = 100;
        Pageable pageable = new PageRequest(page,limit);
        this.init();
        Page<Practicum> practicums = practicumService.getAllPracticum(pageable);
        Map<String, Object> map = new HashMap<>();
        map.put("practicums",practicums);
        httpStatus = HttpStatus.OK;
        location = "/practicum";
        statusResponse = 1;
        body = map;
        return this.response();
    }

    @PostMapping("/practicum")
    public ResponseEntity<Map<String,Object>> addPracticum(
            @RequestParam("practicumname") String practicumName,
            @RequestParam("idcourse") String idCourse
    ){
        this.init();
        location = "/practicum";
        Practicum practicum = new Practicum();
        practicum.setName(practicumName);
        practicum.setCourse(courseService.getCourse(idCourse));
        try{
            Practicum result = practicumService.addPracticum(practicum);
            if(result.getId()!=null){
                httpStatus = HttpStatus.CREATED;
                statusResponse = 1;
                message = "Praktikum berhasil ditambahkan";
                Map<String,Object> map = new HashMap<>();
                map.put("practicum",result);
                body = map;
            }
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = e.getMessage();
        }catch(ConstraintViolationException e){
            e.printStackTrace();
            message = "Lengkapi data praktikum :"+e.getMessage().toString();
        }
        return this.response();
    }

    @PutMapping("/practicum/{id}")
    public ResponseEntity<Map<String,Object>> updatePracticum(
            @PathVariable("id") String id,
            @RequestBody Practicum practicum
    ){
        this.init();
        location = "/practicum";
        Practicum old = practicumService.getPracticum(id);
        try{
            old.setName(practicum.getName());
            Practicum result = practicumService.updatePracticum(old);
            statusResponse = 1;
            message = "Data praktikum berhasil diubah";
            Map<String,Object> map = new HashMap<>();
            map.put("practicum",result);
            body = map;
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = e.getMessage();
        }
        return this.response();
    }

    @PostMapping("/practicum/{id}")
    public ResponseEntity<Map<String,Object>> assignOrRemoveCoordinatorAssistance(
            @PathVariable("id") String id,
            @RequestParam("idcoordinator") String idCoordinator
    ){
        this.init();
        location = "/practicum";
        Practicum old = practicumService.getPracticum(id);
        try{
            if(idCoordinator==null || idCoordinator=="" || idCoordinator=="null"){
                old.setCoordinatorAssistance(null);
                message = "Data koordinator asisten berhasil dihapus";
            }else{
                User coordinatorAssistance = userService.getUser(idCoordinator);
                if(coordinatorAssistance!=null){
                    old.setCoordinatorAssistance(coordinatorAssistance);
                    message = "Data koordinator asisten praktikum berhasil diubah";
                }else{
                    message = "Koordinator asisten tidak valid";
                }
            }
            Practicum result = practicumService.updatePracticum(old);
            statusResponse = 1;
            Map<String,Object> map = new HashMap<>();
            map.put("practicum",result);
            body = map;
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
        }
        return this.response();
    }

    @DeleteMapping("/practicum/{id}")
    public ResponseEntity<Map<String,Object>> deleteCourse(
            @PathVariable("id") String id
    ){
        this.init();
        location = "/practicum/{id}";
        try{
            Practicum practicum = practicumService.getPracticum(id);
            Map<String,Object> map = new HashMap<>();
            if(practicum == null){
                message = "Praktikum tidak terdefinisi";
                map.put("delete",false);
            }else{
                practicumService.deletePracticum(practicum);
                statusResponse = 1;
                message = "Praktikum berhasil dihapus";
            }
            body = map;
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = e.getMessage();
        }
        return this.response();
    }

    @GetMapping("/practicum/coordinator/{iduser}")
    public ResponseEntity<Map<String, Object>> getPracticumByCoordinatorAssistance(
            @PathVariable("iduser") String idUser
    ){
        this.init();
            try{
                List<Practicum> practicums = practicumService.getPracticumByCoordinatorAssistance(idUser);
                Map<String,Object> map = new HashMap<>();
                if(practicums == null){
                    message = "Praktikum tidak terdefinisi";
                    map.put("practicums",false);
                }else{
                    map.put("practicums",practicums);
                    statusResponse = 1;
                }
                body = map;
            }catch(IllegalArgumentException e) {
                e.printStackTrace();
                message = e.getMessage();
            }
        return this.response();
    }
}
