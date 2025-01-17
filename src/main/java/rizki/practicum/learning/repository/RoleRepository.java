package rizki.practicum.learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
    Role findByInitial(String initial);
}
