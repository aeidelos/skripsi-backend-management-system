package rizki.practicum.learning.service.assignment;

import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;

public interface AssignmentService {
    Assignment addAssignment(String idTask, Assignment assignment) throws Exception;
    Assignment getAssignment(String idAssigment) throws Exception;
    boolean deleteAssignment(String idAssignment) throws Exception;
    Document documentAssignment(String idAssignment, MultipartFile file, String idPractican) throws Exception;
    boolean documentAssignmentDelete(String idDocument) throws Exception;
}
