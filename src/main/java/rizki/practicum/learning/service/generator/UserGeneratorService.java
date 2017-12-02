package rizki.practicum.learning.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import rizki.practicum.learning.service.user.UserService;

public class UserGeneratorService implements GeneratorService {

    @Autowired
    private UserService userService;

    @Override
    public void populate() {

    }

    @Override
    public void destroy() {

    }
}
