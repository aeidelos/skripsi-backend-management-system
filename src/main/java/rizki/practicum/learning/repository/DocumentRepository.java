package rizki.practicum.learning.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.*;

import java.util.List;

@Repository
public interface DocumentRepository extends PagingAndSortingRepository<Document, String> {
    List<Document> findAllByAssignmentAndPractican(Assignment assignment, User practican);

    List<Document> findAllByAssignment_Task(Task task);

    Document findByAssignmentAndPractican(Assignment assignment, User practican);

    List<Document> findAllByAssignment(Assignment assignment);
}
