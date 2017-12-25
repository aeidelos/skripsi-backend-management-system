package rizki.practicum.learning.service.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.entity.Task;
import rizki.practicum.learning.repository.AssignmentRepository;
import rizki.practicum.learning.repository.DocumentRepository;
import rizki.practicum.learning.service.task.TaskService;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public Assignment addAssignment(String idTask, Assignment assignment) throws Exception {
        Task task = taskService.getTask(idTask);
        List<Assignment> assignments = task.getAssignments();
        Assignment assignmentResult = assignmentRepository.save(assignment);
        assignments.add(assignmentResult);
        taskService.updateTask(task);
        return assignmentResult;
    }

    @Override
    public boolean deleteAssignment(String idAssignment) throws Exception {
        assignmentRepository.delete(idAssignment);
        return assignmentRepository.findOne(idAssignment)==null;
    }

    @Override
    public Document documentAssignment(String idAssignment, Document document) throws Exception {
        document.setAssignment(assignmentRepository.findOne(idAssignment));
        return documentRepository.save(document);
    }

    @Override
    public boolean documentAssignmentDelete(String idDocument) throws Exception {
        documentRepository.delete(idDocument);
        return documentRepository.findOne(idDocument) == null;
    }
}
