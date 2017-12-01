package rizki.practicum.learning.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.Routes;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.service.storage.StorageService;
import rizki.practicum.learning.service.user.UserService;
import rizki.practicum.learning.util.Confirmation;
import rizki.practicum.learning.util.response.ResponseWrapper;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ResponseWrapper responseWrapper;

    @PostMapping(Routes.UserRoutes.USER_REGISTER)
    public ResponseEntity<Map<String,Object>> register_user(
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestParam("identity") String identity,
            @RequestParam("photo") MultipartFile photo
            ){

        User newUser = new User();
        newUser.setName(name);
        newUser.setPhoto(null);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setIdentity(identity);
        newUser.setPhoto(photo.getOriginalFilename());
        try{
            Confirmation confirmation = userService.createUser(newUser);
            storageService.store(photo);
            return responseWrapper.restResponseCollectionWrapper(HttpStatus.CREATED,null,
                    Routes.UserRoutes.USER_REGISTER,1,confirmation.getMessage());
        }catch (Exception e){
            return responseWrapper.restResponseCollectionWrapper(HttpStatus.NOT_ACCEPTABLE,null,
                    Routes.UserRoutes.USER_REGISTER,0,null);
        }
    }

    @PostMapping(Routes.UserRoutes.USER_UPDATE)
    public ResponseEntity<Map<String,Object>> updateUser(
            @PathVariable("id") String id,
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("password") String password
    ){
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
            Confirmation confirmation = userService.updateUser(userEdit);
            return responseWrapper.restResponseCollectionWrapper(HttpStatus.OK,
                    null, Routes.UserRoutes.USER_UPDATE,1,confirmation.getMessage());
        } catch (Exception e) {
            return responseWrapper.restResponseCollectionWrapper(HttpStatus.OK,
                    null, Routes.UserRoutes.USER_UPDATE,0,null);
        }
    }
}
