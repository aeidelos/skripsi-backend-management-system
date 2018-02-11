package rizki.practicum.learning.service.assignment;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Validated
public interface AssignmentService {
    Assignment addAssignment(String idTask, Assignment assignment) throws Exception;
    Assignment getAssignment(String idAssigment);
    Document documentAssignment(String idAssignment, MultipartFile file, String idPractican) throws Exception;
    boolean documentAssignmentDelete(String idDocument) throws Exception;
    List<Assignment> getAssignmentByTask(@NotNull @NotBlank String idTask);

    Assignment addAssignment(@NotBlank @NotNull String idTask, @NotBlank @NotNull String description,@NotBlank @NotNull String fileAllowed);
    void deleteAssignment(@NotNull @NotBlank String idAssignment);
    Document fulfillAssignment(@NotNull @NotBlank String idTask,@NotNull String idPractican, MultipartFile file) throws FileFormatException, FileUploadException;

    List<Document> getAssignmentByTaskPractican(String idTask, String idPractican);

    List<Document> getAssignmentByTaskPractican(String idTask);

    Map<String, List<Document>> getDocumentByClassroom(String idTask, String idClassroom);
}
