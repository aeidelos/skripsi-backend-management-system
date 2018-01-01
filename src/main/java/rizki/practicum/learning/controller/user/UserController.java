package rizki.practicum.learning.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.RoutesConfig;
import rizki.practicum.learning.entity.Role;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.exception.ExceptionMessage;
import rizki.practicum.learning.service.role.RoleDefinition;
import rizki.practicum.learning.service.role.RoleService;
import rizki.practicum.learning.service.storage.StorageService;
import rizki.practicum.learning.service.user.UserService;
import rizki.practicum.learning.util.response.ResponseWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired @Qualifier("ImageStorageService")
    private StorageService storageService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResponseWrapper responseWrapper;

    @PostMapping(RoutesConfig.UserRoutes.USER_REGISTER)
    public ResponseEntity<Map<String,Object>> register_user(
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestParam("identity") String identity,
            @RequestParam(value="photo", required=false) MultipartFile photo
            )
    {
        try{
            User newUser = new User();
            newUser.setName(name);
            newUser.setPhoto(null);
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.setIdentity(identity);
            List<Role> newRole = new ArrayList<>();
            newRole.add(roleService.getRole(RoleDefinition.Practican.initial));
            newUser.setRole(newRole);
            if(userService.createUser(newUser)){
                if(photo!=null) newUser.setPhoto(storageService.store(photo));
                return responseWrapper.restResponseWrapper(HttpStatus.CREATED,null,
                        RoutesConfig.UserRoutes.USER_REGISTER,1, ExceptionMessage.User.USER_CREATED);
            }else{
                if(photo!=null) newUser.setPhoto(storageService.store(photo));
                return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                        RoutesConfig.UserRoutes.USER_REGISTER,0,ExceptionMessage.User.USER_CREATED_FAIL);
            }

        }catch (Exception e){
            return responseWrapper.restResponseWrapper(HttpStatus.NOT_ACCEPTABLE,e.getMessage(),
                    RoutesConfig.UserRoutes.USER_REGISTER,0,ExceptionMessage.User.USER_CREATED_FAIL);
        }
    }

    @PostMapping(RoutesConfig.UserRoutes.USER_UPDATE)
    public ResponseEntity<Map<String,Object>> updateUser(
            @PathVariable("id") String id,
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("password") String password
    )
    {
        try {
            User userEdit = userService.getUser(id);
            if(email!=null){
                userEdit.setEmail(email);
            }
            if(name!=null){
                userEdit.setName(name);
            }
            if(password!=null){
                userEdit.setPassword(password);
            }
            if(userService.updateUser(userEdit)){
                return responseWrapper.restResponseWrapper(HttpStatus.OK,
                        null, RoutesConfig.UserRoutes.USER_UPDATE,1, ExceptionMessage.User.USER_UPDATED);
            }else{
                return responseWrapper.restResponseWrapper(HttpStatus.OK,
                        null, RoutesConfig.UserRoutes.USER_UPDATE,0,ExceptionMessage.User.USER_UPDATED_FAIL);

            }
        } catch (Exception e) {
            return responseWrapper.restResponseWrapper(HttpStatus.OK,
                    e.getMessage(), RoutesConfig.UserRoutes.USER_UPDATE,0,ExceptionMessage.User.USER_UPDATED_FAIL);
        }
    }

    @GetMapping(RoutesConfig.VALIDITY_TOKEN)
    public boolean checkToken(){
        return true;
    }
}
