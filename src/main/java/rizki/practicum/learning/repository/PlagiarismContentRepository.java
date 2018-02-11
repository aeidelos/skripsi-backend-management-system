package rizki.practicum.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.entity.PlagiarismContent;

@Repository
public interface PlagiarismContentRepository extends JpaRepository<PlagiarismContent, String> {
    void deleteAllByDocument1OrDocument2(Document doc_1, Document doc_2);
}
