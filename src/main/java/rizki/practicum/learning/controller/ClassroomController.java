package rizki.practicum.learning.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rizki.practicum.learning.dto.ResponseObject;
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

    private Object body = null;

    @Autowired
    private ClassroomService classroomService;

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Mengambil data kelas per praktikum")
    @GetMapping(value = "/classroom/practicum/{idpracticum}", produces = {"application/json"})
    public @ResponseBody List<Classroom> getClassByPracticum(
            @ApiParam("Id praktikum dalam format string") @PathVariable("idpracticum") String idPracticum
    ){
        List<Classroom> classrooms = classroomService.getByPracticum(idPracticum);
        WebResponse.checkNullObject(classrooms);
        return classrooms;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Mengambil data seluruh kelas")
    @GetMapping(value="/classroom", produces = {"application/json"})
    public @ResponseBody Page<Classroom> getAllClassrooms(
            @PathVariable(required = false, value = "page") Integer page,
            @PathVariable(required = false,value = "limit") Integer limit,
            @PathVariable(required = false, value = "sort") String sort
    ){
        page = WebResponse.DEFAULT_PAGE_NUM;
        limit = WebResponse.DEFAULT_PAGE_SIZE;
        Pageable pageable = new PageRequest(page,limit);
        Page<Classroom> classrooms = classroomService.getAllClassroom(pageable);
        WebResponse.checkNullObject(classrooms);
        return classrooms;
    }

    @ApiOperation("Menambahkan classroom baru")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/classroom")
    public @ResponseBody Classroom addClassroom(
        @ApiParam("Objek classroom dalam format json") @RequestBody Classroom classroom
    ){
        Classroom result = classroomService.addClassroom(classroom);
        WebResponse.checkNullObject(result);
        return result;
    }

    @ApiOperation("Menghapus classroom tertentu")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/classroom/{idclassroom}")
    public @ResponseBody
    ResponseObject deleteClassroom(
            @ApiParam("Id classroom") @PathVariable("idclassroom") String idClassroom
    ){
        classroomService.deleteClassroom(idClassroom);
        return ResponseObject.builder()
                .message("Classroom berhasil dihapus")
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @ApiOperation("Mengubah data classroom tertentu")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/classroom/{idclassroom}")
    public @ResponseBody Classroom updateClassroom(
            @ApiParam("Id classroom yang akan diubah") @PathVariable("idclassroom") String id,
            @ApiParam("Objek classroom dalam format json")@RequestBody Classroom classroom
    ){
        Classroom result = classroomService.updateClassroom(classroom);
        WebResponse.checkNullObject(result);
        return result;
    }

    @GetMapping("/classroom/enroll/{key}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Mencari kelas berdasarkan kode enroll")
    public @ResponseBody Classroom enrollmentClassroom(
            @ApiParam("Kode enroll berupa String") @PathVariable("key") String enrollmentKey
    ){
        Classroom result = classroomService.searchByEnrollmentKey(enrollmentKey);
        WebResponse.checkNullObject(result);
        return result;
    }

    @ApiOperation("Mendapatkan kelas dari asisten tertentu")
    @GetMapping(value = "/classroom/assistance/{iduser}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Classroom> getClassroomByAssistance(
            @PathVariable("iduser") String idUser
    ){
        List<Classroom> classrooms = classroomService.getByAssistance(idUser);
        WebResponse.checkNullObject(classrooms);
        return classrooms;
    }

    @ApiOperation("Mendapatkan kelas dari praktikan tertentu")
    @GetMapping(value = "/classroom/practican/{iduser}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Classroom> getClassroomByPractican(
            @PathVariable("iduser") String idUser
    ){
        List<Classroom> classrooms = classroomService.getByPractican(idUser);
        WebResponse.checkNullObject(classrooms);
        return classrooms;
    }

}
