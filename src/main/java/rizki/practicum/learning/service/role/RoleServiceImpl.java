package rizki.practicum.learning.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Role;
import rizki.practicum.learning.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getRole() {
        return (ArrayList<Role>) roleRepository.findAll();
    }

    @Override
    public Role getRole(String initial) {
        return roleRepository.findByInitial(initial);
    }
}
