package rizki.practicum.learning.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.Document;
@Repository
public interface DocumentRepository extends PagingAndSortingRepository<Document, String> {
}
