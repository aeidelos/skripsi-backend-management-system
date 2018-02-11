package rizki.practicum.learning.service.plagiarism;

import info.debatty.java.stringsimilarity.JaroWinkler;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.LearningManagementSystemApplication;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.entity.PlagiarismContent;
import rizki.practicum.learning.repository.DocumentRepository;
import rizki.practicum.learning.repository.PlagiarismContentRepository;
import rizki.practicum.learning.service.assignment.AssignmentService;
import rizki.practicum.learning.service.storage.StorageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PlagiarismServiceImpl implements PlagiarismService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    @Qualifier("DocumentStorageService")
    private StorageService storageService;

    @Autowired
    private PlagiarismServiceRunners plagiarismServiceRunners;

    @Autowired
    private PlagiarismContentRepository plagiarismContentRepository;

    @Override
    public void checkPlagiarism(String idDocument) {
        Document document = documentRepository.findOne(idDocument);
        plagiarismServiceRunners.setDocument(document);
        LearningManagementSystemApplication.plagiarismServiceRunnersQueue.add(plagiarismServiceRunners);
    }
}
