package rizki.practicum.learning.service.assignment;

import org.apache.commons.io.FilenameUtils;
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
    @Qualifier("StorageService")
    private StorageService storageService;

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
    public void deleteAssignment(String idAssignment) {
        assignmentRepository.delete(idAssignment);
    }

    @Override
    public List<Document> fulfillAssignment(String idAssignment, String idPractican, MultipartFile[] file, String idDocument) {
        String extension = FilenameUtils.getExtension(file[0].getOriginalFilename());
        String filename = FilenameUtils.removeExtension(file[0].getOriginalFilename());
        ArrayList<String> doc = null;
        if (idDocument!=null && !idDocument.equals("")) {
            Document temp = documentRepository.findOne(idDocument);
            storageService.delete(temp.getFilename());
            List<Document> other = documentRepository.findAllByAssignment(temp.getAssignment());
            if (temp != null && other != null) {
                other.stream().forEach(o -> {
                    plagiarismContentRepository.deleteAllByDocument1OrDocument2(o, o);
                    documentRepository.delete(o);
                });
            }
        }
        try {
            if (Arrays.asList(FilesLocationConfig.Document.FILE_EXTENSION_ALLOWED).contains(extension)) {
                doc = documentStorageService.store(file, filename);
            } else if (Arrays.asList(FilesLocationConfig.SourceCode.FILE_EXTENSION_ALLOWED).contains(extension)) {
                doc = sourceCodeStorageService.store(file, filename);
            } else if (Arrays.asList(FilesLocationConfig.Image.FILE_EXTENSION_ALLOWED).contains(extension)) {
                doc = imageStorageService.store(file, filename);
            }
        } catch (FileFormatException e) {
            e.printStackTrace();
        }
        return documentCreator(doc, idPractican, idAssignment);
    }

    private List<Document> documentCreator(List<String> filename, String idPractican, String idAssignment) {
        List<Document> documents = new ArrayList<>();
        filename.stream().forEach(
                f-> {
                    Document document = new Document();
                    document.setFilename(f);
                    document.setPractican(userRepository.findOne(idPractican));
                    document.setAssignment(assignmentRepository.findOne(idAssignment));
                    documents.add(documentRepository.save(document));
                }
        );
        return documents;
    }

    @Override
    public List<Document> getAssignmentByTaskPractican(String assignment, String idPractican) {
        return documentRepository.findAllByAssignmentAndPractican(assignmentRepository.findOne(assignment),
                userRepository.findOne(idPractican));
    }

    @Override
    public List<Document> getAssignmentByTaskPractican(String idTask) {
        return null;
    }

    @Override
    public Map<String, List<Document>> getDocumentByClassroom(String idTask, String idClassroom) {
        Task task = taskRepository.findOne(idTask);
        Classroom classroom = classroomRepository.findOne(idClassroom);
        List<Document> documents = null;
        if (idClassroom == null || idClassroom.equalsIgnoreCase("null")) {
            documents = documentRepository.findAllByAssignmentIsIn(task.getAssignments());
        } else {
            documents = documentRepository.findAllByAssignmentIsInAndPracticanIsIn(task.getAssignments(), classroom.getPractican());
        }
        Map<String, List<Document>> result = new HashMap<>();
        for (Document filter : documents) {
            ArrayList<Document> temp = null;
            if (result == null || !result.containsKey(filter.getPractican().getId())) {
                temp = new ArrayList<>();
                temp.add(filter);
            } else {
                temp = (ArrayList<Document>) result.get(filter.getPractican().getId());
                temp.add(filter);
            }
            result.put(filter.getPractican().getId(), temp);
        }
        return result;
    }

    @Override
    public List<Document> getDocumentByAssignmentAndPractican(String assignment, String practican) {
        return documentRepository.findByAssignmentAndPractican(assignmentRepository.findOne(assignment), userRepository.findOne(practican));
    }

    @Override
    public List<Assignment> getAssignmentByTask(String idTask) {
        Task task = taskRepository.findOne(idTask);
        return task.getAssignments();
    }

    @Override
    public Document getDocument(String idDocument) {
        return documentRepository.findOne(idDocument);
    }

    @Override
    public Assignment addAssignment(String idTask, String description, String fileAllowed) {
        Assignment assignment = new Assignment();
        assignment.setDescription(description);
        assignment.setFileAllowed(fileAllowed);
        return assignmentRepository.save(assignment);
    }
}
