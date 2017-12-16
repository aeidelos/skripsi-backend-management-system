package rizki.practicum.learning.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import rizki.practicum.learning.entity.UserSession;

public interface UserSessionRepository extends MongoRepository<UserSession, String> {
}
