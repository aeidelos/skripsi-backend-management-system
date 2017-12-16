package rizki.practicum.learning.repository;

import org.springframework.data.repository.CrudRepository;
import rizki.practicum.learning.entity.Practicum;

public interface PracticumRepository extends CrudRepository<Practicum, String> {
}
