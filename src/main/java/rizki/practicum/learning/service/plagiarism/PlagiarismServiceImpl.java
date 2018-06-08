package rizki.practicum.learning.service.plagiarism;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.MainApplication;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.repository.DocumentRepository;

import java.util.List;

@Service
public class PlagiarismServiceImpl implements PlagiarismService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private PlagiarismServiceRunners plagiarismServiceRunners;

    @Override
    public void checkPlagiarism(List<Document> documents) {
        plagiarismServiceRunners.setDocument(documents);
        // create object runners and add to queue
        MainApplication.plagiarismServiceRunnersQueue.add(plagiarismServiceRunners);
    }
}
