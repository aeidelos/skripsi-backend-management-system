package rizki.practicum.learning.service.assignment;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.FilesLocationConfig;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.repository.AssignmentRepository;
import rizki.practicum.learning.repository.DocumentRepository;
import rizki.practicum.learning.repository.TaskRepository;
import rizki.practicum.learning.repository.UserRepository;
import rizki.practicum.learning.service.storage.StorageService;

import java.util.Arrays;
import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    @Qualifier("DocumentStorageService")
    private StorageService documentStorageService;

    @Autowired
    @Qualifier("SourceCodeStorageService")
    private StorageService sourceCodeStorageService;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Assignment addAssignment(String idTask, Assignment assignment) throws Exception {
        return assignmentRepository.save(assignment);
    }

    @Override
    public Assignment getAssignment(String idAssigment) throws Exception {
        return assignmentRepository.findOne(idAssigment);
    }

    @Override
    public void deleteAssignment(String idAssignment) {
        assignmentRepository.delete(idAssignment);
    }

    @Override
    public void fulfillAssignment(String idAssignment, String idPractican, MultipartFile file) throws FileFormatException, FileUploadException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String doc = null;
        if(Arrays.asList(FilesLocationConfig.Document.FILE_EXTENSION_ALLOWED).contains(extension)){
            doc = documentStorageService.store(file, idAssignment+"_"+idPractican);
        }else if(Arrays.asList(FilesLocationConfig.SourceCode.FILE_EXTENSION_ALLOWED).contains(extension)){
            doc = sourceCodeStorageService.store(file,idAssignment+"_"+idPractican);
        }
        if(doc!=null || !doc.equalsIgnoreCase("")){
            documentCreator(doc,idPractican,idAssignment);
        }
    }

    @Override
    public List<Document> getAssignmentByTaskPractican(String idTask, String idPractican) {
        return documentRepository.findAllByAssignmentAndPractican(idTask,idPractican);
    }

    @Override
    public List<Document> getAssignmentByTaskPractican(String idTask) {
        return documentRepository.findAllByAssignment(idTask);
    }

    private Document documentCreator(String filename, String idPractican, String idAssignment){
        Document document = new Document();
        document.setFilename(filename);
        document.setPractican(userRepository.findOne(idPractican));
        document.setAssignment(assignmentRepository.findOne(idAssignment));
        return documentRepository.save(document);
    }

    @Override
    public Document documentAssignment(String idAssignment, MultipartFile file, String idPractican) throws Exception {
        Document document = new Document();
        document.setPractican(userRepository.findOne(idPractican));
        document.setAssignment(assignmentRepository.findOne(idAssignment));
        String uploaded = null;
        if(document.getAssignment().getFileAllowed().toUpperCase().equals("DOCUMENT")){
            uploaded = documentStorageService.store(file,idAssignment+"_"+idPractican);
        }else if(document.getAssignment().getFileAllowed().toUpperCase().equals("SOURCECODE")){
            uploaded = sourceCodeStorageService.store(file,idAssignment+"_"+idPractican);
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
    public List<Assignment> getAssignmentByTask(String idTask){
        return assignmentRepository.findAllByTask(taskRepository.findOne(idTask));
    }

    @Override
    public Assignment addAssignment(String idTask, String description, String fileAllowed) {
        Assignment assignment = new Assignment();
        assignment.setDescription(description);
        assignment.setFileAllowed(fileAllowed);
        assignment.setTask(taskRepository.findOne(idTask));
        return assignmentRepository.save(assignment);
    }
}
