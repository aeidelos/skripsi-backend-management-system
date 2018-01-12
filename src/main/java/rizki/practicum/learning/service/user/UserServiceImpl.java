package rizki.practicum.learning.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rizki.practicum.learning.entity.Role;
import rizki.practicum.learning.util.Confirmation;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.UserRepository;

import java.util.List;

@Service
@Validated
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordencoder;

    @Autowired
    private UserRepository userRepository;

    public boolean createUser(User user) {
        if(user.getPassword()!=null
                && user.getEmail()!=null
                && user.getName()!=null
                && user.getIdentity()!=null){
            try{
                user.setPassword(passwordencoder.encode(user.getPassword()));
                userRepository.save(user);
                return true;
            }catch (Exception e){
                return false;
            }
        }else{
            return false;
        }
    }
    @Override
    public boolean updateUser(User user) {
        return false;
    }
    @Override
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getCandidateCoordinatorAssistance(String query) {
        return userRepository.findCandidateCoordinatorAssistance(query);
    }

    @Override
    public List<User> getUserByName(String query) {
        return userRepository.findByName(query);
    }

    @Override
    public User getUser(String id){
        return userRepository.findOne(id);
    }
    public User getUser(User user){
        return userRepository.findOne(user.getId());
    }
    @Override
    public Page<User> getUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
