package rizki.practicum.learning.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.dto.DocumentPlagiarism;
import rizki.practicum.learning.dto.ResponseObject;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.service.assignment.AssignmentService;
import rizki.practicum.learning.service.plagiarism.PlagiarismService;

import java.util.List;
import java.util.Map;

@RestController
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private PlagiarismService plagiarismService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/assignment/a/{idtask}", produces = {"application/json"})
    @ApiOperation(value = "Mendapatkan list assignment yang harus dikerjakan pada satu task")
    public @ResponseBody
    List<Assignment> getAssignment(
            @ApiParam(value = "Id Task dalam format String") @PathVariable("idtask") String idTask
    ) {
        List<Assignment> assignments = assignmentService.getAssignmentByTask(idTask);
        WebResponse.verify(assignments);
        return assignments;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/assignment/p/{idtask}/{idpractican}", produces = {"application/json"})
    @ApiOperation(value = "Mendapatkan list assignment dari task dengan praktikan yang spesifik")
    public @ResponseBody
    List<Document> getAssignmentByPractican(
            @ApiParam(value = "id task dalam format String")
            @PathVariable("idtask") String idTask,
            @ApiParam(value = "id praktikan dalam format String")
            @PathVariable("idpractican") String idPractican
    ) {
        List<Document> assignments = assignmentService.getAssignmentByTaskPractican(idTask, idPractican);
        WebResponse.verify(assignments);
        return assignments;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/assignment/p/{idtask}", produces = {"application/json"})
    @ApiOperation(value = "Mendapatkan list assignment yang sudah dikerjakan")
    public @ResponseBody
    List<Document> getFulfilledAssignment(
            @ApiParam(value = "id task dalam format String") @PathVariable("idtask") String idTask
    ) {
        List<Document> assignments = assignmentService.getAssignmentByTaskPractican(idTask);
        WebResponse.verify(assignments);
        return assignments;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Menambahkan task baru")
    @PostMapping(value = "/assignment/a/{idtask}")
    public @ResponseBody Assignment addAssignment(
            @ApiParam("id task dalam format String" )@PathVariable("idtask") String idTask,
            @ApiParam("deskripsi task")@RequestParam("description") String description,
            @ApiParam("jenis file yang diperbolehkan (document, sourcecode dan images)")
            @RequestParam("fileallowed") String fileAllowed
    ) {
            Assignment assignment = assignmentService.addAssignment(idTask, description, fileAllowed);
            WebResponse.verify(assignment);
            return assignment;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Melakukan upload pengerjaan assignment")
    @PostMapping("/assignment/p/{idassignment}/{idpractican}")
    public @ResponseBody List<Document> addAssignmentFile(
            @ApiParam("Id assignment dalam format string")@PathVariable("idassignment") String idAssignment,
            @ApiParam("Id praktikan dalam url")@PathVariable("idpractican") String idPractican,
            @ApiParam("Id dokumen yang digunakan untuk pengecekan duplikasi upload")
            @RequestParam(value = "document", required = false) String idDocument,
            @ApiParam("File yang diunggah bertipe multipart") @RequestParam("file") MultipartFile ...file
    ) throws FileFormatException {
            List<Document> document = assignmentService.fulfillAssignment(idAssignment, idPractican, file, idDocument);
            WebResponse.verify(document);
            plagiarismService.checkPlagiarism(document);
            return document;

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/assignment/a/{idassignment}")
    @ApiOperation(value = "Menghapus assignment yang sudah ada")
    public @ResponseBody ResponseObject deleteAssignment(
            @ApiParam("Id assignment dalam format String")@PathVariable("idassignment") String idAssignment
    ) {
        assignmentService.deleteAssignment(idAssignment);
        return ResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message("Assignment berhasil dihapus")
                .status(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/assignment/grade/set")
    @ApiOperation("Memberi nilai pada suatu dokumen")
    public @ResponseBody ResponseObject addGradeDocument (
            @ApiParam("Id document dalam string") @RequestParam("idDocument") String idDocument,
            @ApiParam("Nilai") @RequestParam("grade") int grade
    ){
        assignmentService.setGradeAssignment(idDocument,grade);
        return ResponseObject.builder()
                .code(HttpStatus.OK.value())
                .message("Nilai berhasil ditambahkan")
                .status(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "melakukan pengecekan dokumen yang sudah diunggah")
    @GetMapping(value = "/assignment/document/{idtask}/{iduser}", produces = {"application/json"})
    public @ResponseBody List<Document> checkDocumentAssigned(
            @ApiParam("id task dalam format string") @PathVariable("idassignment") String idTask,
            @ApiParam("id user dalam format string") @PathVariable("iduser") String idUser
    ) {
            List<Document> documents = assignmentService.getAssignmentByTaskPractican(idTask, idUser);
            WebResponse.verify(documents);
            return documents;
    }

    @ApiOperation(value = "mengambil data dokumen tugas yang diunggah per kelas")
    @GetMapping(value = "/assignment/classroom/{idtask}/{idclassroom}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Map<String, Map<String, List<DocumentPlagiarism>>> checkDocumentClassroom(
           @ApiParam("id task dalam format String") @PathVariable("idtask") String idTask,
           @ApiParam("id classroom dalam format String")@PathVariable(value = "idclassroom", required = false) String idClassroom
    ) {
            Map<String, Map<String, List<DocumentPlagiarism>>> assignments = assignmentService.getDocumentByClassroom(idTask, idClassroom);
            WebResponse.verify(assignments);
            return assignments;
    }

    @ApiOperation(value = "mengambil data untuk dashboard")
    @GetMapping(value = "/assignment/dashboard/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Object getDashboardState (
            @PathVariable("id") String idUser
    ) {
        return WebResponse.verify(assignmentService.getDashboardState(idUser));
    }


}
