package rizki.practicum.learning.service.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import rizki.practicum.learning.entity.Role;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.exception.ExceptionMessage;
import rizki.practicum.learning.util.Confirmation;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface UserService {

    boolean createUser(@Valid User user);
    boolean updateUser(@Valid User user);
    User getUser(@NotBlank String id);
    User getUser(@Valid User user);
    Page<User> getUser(Pageable pageable) ;
    User getUserByEmail(@NotBlank String email);
    List<User> getCandidateCoordinatorAssistance(String query);
    List<User> getUserByName(String query);

    List<User> getCandidateAssistance(String idClassroom, String query);
}
