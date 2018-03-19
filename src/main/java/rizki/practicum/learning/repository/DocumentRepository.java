package rizki.practicum.learning.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.*;

import java.util.Collection;
import java.util.List;

@Repository
public interface DocumentRepository extends PagingAndSortingRepository<Document, String> {
    List<Document> findAllByAssignmentAndPractican(Assignment assignment, User practican);

    List<Document> findByAssignmentAndPractican(Assignment assignment, User practican);

    List<Document> findAllByAssignmentIsInAndPracticanIsIn(List<Assignment> assignment, List<User> practican);
    List<Document> findAllByAssignment(Assignment assignment);

    List<Document> findAllByAssignmentIsIn(List<Assignment> assignments);

    int countDocumentsByMarkAsPlagiarizedIsTrue();

    int countDocumentsByMarkAsPlagiarizedIsTrueAndPractican(User user);

    int countDocumentsByGradeEquals(Double grade);

    int countDistinctByMarkAsPlagiarizedIsTrue();

    @Query("SELECT AVG(d.grade) from Document d")
    int averagePlagiarismRates();
}
