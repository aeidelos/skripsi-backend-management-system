package rizki.practicum.learning.service.assignment;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface AssignmentService {
    Assignment addAssignment(String idTask, Assignment assignment) throws Exception;
    Assignment getAssignment(String idAssigment) throws Exception;
    boolean deleteAssignment(String idAssignment) throws Exception;
    Document documentAssignment(String idAssignment, MultipartFile file, String idPractican) throws Exception;
    boolean documentAssignmentDelete(String idDocument) throws Exception;
    List<Assignment> getAssignmentByTask(@NotNull @NotBlank String idTask);
}
