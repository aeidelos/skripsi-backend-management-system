package rizki.practicum.learning.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rizki.practicum.learning.entity.Role;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.service.role.RoleService;
import rizki.practicum.learning.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Menambahkan pengguna baru")
    public @ResponseBody User register(@ApiParam("Objek User dalam JSON") @RequestBody User user) {
        User newUser = user;
        Role role = roleService.getRole("mhs");
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        newUser.setPhoto(null);
        newUser.setRole(roles);
        User result = userService.createUser(newUser);
        return WebResponse.verify(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Mengubah data pengguna")
    @PutMapping("/user/{id}")
    public @ResponseBody User updateUser(
            @ApiParam("ID user lama") @PathVariable("id") String id,
            @ApiParam("Objek user dalam JSON") @RequestBody User user
    )
    {
        User old = userService.getUser(id);
        if(!old.getId().equals(user.getId())) {
            throw new DataIntegrityViolationException("User tidak sesuai");
        }
        User result = userService.updateUser(user);
        return WebResponse.verify(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Mendapatkan data user dari username/email")
    @PostMapping("/check/user/")
    public @ResponseBody User getCurrentUser(
            @RequestParam(value = "username") String username
    ){
        User result = userService.getUserByEmail(username);
        return WebResponse.verify(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Mengganti password pengguna")
    @PostMapping("/user/password/")
    public @ResponseBody User updatePassword(
            @ApiParam("Password Lama") @RequestParam("password") String password,
            @ApiParam("Password Baru") @RequestParam("newpassword") @Length(min = 8) String newPassword,
            @ApiParam("ID Pengguna") @RequestParam("id") String id
    ){
        User user = userService.getUser(id);
        WebResponse.verify(user);
        if (!passwordEncoder.encode(password).equals(user.getPassword())) {
            throw new DataIntegrityViolationException("Password lama tidak sama");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        User result = userService.updateUser(user);
        return WebResponse.verify(result);
    }


    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Mendapatkan kandidat koordinator asisten")
    @GetMapping(value = "/user/search/coordinator/{query}", produces = {"application/json"})
    public @ResponseBody List<User> getCandidateCoordinateAssistance(
           @ApiParam("Query search") @PathVariable(name = "query") String query
    ){
        List<User> users = userService.getCandidateCoordinatorAssistance(query);
        WebResponse.verify(users);
        return users;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Mendapatkan kandidat asisten")
    @GetMapping(value = "/user/search/assistance/{idclassroom}/{query}", produces = {"application/json"})
    public @ResponseBody List<User> getCandidateAssistance(
            @ApiParam("ID classroom dalam format string")@PathVariable(name ="idclassroom") String idClassroom,
            @ApiParam("Query pencarian") @PathVariable(name = "query") String query
    ){
        List<User> users = userService.getCandidateAssistance(idClassroom,query);
        WebResponse.verify(users);
        return users;
    }

}
