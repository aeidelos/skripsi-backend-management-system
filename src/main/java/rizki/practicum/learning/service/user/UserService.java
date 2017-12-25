package rizki.practicum.learning.service.user;

import rizki.practicum.learning.entity.Role;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.exception.ExceptionMessage;
import rizki.practicum.learning.util.Confirmation;

import java.util.List;

public interface UserService {

    public boolean createUser(User user) throws Exception;
    public User login(String email, String password) throws Exception;
    public boolean updateUser(User user) throws Exception;
    public boolean removeUser(String id) throws Exception;
    public User getUser(String id) throws Exception;
    public User getUser(User user) throws Exception;
    public List<User> getUser() throws Exception;
    public User setRole(User user, Role id_role) throws Exception;
    public User removeRole(User user, Role id_role) throws Exception;
}
