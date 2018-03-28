package rizki.practicum.learning.service.role;

import rizki.practicum.learning.entity.Role;
import rizki.practicum.learning.entity.User;

import java.util.List;

public interface RoleService {
    public List<Role> getRole();
    public Role getRole(String initial);
    public Role getRoleById(String idRole);
}
