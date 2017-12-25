package rizki.practicum.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.Practicum;
@Repository
public interface PracticumRepository extends CrudRepository<Practicum, String> {
}
