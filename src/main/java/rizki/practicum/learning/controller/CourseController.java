package rizki.practicum.learning.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rizki.practicum.learning.dto.ResponseObject;
import rizki.practicum.learning.entity.Course;
import rizki.practicum.learning.service.course.CourseService;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/search/course/{query}", produces = {"application/json"})
    @ApiOperation(value = "mencari mata kuliah tertentu")
    public @ResponseBody
    List<Course> getSearchQuery(
            @ApiParam(value="Query pencarian")
            @PathVariable(required = false, value = "query") String query
    ){
        List<Course> courses = courseService.getSearchCourse(query);
        WebResponse.verify(courses);
        return courses;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Mengambil daftar mata kuliah")
    @GetMapping(value = "/course", produces = {"application/json"})
    public @ResponseBody Page<Course> getCourses(
            @PathVariable(required = false, value = "page") Integer page,
            @PathVariable(required = false, value = "limit") Integer limit,
            @PathVariable(required = false, value = "sort") String sort
    ){
        page = WebResponse.DEFAULT_PAGE_NUM;
        limit = WebResponse.DEFAULT_PAGE_SIZE;
        Pageable pageable = new PageRequest(page,limit);
        Page<Course> courses = courseService.getAllCourse(pageable);
        WebResponse.verify(courses);
        return courses;
    }

    @ApiOperation(value = "Menambahkan mata kuliah")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value="/course", produces = {"application/json"})
    public @ResponseBody Course addCourse(
        @RequestParam("coursename") String courseName,
        @RequestParam("coursecode") String courseCode
    ){
        Course course = new Course();
        course.setCourseName(courseName);
        course.setCourseCode(courseCode);
        Course result = courseService.addCourse(course);
        WebResponse.verify(result);
        return result;

    }

    @ApiOperation(value = "Mengubah data mata kuliah")
    @PutMapping(value = "/course/{id}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Course updateCourse(
            @ApiParam(value = "Id tugas") @PathVariable("id") String id,
            @ApiParam(value = "Objek json course" )@RequestBody Course course
    ){
        Course updater = courseService.getCourse(id);
        updater.setCourseName(course.getCourseName());
        updater.setCourseCode(course.getCourseCode());
        Course result = courseService.updateCourse(updater);
        WebResponse.verify(result);
        return result;

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/course/{id}")
    @ApiOperation(value = "Menghapus data mata kuliah")
    public @ResponseBody ResponseObject deleteCourse(
            @ApiParam(value = "String id mata kuliah") @PathVariable("id") String id
    ){
        courseService.removeCourse(id);
        return ResponseObject
                .builder()
                .code(HttpStatus.OK.value())
                .message("Tugas berhasil dihapus")
                .status(HttpStatus.OK.getReasonPhrase())
                .build();
    }

}
