package rizki.practicum.learning.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import rizki.practicum.learning.service.user.UserService;

public class AuthenticationController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String hello(){
        return "Hello";
    }

}
