package rizki.practicum.learning.repository;

import org.springframework.data.repository.CrudRepository;
import rizki.practicum.learning.entity.Role;


public interface RoleRepository extends CrudRepository<Role, String> {
    Role findByInitial(String initial);
}
