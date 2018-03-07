package rizki.practicum.learning.controller;

import com.netflix.ribbon.proxy.annotation.Http;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rizki.practicum.learning.dto.ResponseObject;
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

    @Autowired
    private PracticumService practicumService;

    @Autowired
    private CourseService courseService;

    @ApiOperation("mendapatkan list dari praktikum yang ada")
    @GetMapping(value= "/practicum", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Page<Practicum> getPracticum(
            @PathVariable(required = false, value = "page") Integer page,
            @PathVariable(required = false,value = "limit") Integer limit,
            @PathVariable(required = false, value = "sort") String sort
    ){
        page = WebResponse.DEFAULT_PAGE_NUM;
        limit = WebResponse.DEFAULT_PAGE_SIZE;
        Pageable pageable = new PageRequest(page,limit);
        Page<Practicum> practicums = practicumService.getAllPracticum(pageable);
        WebResponse.checkNullObject(practicums);
        return practicums;
    }

    @ApiOperation("menambahkan praktikum baru")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/practicum")
    public @ResponseBody Practicum addPracticum(
            @ApiParam("Judul praktikum") @RequestParam("practicumname") String practicumName,
            @ApiParam("ID Course dalam String") @RequestParam("idcourse") String idCourse
    ){
        Practicum practicum = new Practicum();
        practicum.setName(practicumName);
        practicum.setCourse(courseService.getCourse(idCourse));
        Practicum result = practicumService.addPracticum(practicum);
        WebResponse.checkNullObject(result);
        return result;
    }

    @ApiOperation("Merubah data praktikum tertentu")
    @PutMapping("/practicum/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Practicum updatePracticum(
            @ApiParam("ID praktikum dalam string") @PathVariable("id") String id,
            @ApiParam("Objek praktikum dalam json") @RequestBody Practicum practicum
    ){
        Practicum temp = practicumService.getPracticum(id);
        WebResponse.checkNullObject(temp);
        Practicum result = practicumService.updatePracticum(practicum);
        WebResponse.checkNullObject(result);
        return result;
    }

    @ApiOperation("Menghapus praktikum tertentu")
    @DeleteMapping("/practicum/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseObject deleteCourse(
            @ApiParam("Id praktikum dalam string") @PathVariable("id") String id
    ){
        Practicum practicum = practicumService.getPracticum(id);
        WebResponse.checkNullObject(practicum);
        practicumService.deletePracticum(practicum);
        return ResponseObject.builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("Praktikum berhasil dihapus")
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Mendapatkan praktikum yang dibawahi koordinator tertentu")
    @GetMapping(value = "/practicum/coordinator/{iduser}" , produces = {"application/json"})
    public @ResponseBody Practicum getPracticumByCoordinatorAssistance(
            @PathVariable("iduser") String idUser
    ){
        Practicum practicum = practicumService.getPracticumByCoordinatorAssistance(idUser);
        WebResponse.checkNullObject(practicum);
        return practicum;
    }
}
