package rizki.practicum.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rizki.practicum.learning.entity.Course;
import rizki.practicum.learning.service.course.CourseService;
import rizki.practicum.learning.util.response.ResponseBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CourseController {
    private HttpStatus httpStatus = null;
    private String location = "";
    private int statusResponse = 0;
    private String message = "";
    private Object body = null;

    @Autowired
    private CourseService courseService;

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

    @GetMapping("/search/course/{query}")
    public ResponseEntity<Map<String,Object>> getSearchQuery(
            @PathVariable(required = false, value = "query") String query
    ){
        this.init();
        List<Course> courses = courseService.getSearchCourse(query);
        Map<String, Object> map = new HashMap<>();
        map.put("courses",courses);
        httpStatus = HttpStatus.OK;
        location = "";
        statusResponse = 1;
        body = map;
        return this.response();
    }

    @GetMapping("/course")
    public ResponseEntity<Map<String,Object>> getCourses(
            @PathVariable(required = false, value = "page") Integer page,
            @PathVariable(required = false,value = "limit") Integer limit,
            @PathVariable(required = false, value = "sort") String sort
    ){
        if(page == null) page = 0;
        if(limit == null) limit = 100;
        Pageable pageable = new PageRequest(page,limit);
        this.init();
        Page<Course> courses = courseService.getAllCourse(pageable);
        Map<String, Object> map = new HashMap<>();
        map.put("courses",courses);
        httpStatus = HttpStatus.OK;
        location = "";
        statusResponse = 1;
        body = map;
        return this.response();
    }

    @PostMapping("/course")
    public ResponseEntity<Map<String,Object>> addCourse(
        @RequestParam("coursename") String courseName,
        @RequestParam("coursecode") String courseCode
    ){
        this.init();
        location = "/course";
        Course course = new Course();
        course.setCourseName(courseName);
        course.setCourseCode(courseCode);
        try{
            Course result = courseService.addCourse(course);
            if(result.getId()!=null){
                httpStatus = HttpStatus.CREATED;
                statusResponse = 1;
                message = "Mata Kuliah berhasil ditambahkan";
                Map<String,Object> map = new HashMap<>();
                map.put("course",result);
                body = map;
            }
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = e.getMessage();
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            message = "Duplicated course code";
        }
        return this.response();
    }

    @PutMapping("/course/{id}")
    public ResponseEntity<Map<String,Object>> updateCourse(
            @PathVariable("id") String id,
            @RequestBody Course course
    ){
        this.init();
        location = "/course";
        Course updater = courseService.getCourse(id);
        try{
            updater.setCourseName(course.getCourseName());
            updater.setCourseCode(course.getCourseCode());
            Course result = courseService.updateCourse(updater);
            statusResponse = 1;
            message = "Mata Kuliah berhasil diubah";
            Map<String,Object> map = new HashMap<>();
            map.put("course",result);
            body = map;
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = e.getMessage();
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            message = "Duplicated course code";
        }
        return this.response();
    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<Map<String,Object>> deleteCourse(
            @PathVariable("id") String id
    ){
        this.init();
        location = "/course/{id}";
        try{
            boolean delete = courseService.removeCourse(id);
            if(delete){
                statusResponse = 1;
                message = "Mata Kuliah berhasil diubah";
                Map<String,Object> map = new HashMap<>();
                map.put("delete",true);
                body = map;
            }else{
                statusResponse = 0;
                message = "Mata Kuliah gagal dihapus";
            }
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = e.getMessage();
        }
        return this.response();
    }

}
