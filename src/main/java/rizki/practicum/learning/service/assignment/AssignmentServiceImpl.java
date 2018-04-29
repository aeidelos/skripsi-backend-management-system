package rizki.practicum.learning.service.assignment;

import com.google.common.util.concurrent.AtomicDouble;
import org.apache.commons.io.FilenameUtils;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.FilesLocationConfig;
import rizki.practicum.learning.dto.DocumentPlagiarism;
import rizki.practicum.learning.dto.ExportClassroom;
import rizki.practicum.learning.dto.UserGrade;
import rizki.practicum.learning.entity.*;
import rizki.practicum.learning.repository.*;
import rizki.practicum.learning.service.storage.StorageService;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    @Qualifier("DocumentStorageService")
    private StorageService documentStorageService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    @Qualifier("SourceCodeStorageService")
    private StorageService sourceCodeStorageService;

    @Autowired
    private AnnouncementRepository announcementRepository;

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

    @Override @Transactional
    public List<Document> fulfillAssignment(String idAssignment, String idPractican, MultipartFile[] file, String idDocument) throws FileFormatException {
        String extension = FilenameUtils.getExtension(file[0].getOriginalFilename());
        String filename = FilenameUtils.removeExtension(file[0].getOriginalFilename());
        ArrayList<String> doc = null;
        if (idDocument!=null && !idDocument.equals("")) {
            deletePreviousDocument(idDocument, idPractican);
        }
            if (Arrays.asList(FilesLocationConfig.Document.FILE_EXTENSION_ALLOWED).contains(extension)) {
                doc = documentStorageService.store(file, filename);
            } else if (Arrays.asList(FilesLocationConfig.SourceCode.FILE_EXTENSION_ALLOWED).contains(extension)) {
                doc = sourceCodeStorageService.store(file, filename);
            } else {
                throw new FileFormatException("Format tidak didukung");
            }
        return documentCreator(doc, idPractican, idAssignment);
    }

    public void deletePreviousDocument(String idDocument, String idPractican) {
        Document temp = documentRepository.findOne(idDocument);
        if (storageService!=null) storageService.delete(temp.getFilename());
        List<Document> other = documentRepository.findAllByAssignmentAndPractican(temp.getAssignment(), userRepository.findOne(idPractican));
        if (temp != null && other != null) {
            other.stream().forEach(o -> {
                plagiarismContentRepository.deleteAllByDocument1OrDocument2(o, o);
                documentRepository.delete(o);
            });
        }
    }

    public List<Document> documentCreator(List<String> filename, String idPractican, String idAssignment) {
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
    public Map<String, Map<String, List<DocumentPlagiarism>>> getDocumentByClassroom(String idTask, String idClassroom) {
        Task task = taskRepository.findOne(idTask);
        Classroom classroom = classroomRepository.findOne(idClassroom);
        List<Document> documents = null;
        if (idClassroom == null || idClassroom.equalsIgnoreCase("null")) {
            documents = documentRepository.findAllByAssignmentIsIn(task.getAssignments());
        } else {
            documents = documentRepository.findAllByAssignmentIsInAndPracticanIsIn(task.getAssignments(), classroom.getPractican());
        }
        if (documents == null || documents.size() == 0) {
            return null;
        }
        Map<String, List<DocumentPlagiarism>> result = new HashMap<>();
        for (Document filter : documents) {
            ArrayList<DocumentPlagiarism> temp = null;
            DocumentPlagiarism docPlagTemp = new DocumentPlagiarism(filter,plagiarismContentRepository.findDistinctFirstByDocument1OrDocument2OrderByRateDesc(filter,filter));
            if (result == null || !result.containsKey(filter.getPractican().getId())) {
                temp = new ArrayList<>();
                temp.add(docPlagTemp);
            } else {
                temp = (ArrayList<DocumentPlagiarism>) result.get(filter.getPractican().getId());
                temp.add(docPlagTemp);
            }
            result.put(filter.getPractican().getId(), temp);
        }
        Map<String, Map<String, List<DocumentPlagiarism>>> result1 = new HashMap<>();
        result.forEach((key, value) -> {
            Map<String, List<DocumentPlagiarism>> res = new HashMap<>();
            ArrayList<DocumentPlagiarism> temp = null;
            for (DocumentPlagiarism filter : value) {
                if ( res == null || !res.containsKey(filter.getDocument().getAssignment().getId())) {
                    temp = new ArrayList<>();
                    temp.add(filter);
                } else {
                    temp = (ArrayList<DocumentPlagiarism>) res.get(filter.getDocument().getAssignment().getId());
                    temp.add(filter);
                }
                res.put(filter.getDocument().getAssignment().getId(), temp);
            }
            result1.put(key, res);
        });
        return result1;
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
    public List<Document> getAllDocumentsWithinSameAssignment(String idDocument) {
        Document document = documentRepository.findOne(idDocument);
        return documentRepository.findAllByAssignmentAndPractican(document.getAssignment(), document.getPractican());
    }

    @Override
    public void setGradeAssignment(String idDocument, int grade) {
        Document doc = documentRepository.findOne(idDocument);
        if (doc == null) throw new ResourceNotFoundException("Data tidak ditemukan");
        List<Document> docs = documentRepository.findAllByAssignmentAndPractican(doc.getAssignment(), doc.getPractican());
        docs.forEach(document -> {
            document.setGrade(grade);
            documentRepository.save(document);
        });
    }

    @Override
    public Map<String, Object> getDashboardState(String idUser) {
        User user = userRepository.findOne(idUser);
        Map<String, Object> result = new HashMap<>();
        float plagiarize = documentRepository.countDocumentsByMarkAsPlagiarizedIsTrueAndPractican(userRepository.findOne(idUser));
        float total = documentRepository.count();
        float average_plagiarism = (plagiarize / total);
        result.put("average_plagiarism", average_plagiarism);
        if (user.getRole().contains(roleRepository.findByInitial("kalab"))) {
            int plagiarism_found = documentRepository.countDistinctByMarkAsPlagiarizedIsTrue();
            float plagiarism_rate = plagiarismContentRepository.averagePlagiarismRates();
            int practicum_class = (int) classroomRepository.count();
            result.put("plagiarism_found", plagiarism_found);
            result.put("plagiarism_rate", plagiarism_rate);
            result.put("practicum_class", practicum_class);
        } else {
            List<Practicum> practicums = new ArrayList<>();
            List<Classroom> classrooms = classroomRepository.findAllByPracticanContains(user);
            classrooms.stream().forEach(classroom -> practicums.add(classroom.getPracticum()));
            int activeTask = taskRepository.countTasksByDueDateIsAfterAndClassroom_PracticanContainsOrPracticumIsIn(new Date(), user, practicums);
            int plagiarized = documentRepository.countDocumentsByMarkAsPlagiarizedIsTrueAndPractican(user);
            int classroom = classrooms.size();
            result.put("active_task", activeTask);
            result.put("plagiarized", plagiarized);
            result.put("classroom", classroom);
            List<Announcement> announcements = announcementRepository.getAnnouncementForUser(user);
            result.put("announcement", announcements);
            if (user.getRole().contains(roleRepository.findByInitial("asprak"))) {
                int document_unchecked = documentRepository.countDocumentsByGradeEquals(0.0);
                result.put("document_unchecked", document_unchecked);
            }
        }
        return result;
    }

    @Override
    public List<ExportClassroom> exportGradeClassroom(String idClassroom) {
        List<ExportClassroom> exportClassrooms = new ArrayList<>();
        Classroom classroom = classroomRepository.findOne(idClassroom);
        List<User> users = classroom.getPractican();
        users.stream().sorted(Comparator.comparing(User::getIdentity));
        List<Task> tasks = taskRepository.findAllByClassroom(classroom);
        tasks.stream().sorted(Comparator.comparing(Task::getCreatedDate));
        tasks.stream().forEach(task -> {
            ExportClassroom exportClassroom = new ExportClassroom();
            exportClassroom.setTask(task);
            List<UserGrade> userGrades = new ArrayList<>();
            users.stream().forEach(user -> {
                AtomicDouble value = new AtomicDouble();
                value.set(0);
                task.getAssignments().stream().forEach(assignment -> {
                    List<Document> documents = documentRepository.findAllByAssignmentAndPractican(assignment, user);
                    if (documents.size() > 0) {
                        AtomicDouble temp = new AtomicDouble();
                        temp.set(0);
                        documents.stream().forEach(doc -> {
                            temp.set(temp.get() + doc.getGrade());
                        });
                        value.set((temp.get() + value.get())/documents.size());
                    }
                });
                userGrades.add(UserGrade.builder().user(user).grade((double) value.get() / task.getAssignments().size()).build());
            });
            exportClassroom.setDocuments(userGrades);
            exportClassrooms.add(exportClassroom);
        });
        return exportClassrooms;
    }

    @Override
    public void setStatusPlagiarismDocument(String idDocument, boolean status) {
        Document document = documentRepository.findOne(idDocument);
        document.setMarkAsPlagiarized(status);
        documentRepository.save(document);
    }

    @Override
    public Assignment addAssignment(String idTask, String description, String fileAllowed) {
        Assignment assignment = new Assignment();
        assignment.setDescription(description);
        assignment.setFileAllowed(fileAllowed);
        return assignmentRepository.save(assignment);
    }
}
