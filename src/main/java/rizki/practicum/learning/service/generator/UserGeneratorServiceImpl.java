package rizki.practicum.learning.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.service.role.RoleService;
import rizki.practicum.learning.service.user.UserService;
import rizki.practicum.learning.service.user.UserServiceImpl;

@Component
public class UserGeneratorServiceImpl implements GeneratorService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public void populate() {
        User defaultUser = new User();
        defaultUser.setName("Rizki Maulana Akbar");
        defaultUser.setPassword("test");
        defaultUser.setEmail("test");
        defaultUser.setIdentity("12345");
        defaultUser.setActive(true);
        defaultUser.setRole(roleService.getRole());
        try {
            userService.createUser(defaultUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
