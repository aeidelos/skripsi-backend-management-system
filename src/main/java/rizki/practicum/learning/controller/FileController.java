package rizki.practicum.learning.controller;
/*
    Created by : Rizki Maulana Akbar, On 02 - 2018 ;
*/

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.codehaus.plexus.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rizki.practicum.learning.dto.CompilerIO;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.proxy.CompilerProxy;
import rizki.practicum.learning.service.assignment.AssignmentService;
import rizki.practicum.learning.util.response.ResponseBuilder;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FileController {

    @Autowired
    private AssignmentService assignmentService;

    @ApiOperation("Download file dalam bentuk byte array")
    @GetMapping("/file/assignment/{document}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ByteArrayResource> fileDownload(
            @ApiParam("Id dokumen dalam bentuk string") @PathVariable("document") String document) throws IOException {
        Document temp = assignmentService.getDocument(document);
        File file = new File(temp.getFilename());
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        HttpHeaders headers = new HttpHeaders(); headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename="+temp.getFilename());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/file/information/{assignment}/{practican}", produces = {"application/json"})
    @ApiOperation("Mendapatkan informasi file dalam suatu assignment milik praktikan tertentu")
    public @ResponseBody List<Document> fileInformation(
            @ApiParam("id assignment dalam format string")
            @PathVariable("assignment")  String assignment,
            @ApiParam("id praktikan dalam format string")@PathVariable("practican") String practican
    ){
        List<Document> document = assignmentService.getDocumentByAssignmentAndPractican(assignment, practican);
        WebResponse.checkNullObject(document);
        return document;
    }
    @ApiOperation("Compile & Jalankan file kode program")
    @GetMapping("file/compile/{id_document}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody CompilerIO compileFile(@PathVariable String idDocument){
                // to be implemented
        CompilerIO result = null;
        result.setIdDocument(idDocument);
        return result;
    }

}
