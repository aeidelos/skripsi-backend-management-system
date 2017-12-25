package rizki.practicum.learning.service.assignment;

import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;

public interface AssignmentService {
    Assignment addAssignment(String idTask, Assignment assignment) throws Exception;
    boolean deleteAssignment(String idAssignment) throws Exception;
    Document documentAssignment(String idAssignment, Document document) throws Exception;
    boolean documentAssignmentDelete(String idDocument) throws Exception;
}
