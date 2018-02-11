package rizki.practicum.learning.controller;

import com.sun.org.apache.xpath.internal.operations.Mult;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.service.assignment.AssignmentService;
import rizki.practicum.learning.service.plagiarism.PlagiarismService;
import rizki.practicum.learning.util.response.ResponseBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AssignmentController {

    private HttpStatus httpStatus = null;
    private String location = "";
    private int statusResponse = 0;
    private String message = "";
    private Object body = null;

    private void init(){
        httpStatus = HttpStatus.OK;
        location = "";
        statusResponse = 0;
        message = "";
        body = null;
    }

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private PlagiarismService plagiarismService;

    private ResponseEntity<Map<String,Object>> response(){
        return new ResponseBuilder()
                .setMessage(message)
                .setLocation(location)
                .setStatusCode(httpStatus)
                .setStatusResponse(statusResponse)
                .setBody(body)
                .build();
    }

    @GetMapping("/assignment/a/{idtask}")
    public ResponseEntity<Map<String,Object>> getAssignment(
        @PathVariable("idtask") String idTask
    ){
        this.init();
        try{
            List<Assignment> assignments = assignmentService.getAssignmentByTask(idTask);
            statusResponse = 1;
            Map<String, Object> map = new HashMap<>();
            map.put("assignments",assignments);
            body = map;
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = "Assignment not found";
        }
        return this.response();
    }

    @GetMapping("/assignment/p/{idtask}/{idpractican}")
    public ResponseEntity<Map<String,Object>> getAssignmentByPractican(
            @PathVariable("idtask") String idTask,
            @PathVariable("idpractican") String idPractican
    ){
        this.init();
        try{
            List<Document> assignments = assignmentService.getAssignmentByTaskPractican(idTask,idPractican);
            statusResponse = 1;
            Map<String, Object> map = new HashMap<>();
            map.put("assignments",assignments);
            body = map;
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = "Assignment not found";
        }
        return this.response();
    }

    @GetMapping("/assignment/p/{idtask}")
    public ResponseEntity<Map<String,Object>> getFulfilledAssignment(
            @PathVariable("idtask") String idTask
    ){
        this.init();
        try{
            List<Document> assignments = assignmentService.getAssignmentByTaskPractican(idTask);
            statusResponse = 1;
            Map<String, Object> map = new HashMap<>();
            map.put("assignments",assignments);
            body = map;
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = "Assignment not found";
        }
        return this.response();
    }

    @PostMapping("/assignment/a/{idtask}")
    public ResponseEntity<Map<String, Object>> addAssignment(
        @PathVariable("idtask") String idTask,
        @RequestParam("description") String description,
        @RequestParam("fileallowed") String fileAllowed
    ){
        this.init();
        try{
            Assignment assignment = assignmentService.addAssignment(idTask, description, fileAllowed);
            statusResponse = 1;
            httpStatus = HttpStatus.CREATED;
            Map<String, Object> map = new HashMap<>();
            map.put("assignment",assignment);
            body = map;
        }catch(IllegalArgumentException e){
            message = "Gagal menambahkan lampiran :"+e.getMessage().toString();
            e.printStackTrace();
        }
        return this.response();
    }

    @PostMapping("/assignment/p/{idtask}/{idpractican}")
    public ResponseEntity<Map<String, Object>> addAssignmentPractican(
            @PathVariable("idtask") String idTask,
            @PathVariable("idpractican") String idPractican,
            @RequestParam("file") MultipartFile file
            ){
        this.init();
        try{
            Document document = assignmentService.fulfillAssignment(idTask, idPractican, file);
            statusResponse = 1;
            httpStatus = HttpStatus.CREATED;
            plagiarismService.checkPlagiarism(document.getId());
        }catch(IllegalArgumentException e){
            message = "Gagal melakukan upload file :"+e.getMessage().toString();
            e.printStackTrace();
        }catch (FileFormatException e) {
            e.printStackTrace();
            message = "Format file tidak diterima :"+e.getMessage().toString();
        }catch (FileUploadException e) {
            e.printStackTrace();
            message = "Gagal melakukan upload file :"+e.getMessage().toString();
        }
        return this.response();
    }

    @DeleteMapping("/assignment/a/{idassignment}")
    public ResponseEntity<Map<String, Object>> deleteAssignment(
        @PathVariable("idassignment") String idAssignment
    ){
        this.init();
        try{
            assignmentService.deleteAssignment(idAssignment);
            statusResponse = 1;
        }catch(IllegalArgumentException e){
            message = "Gagal menghapus lampiran :"+e.getMessage().toString();
            e.printStackTrace();
        }
        return this.response();
    }

    @GetMapping("/assignment/document/{idtask}/{iduser}")
    public ResponseEntity<Map<String, Object>> checkDocumentAssigned(
            @PathVariable("idassignment") String idTask,
            @PathVariable("iduser") String idUser
    ){
        this.init();
        try{
            List<Document> documents = assignmentService.getAssignmentByTaskPractican(idTask,idUser);
            statusResponse = 1;
            Map<String, Object> map = new HashMap<>();
            map.put("assignment",documents);
            body = map;
        }catch(Exception e){
        }
        return this.response();
    }

    @GetMapping("/assignment/classroom/{idtask}/{idclassroom}")
    public ResponseEntity<Map<String, Object>> checkDocumentClassroom(
            @PathVariable("idassignment") String idTask,
            @PathVariable("idclassroom") String idClassroom
    ){
        this.init();
        try{
            Map<String,List<Document>> assignments = assignmentService.getDocumentByClassroom(idTask, idClassroom);
            statusResponse = 1;
            httpStatus = HttpStatus.CREATED;
            Map<String, Object> map = new HashMap<>();
            map.put("assignments",assignments);
            body = map;
        }catch(Exception e){
        }
        return this.response();
    }


}
