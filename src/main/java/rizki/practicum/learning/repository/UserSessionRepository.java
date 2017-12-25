package rizki.practicum.learning.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.UserSession;
@Repository
public interface UserSessionRepository extends MongoRepository<UserSession, String> {
}
