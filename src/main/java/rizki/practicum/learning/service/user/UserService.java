package rizki.practicum.learning.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.util.Confirmation;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    public Confirmation createUser(User user) {
        if(user.getPassword()!=null
                && user.getEmail()!=null
                && user.getName()!=null
                && user.getIdentity()!=null){
            try{
                userRepository.save(user);
                return new Confirmation(true,UserServiceMessage.USER_CREATED);
            }catch (Exception e){
                return new Confirmation(false,"Error : "+e);
            }
        }else{
            return new Confirmation(false,UserServiceMessage.USER_NOT_COMPLETED);
        }
    }

    public User login(String email, String password) throws Exception {
        return userRepository.findByEmailAndPassword(email,password);
    }

    public Confirmation updateUser(User user){
        try{
            userRepository.save(user);
            return new Confirmation(true,UserServiceMessage.USER_UPDATED);
        }catch(Exception e){
            return new Confirmation(false, "Error = "+e.getMessage());
        }
    }

    public Confirmation removeUser(String id){
        if(id!=null){
            try{
                userRepository.delete(id);
                return new Confirmation(true, UserServiceMessage.USER_REMOVED);
            }catch (Exception e){
                return new Confirmation(false,"Error : "+e);
            }
        }else{
            return new Confirmation(false,UserServiceMessage.USER_NOT_FOUND);
        }
    }

    public List<User> getUser() throws Exception{
        return (List<User>) userRepository.findAll();
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
