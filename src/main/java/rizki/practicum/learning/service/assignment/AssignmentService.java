package rizki.practicum.learning.service.assignment;

import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.dto.DocumentPlagiarism;
import rizki.practicum.learning.dto.ExportClassroom;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Validated
public interface AssignmentService {
    Assignment addAssignment(String idTask, Assignment assignment) throws Exception;
    List<Assignment> getAssignmentByTask(@NotNull @NotBlank String idTask);

    Assignment addAssignment(@NotBlank @NotNull String idTask, @NotBlank @NotNull String description,@NotBlank @NotNull String fileAllowed);
    void deleteAssignment(@NotNull @NotBlank String idAssignment);
    List<Document> fulfillAssignment(@NotNull @NotBlank String idAssignment, @NotNull String idPractican,
                                     MultipartFile[] file, @Nullable String idDocument) throws FileFormatException;

    List<Document> getAssignmentByTaskPractican(String idTask, String idPractican);

    List<Document> getAssignmentByTaskPractican(String idTask);

    Map<String, Map<String, List<DocumentPlagiarism>>> getDocumentByClassroom(@NotNull String idTask, @Nullable String idClassroom);

    List<Document> getDocumentByAssignmentAndPractican(String assignment, String practican);

    Document getDocument(String idDocument);

    List<Document> getAllDocumentsWithinSameAssignment(@NotNull String idDocument);

    void setGradeAssignment(@NotNull @NotBlank String idDocument, int grade);

    Map<String, Object> getDashboardState(@NotNull String idUser);

    Map<String, Map<String, Object>> getGradeDocumentByClassroom(@NotNull String idTask,
                                                                 @NotNull String idClassroom);

    List<ExportClassroom> exportGradeClassroom(@NotNull @NotBlank String idClassroom);
}
