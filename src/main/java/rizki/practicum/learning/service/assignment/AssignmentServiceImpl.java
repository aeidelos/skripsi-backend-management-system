package rizki.practicum.learning.service.assignment;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.FilesLocationConfig;
import rizki.practicum.learning.entity.*;
import rizki.practicum.learning.repository.*;
import rizki.practicum.learning.service.storage.StorageService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    @Qualifier("DocumentStorageService")
    private StorageService documentStorageService;

    @Autowired
    @Qualifier("SourceCodeStorageService")
    private StorageService sourceCodeStorageService;

    @Autowired
    @Qualifier("ImageStorageService")
    private StorageService imageStorageService;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private PlagiarismContentRepository plagiarismContentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    public Assignment addAssignment(String idTask, Assignment assignment) throws Exception {
        return assignmentRepository.save(assignment);
    }

    @Override
    public Assignment getAssignment(String idAssigment) {
        return assignmentRepository.findOne(idAssigment);
    }

    @Override
    public void deleteAssignment(String idAssignment) {
        assignmentRepository.delete(idAssignment);
    }

    @Override
    public Document fulfillAssignment(String idAssignment, String idPractican, MultipartFile file) throws FileFormatException, FileUploadException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String doc = null;
        doc = documentStorageService.store(file, idAssignment+"_"+idPractican);
        if(doc!=null || !doc.equalsIgnoreCase("")){
            return documentCreator(doc,idPractican,idAssignment);
        }else{
            return null;
        }
    }

    @Override
    public List<Document> getAssignmentByTaskPractican(String assignment, String idPractican) {
        return documentRepository.findAllByAssignmentAndPractican(assignmentRepository.findOne(assignment),
                userRepository.findOne(idPractican));
    }

    @Override
    public List<Document> getAssignmentByTaskPractican(String idTask) {
        Task task = taskRepository.findOne(idTask);
        return documentRepository.findAllByAssignment_Task(task);
    }

    @Override
    public Map<String, List<Document>> getDocumentByClassroom(String idTask, String idClassroom) {
        Task task = taskRepository.findOne(idTask);
        Classroom classroom = classroomRepository.findOne(idClassroom);
        List<Document> documents = documentRepository.findAllByAssignment_Task(task);
        List<Document> filtered =
                documents.stream()
                        .filter(document -> document.getAssignment().getTask().getClassroom().equals(classroom))
                        .collect(Collectors.toList());
        Map<String, List<Document>> result = new HashMap<>();
        for(Document filter: filtered){
            ArrayList<Document> temp = null;
            if(result==null || !result.containsKey(filter.getPractican().getId())){
                temp = new ArrayList<>();
                temp.add(filter);
            }else{
                temp = (ArrayList<Document>) result.get(filter.getPractican().getId());
                temp.add(filter);
            }
            result.put(filter.getPractican().getId(), temp);
        }
        return result;
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
        Assignment assignment = assignmentRepository.findOne(idAssignment);
        User practican = userRepository.findOne(idPractican);
        Document document = documentRepository.findByAssignmentAndPractican(assignment, practican);
        if(document == null) {
            document = new Document();
            document.setAssignment(assignment);
            document.setPractican(practican);
        }else{
            plagiarismContentRepository.deleteAllByDocument1OrDocument2(document, document);
        }
        String uploaded = null;
        if(assignment.getFileAllowed().equalsIgnoreCase(FilesLocationConfig.Document.INITIAL)){
            uploaded = documentStorageService.store(file,idAssignment+"_"+idPractican);
        }else if(assignment.getFileAllowed().equalsIgnoreCase(FilesLocationConfig.SourceCode.INITIAL)){
            uploaded = sourceCodeStorageService.store(file,idAssignment+"_"+idPractican);
        }else if(assignment.getFileAllowed().equalsIgnoreCase(FilesLocationConfig.Image.INITIAL)){
            uploaded = imageStorageService.store(file,idAssignment+"_"+idPractican);
        }else{
            throw new FileFormatException();
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
