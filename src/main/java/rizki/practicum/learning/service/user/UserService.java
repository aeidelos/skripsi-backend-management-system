package rizki.practicum.learning.service.user;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import rizki.practicum.learning.entity.User;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface UserService {

    User createUser(@Valid User user);
    User updateUser(@Valid User user);
    User getUser(@NotBlank String id);
    User getUserByEmail(@NotBlank String email);
    List<User> getCandidateCoordinatorAssistance(String query);
    List<User> getCandidateAssistance(String idClassroom, String query);
}
