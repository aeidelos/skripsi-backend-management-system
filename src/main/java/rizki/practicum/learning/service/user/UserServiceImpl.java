package rizki.practicum.learning.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Role;
import rizki.practicum.learning.util.Confirmation;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean createUser(User user) {
        if(user.getPassword()!=null
                && user.getEmail()!=null
                && user.getName()!=null
                && user.getIdentity()!=null){
            try{
                userRepository.save(user);
                return true;
            }catch (Exception e){
                return false;
            }
        }else{
            return false;
        }
    }

    public User login(String email, String password) throws Exception {
        return userRepository.findByEmailAndPassword(email,password);
    }

    public boolean updateUser(User user) throws Exception{
        try{
            userRepository.save(user);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean removeUser(String id) throws Exception{
        if(id!=null){
            try{
                userRepository.delete(id);
                return true;
            }catch (Exception e){
                return false;
            }
        }else{
            return false;
        }
    }

    public List<User> getUser() throws Exception{
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User setRole(User idUser,Role idRole) throws Exception {
        User user = getUser(idUser);
        List<Role> newRole= user.getRole();
        newRole.add(idRole);
        user.setRole(newRole);
        return userRepository.save(user);
    }

    public User removeRole(User idUser, Role idRole) throws Exception{
        User user = getUser(idUser);
        List<Role> newRole = user.getRole();
        newRole.remove(idRole);
        user.setRole(newRole);
        return userRepository.save(user);
    }

    public User getUser(String id) throws Exception{
        if(id!=null){
            return userRepository.findOne(id);
        }else{
            return null;
        }
    }

    public User getUser(User user){
        return userRepository.findOne(user.getId());
    }


}
