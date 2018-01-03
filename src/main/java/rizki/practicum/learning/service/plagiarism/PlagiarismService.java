package rizki.practicum.learning.service.plagiarism;

import rizki.practicum.learning.entity.Document;

public interface PlagiarismService {
    Document checkPlagiarism(String idDocument) throws Exception;
    void BatchProcessing(String idAssignment) throws Exception;
}
