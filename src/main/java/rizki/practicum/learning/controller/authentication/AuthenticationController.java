package rizki.practicum.learning.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import rizki.practicum.learning.service.user.UserServiceInterface;

public class AuthenticationController {

    @Autowired
    private UserServiceInterface userService;


}
