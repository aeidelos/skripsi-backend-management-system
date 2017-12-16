package rizki.practicum.learning.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.RoutesConfig;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.exception.ExceptionMessage;
import rizki.practicum.learning.service.role.RoleDefinition;
import rizki.practicum.learning.service.user.UserService;
import rizki.practicum.learning.util.response.ResponseWrapper;

import java.util.Map;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseWrapper responseWrapper;

    @PostMapping(RoutesConfig.AuthenticationRoutes.LOGIN)
    public ResponseEntity<Map<String,Object>> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password){
            try {
                User user = userService.login(email,password);
                if(user!=null){
                    return responseWrapper.restResponseWrapper(HttpStatus.OK,user,
                            RoutesConfig.AuthenticationRoutes.LOGIN,1, ExceptionMessage.Auth.LOGIN_SUCCESS);
                }else{
                    return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                            RoutesConfig.AuthenticationRoutes.LOGIN,0, ExceptionMessage.Auth.LOGIN_FAIL);
                }
            } catch (Exception e) {
                return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                        RoutesConfig.AuthenticationRoutes.LOGIN,0, ExceptionMessage.Auth.LOGIN_FAIL +" - "+ e);
            }
    }


}
