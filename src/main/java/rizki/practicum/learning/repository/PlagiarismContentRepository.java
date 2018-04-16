package rizki.practicum.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.entity.PlagiarismContent;

@Repository
public interface PlagiarismContentRepository extends JpaRepository<PlagiarismContent, String> {
    void deleteAllByDocument1OrDocument2(Document doc_1, Document doc_2);
    PlagiarismContent findDistinctFirstByDocument1OrDocument2OrderByRateDesc(Document doc1, Document doc2);

    @Query("SELECT AVG(P.rate) FROM PlagiarismContent P")
    float averagePlagiarismRates();
}
