package rizki.practicum.learning.controller;
/*
    Created by : Rizki Maulana Akbar, On 02 - 2018 ;
*/

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.service.assignment.AssignmentService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + temp.getFilename());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/file/information/{assignment}/{practican}", produces = {"application/json"})
    @ApiOperation("Mendapatkan informasi file dalam suatu assignment milik praktikan tertentu")
    public @ResponseBody
    List<Document> fileInformation(
            @ApiParam("id assignment dalam format string")
            @PathVariable("assignment") String assignment,
            @ApiParam("id praktikan dalam format string") @PathVariable("practican") String practican
    ) {
        List<Document> document = assignmentService.getDocumentByAssignmentAndPractican(assignment, practican);
        WebResponse.verify(document);
        return document;
    }

    @ApiOperation("Compile & Jalankan file kode program")
    @GetMapping("file/compile/{idDocument}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<String> compileFile(@PathVariable String idDocument) throws IOException {
        List<Document> documents = assignmentService.getAllDocumentsWithinSameAssignment(idDocument);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        RestTemplate restTemplate = new RestTemplate();
        for (Document document : documents) {
            Resource resource = new FileSystemResource(
                    document.getFilename());
            map.add("sourcecode", resource);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<>(map, headers);
        return restTemplate.postForEntity("http://localhost:8080/api/v2/compile", request, String.class);
    }

}
