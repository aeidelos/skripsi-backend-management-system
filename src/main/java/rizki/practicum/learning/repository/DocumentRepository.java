package rizki.practicum.learning.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;

import java.util.List;

@Repository
public interface DocumentRepository extends PagingAndSortingRepository<Document, String> {
    List<Document> findAllByAssignment(String idAssignment);
    List<Document> findAllByAssignmentAndPractican(String assignment, String practican);
}
