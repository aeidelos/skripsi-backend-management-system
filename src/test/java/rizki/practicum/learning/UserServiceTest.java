package rizki.practicum.learning;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.UserRepository;
import rizki.practicum.learning.service.UserService;
import rizki.practicum.learning.util.Confirmation;

import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest{

    private static final Logger LOGGER = Logger.getLogger( UserServiceTest.class.getName() );

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private Confirmation confirmation;

    private User user;

    @Before
    public void initConfirmation(){
        this.confirmation = new Confirmation();
        user = new User();
        user.setName("Pre Testing");
        user.setPhoto("Photo");
        user.setPassword("Password");
        user.setEmail("Email");
        user.setIdentity("Identity");
    }
    @Test
    public void contextLoads() {
        userRepository.save(user);
    }
    @Test
    public void createUser(){
        User newUser = new User();
        newUser.setName("New Testing");
        newUser.setPhoto("Photo Testing");
        newUser.setPassword("Password Testing");
        newUser.setEmail("Email Testing");
        newUser.setIdentity("Identity Testing");
        Confirmation createUser = userService.createUser(newUser);
        Assert.assertNotNull(createUser);
        Assert.assertTrue(createUser.isSuccess());
    }
    @Test
    public void createUserDuplicatedEmail(){
        User newUser = new User();
        newUser.setName("New Duplicated Email Testing");
        newUser.setPhoto("Photo Duplicated Email Testing");
        newUser.setPassword("Password Duplicated Email Testing");
        newUser.setEmail("Email");
        newUser.setIdentity("Identity Duplicated Email Testing");
        Confirmation createUser = userService.createUser(newUser);
        Assert.assertNotNull(createUser);
        Assert.assertFalse(createUser.isSuccess());
    }

    @Test
    public void createUserDuplicatedIdentify(){
        User newUser = new User();
        newUser.setName("New Duplicated Identify Testing");
        newUser.setPhoto("Photo Duplicated Identify Testing");
        newUser.setPassword("Password Duplicated Identify Testing");
        newUser.setEmail("Email Duplicated Identify Testing");
        newUser.setIdentity("Identity");
        Confirmation createUser = userService.createUser(newUser);
        Assert.assertNotNull(createUser);
        Assert.assertFalse(createUser.isSuccess());
    }
    @Test
    public void createUserWithSomeNullParameters(){
        User newUser = new User();
        newUser.setName("New Duplicated Testing");
        newUser.setPhoto("Photo Duplicated Testing");
        newUser.setPassword(null);
        newUser.setEmail(null);
        newUser.setIdentity("Identity");
        Confirmation createUser = userService.createUser(newUser);
        Assert.assertNotNull(createUser);
        Assert.assertFalse(createUser.isSuccess());
    }
    @Test
    public void getUser(){
        Confirmation getUser= null ; //userService.getUser(user.getId());
        Assert.assertNotNull(getUser);
        Assert.assertTrue(getUser.isSuccess());
    }
    @Test
    public void getUserWithId(){
        Confirmation getUser= null ; //userService.getUser(user.getId());
        Assert.assertNotNull(getUser);
        Assert.assertTrue(getUser.isSuccess());
    }
    @Test
    public void getUserWithUsernameAndPassword(){
        Confirmation getUser= null ; //userService.getUser(user.getId());
        Assert.assertNotNull(getUser);
        Assert.assertTrue(getUser.isSuccess());
    }
    @Test
    public void getUserWithUserObject(){
        Confirmation getUser= null ; //userService.getUser(user.getId());
        Assert.assertNotNull(getUser);
        Assert.assertTrue(getUser.isSuccess());
    }
    @Test
    public void removeUser(){
        Confirmation removeUser = userService.removeUser(user.getId());
        Assert.assertNotNull(removeUser);
        Assert.assertTrue(removeUser.isSuccess());
    }
    @Test
    public void removeUserIfNullId(){
        Confirmation removeUser = userService.removeUser(null);
        Assert.assertNotNull(removeUser);
        Assert.assertFalse(removeUser.isSuccess());
    }
    @Test
    public void removeUserIfNotFoundId(){
        Confirmation removeUser = userService.removeUser("RandomText");
        Assert.assertNotNull(removeUser);
        Assert.assertFalse(removeUser.isSuccess());
    }
    @Test
    public void updateUser(){
        Assert.assertNotNull(null);
        Assert.assertTrue(false);
    }
    @Test
    public void updateUserWithoutId(){
        Assert.assertNotNull(null);
        Assert.assertFalse(true);
    }
    @Test
    public void updateUserWithSomeNullData(){
        Assert.assertNotNull(null);
        Assert.assertFalse(true);
    }
}
