package rizki.practicum.learning.service.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.entity.Task;
import rizki.practicum.learning.repository.AssignmentRepository;
import rizki.practicum.learning.repository.DocumentRepository;
import rizki.practicum.learning.service.storage.StorageService;
import rizki.practicum.learning.service.task.TaskService;
import rizki.practicum.learning.service.user.UserService;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("DocumentStorageService")
    private StorageService documentStorageService;

    @Autowired
    @Qualifier("SourceCodeStorageService")
    private StorageService sourceCodeStorageService;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public Assignment addAssignment(String idTask, Assignment assignment) throws Exception {
        return assignmentRepository.save(assignment);
    }

    @Override
    public Assignment getAssignment(String idAssigment) throws Exception {
        return assignmentRepository.findOne(idAssigment);
    }

    @Override
    public boolean deleteAssignment(String idAssignment) throws Exception {
        assignmentRepository.delete(idAssignment);
        return assignmentRepository.findOne(idAssignment)==null;
    }

    @Override
    public Document documentAssignment(String idAssignment, MultipartFile file, String idPractican) throws Exception {
        Document document = new Document();
        document.setPractican(userService.getUser(idPractican));
        document.setAssignment(assignmentRepository.findOne(idAssignment));
        String uploaded = null;
        if(document.getAssignment().getFileAllowed().toUpperCase().equals("DOCUMENT")){
            uploaded = documentStorageService.store(file);
        }else if(document.getAssignment().getFileAllowed().toUpperCase().equals("SOURCECODE")){
            uploaded = sourceCodeStorageService.store(file);
        }
        document.setFilename(uploaded);
        return documentRepository.save(document);
    }

    @Override
    public boolean documentAssignmentDelete(String idDocument) throws Exception {
        documentRepository.delete(idDocument);
        return documentRepository.findOne(idDocument) == null;
    }

    @Override
    public List<Assignment> getAssignmentByTask(String idTask) throws Exception {
        return assignmentRepository.findAllByTask(idTask);
    }
}
