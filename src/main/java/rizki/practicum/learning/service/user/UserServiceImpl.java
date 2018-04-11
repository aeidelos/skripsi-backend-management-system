package rizki.practicum.learning.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.repository.ClassroomRepository;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordencoder;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        user.setPassword(passwordencoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getCandidateCoordinatorAssistance(String query) {
        return userRepository.findCandidateCoordinatorAssistance(query);
    }

    @Override
    public List<User> getCandidateAssistance(String idClassroom, String query) {
        List<User> users = userRepository.findByName(query);
        if (idClassroom.equalsIgnoreCase("null") || idClassroom == null) {
            return users;
        } else {
            Classroom classroom = classroomRepository.findOne(idClassroom);
            return users.stream().filter(user -> !classroom.getAssistance().contains(user))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public User getUser(String id) {
        return userRepository.findOne(id);
    }
}
