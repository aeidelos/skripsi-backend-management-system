package rizki.practicum.learning.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import rizki.practicum.learning.service.user.UserServiceImpl;

public class UserGeneratorServiceImpl implements GeneratorService {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public void populate() {

    }

    @Override
    public void destroy() {

    }
}
