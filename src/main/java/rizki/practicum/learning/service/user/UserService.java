package rizki.practicum.learning.service.user;

import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.util.Confirmation;

import java.util.List;

public interface UserService {

    public Confirmation createUser(User user);
    public User login(String email, String password) throws Exception;
    public Confirmation updateUser(User user);
    public Confirmation removeUser(String id);
    public User getUser(String id) throws Exception;
    public User getUser(User user) throws Exception;
    public List<User> getUser() throws Exception;
}
