package rizki.practicum.learning.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rizki.practicum.learning.entity.Role;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.service.role.RoleDefinition;
import rizki.practicum.learning.service.role.RoleService;
import rizki.practicum.learning.service.user.UserService;

import java.util.ArrayList;

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

        ArrayList<Role> mhs = new ArrayList<>();
        ArrayList<Role> assistant = new ArrayList<>();
        ArrayList<Role> coasssistant = new ArrayList<>();

        try {
            mhs.add(roleService.getRole(RoleDefinition.Practican.initial));
            assistant.addAll(mhs);
            assistant.add(roleService.getRole(RoleDefinition.Assistance.initial));
            coasssistant.addAll(assistant);
            coasssistant.add(roleService.getRole(RoleDefinition.CoordinatorAssistance.initial));
        } catch (Exception e) {
            e.printStackTrace();
        }

        User assistant1 = new User();
        assistant1.setName("Asisten 1");
        assistant1.setPassword("asisten1");
        assistant1.setEmail("asisten1");
        assistant1.setIdentity("123456");
        assistant1.setActive(true);
        assistant1.setRole(assistant);
        try {
            userService.createUser(assistant1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        User assistant2 = new User();
        assistant2.setName("Asisten 2");
        assistant2.setPassword("asisten2");
        assistant2.setEmail("asisten2");
        assistant2.setIdentity("1234567");
        assistant2.setActive(true);
        assistant2.setRole(assistant);
        try {
            userService.createUser(assistant2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        User practican1 = new User();
        practican1.setName("Praktikan 1");
        practican1.setPassword("praktikan1");
        practican1.setEmail("praktikan1");
        practican1.setIdentity("12345678");
        practican1.setActive(true);
        practican1.setRole(mhs);
        try {
            userService.createUser(practican1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        User practican2 = new User();
        practican2.setName("Praktikan 2");
        practican2.setPassword("praktikan2");
        practican2.setEmail("praktikan2");
        practican2.setIdentity("123456789");
        practican2.setActive(true);
        practican2.setRole(mhs);
        try {
            userService.createUser(practican2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        User coas1 = new User();
        coas1.setName("Koordinator Asisten 1");
        coas1.setPassword("koas1");
        coas1.setEmail("koas1");
        coas1.setIdentity("1234567890");
        coas1.setActive(true);
        coas1.setRole(coasssistant);
        try {
            userService.createUser(coas1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        User coas2 = new User();
        coas2.setName("Koordinator Asisten 2");
        coas2.setPassword("koas2");
        coas2.setEmail("koas2");
        coas2.setIdentity("12345678901");
        coas2.setActive(true);
        coas2.setRole(coasssistant);
        try {
            userService.createUser(coas2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {

    }
}
