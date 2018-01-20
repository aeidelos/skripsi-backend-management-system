package rizki.practicum.learning.controller;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.bouncycastle.openssl.PasswordException;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import rizki.practicum.learning.util.response.ResponseBuilder;
import rizki.practicum.learning.util.response.ResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private HttpStatus httpStatus = null;
    private String location = "";
    private int statusResponse = 0;
    private String message = "";
    private Object body = null;

    @Autowired
    private UserService userService;

    @Autowired @Qualifier("ImageStorageService")
    private StorageService storageService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void init(){
        httpStatus = HttpStatus.OK;
        location = "";
        statusResponse = 0;
        message = "";
        body = null;
    }

    private ResponseEntity<Map<String,Object>> response(){
        return new ResponseBuilder()
                .setMessage(message)
                .setLocation(location)
                .setStatusCode(httpStatus)
                .setStatusResponse(statusResponse)
                .setBody(body)
                .build();
    }

    @PostMapping("/user")
    public ResponseEntity<Map<String,Object>> register_user(
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestParam("identity") String identity,
            @RequestParam(value="photo", required=false) MultipartFile photo
            )
    {
        try{
            this.init();
            User newUser = new User();
            newUser.setName(name);
            newUser.setPhoto(null);
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.setIdentity(identity);
            List<Role> newRole = new ArrayList<>();
            newRole.add(roleService.getRole(RoleDefinition.Practican.initial));
            newUser.setRole(newRole);
            if(photo!=null) newUser.setPhoto(storageService.store(photo, email));
            if(userService.createUser(newUser)){
                httpStatus = HttpStatus.CREATED;
                message = "Pengguna berhasil terdaftar";
                statusResponse = 1;
            }else{
                message = "Pengguna gagal ditambahkan";
            }
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            message = "Cek kembali data masukan : "+e.getMessage().toString();
        }catch(DataIntegrityViolationException e){
            e.printStackTrace();
            message = "Silahkan cek kembali NIM dan Email : "+e.getMessage().toString();
        }
        catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
        }
        return this.response();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Map<String,Object>> updateUser(
            @PathVariable("id") String id,
            @RequestBody User user
    )
    {
        this.init();
        location = "/user/{id}";
        try {
            User userEdit = userService.getUser(id);
            userEdit.setName(user.getName());
            if(userService.updateUser(userEdit)){
                httpStatus = HttpStatus.CREATED;
                statusResponse = 1;
                message = ExceptionMessage.User.USER_UPDATED;
                }else{
                httpStatus = HttpStatus.OK;
                message = ExceptionMessage.User.USER_UPDATED_FAIL;
            }
        } catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            message = e.getMessage().toString();
        }
        return this.response();
    }

    @PostMapping("/check/user")
    public ResponseEntity<Map<String,Object>> getCurrentUser(
            @RequestParam("username") String username
    ){
        this.init();
        try{
            User user = userService.getUserByEmail(username);
            httpStatus = HttpStatus.OK;
            statusResponse = 1;
            body = user;
        }catch(Exception e){
            e.printStackTrace();
            httpStatus = HttpStatus.BAD_REQUEST;
            message = e.getMessage().toString();
        }

        return this.response();
    }

    @PostMapping("/user/changePassword/{id}")
    public ResponseEntity<Map<String,Object>> updatePassword(
            @RequestParam("password") String password,
            @RequestParam("newpassword") @Length(min = 8) String newPassword,
            @PathVariable("id") String id
    ){
        this.init();
        location = "/user/changepassword";
        try{
            User user = userService.getUser(id);
            httpStatus = HttpStatus.OK;
            if(false){
                message = "Password tidak cocok";
            }else{
                user.setPassword(newPassword);
                boolean result = userService.updateUser(user);
                if(result){
                    message = "Password berhasil diubah";
                    statusResponse = 1;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            httpStatus = HttpStatus.OK;
            message = e.getMessage().toString();
        }
        return this.response();
    }

    @PostMapping("/user/updatephoto/{id}")
    public ResponseEntity<Map<String,Object>> updatePhoto(
            @RequestParam("photo") MultipartFile photo,
            @PathVariable("id") String id
    ){
        this.init();
        location = "/user/updatephoto";
        try{
            String imageName = storageService.store(photo, id);
            if(imageName != null || imageName != ""){
                User user = userService.getUser(id);
                user.setPhoto(imageName);
                boolean result = userService.updateUser(user);
                if(result){
                    message = "Foto berhasil diubah";
                    statusResponse = 1;
                    Map<String,String> map = new HashMap<>();
                    String path = "media/photo/"+imageName;
                    map.put("path",path);
                    body = map;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            message = e.getMessage().toString();
        }
        return this.response();
    }


    @GetMapping("/user")
    public ResponseEntity<Map<String,Object>> getUsers(
            @PathVariable(required = false, value = "page") Integer page,
            @PathVariable(required = false,value = "limit") Integer limit,
            @PathVariable(required = false, value = "sort") String sort
    ){
        this.init();
        location = "/user";
        Pageable pageable = new PageRequest(page,limit);
        Page<User> users = userService.getUser(pageable);
        Map<String, Object> map = new HashMap<>();
        map.put("users",users);
        body = map;
        statusResponse = 1;
        return this.response();
    }

    @GetMapping("/user/search/coordinator/{query}")
    public ResponseEntity<Map<String,Object>> getCoordinateAssistanceCandidate(
            @PathVariable(name = "query") String query
    ){
        this.init();
        location = "/user/search/coordinator";
        List<User> users = userService.getCandidateCoordinatorAssistance(query);
        Map<String, Object> map = new HashMap<>();
        map.put("users",users);
        body = map;
        statusResponse = 1;
        return this.response();
    }

    @GetMapping("/user/search/byname/{query}")
    public ResponseEntity<Map<String, Object>> getSearchedUser(
            @PathVariable(name = "query") String query
    ){
        this.init();
        location = "/user/search/byname";
        List<User> users = userService.getUserByName(query);
        Map<String, Object> map = new HashMap<>();
        map.put("users",users);
        body = map;
        statusResponse = 1;
        return this.response();
    }




}
