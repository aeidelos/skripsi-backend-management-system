package rizki.practicum.learning.service.plagiarism;

import rizki.practicum.learning.entity.Document;

import java.util.List;

public interface PlagiarismService {
    void checkPlagiarism(List<Document> idDocument);
}
