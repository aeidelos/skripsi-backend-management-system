package rizki.practicum.learning.service.user;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import rizki.practicum.learning.entity.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface UserService {

    User createUser(@Valid User user);
    User updateUser(@Valid User user);
    User getUser(@NotBlank @NotNull String id);
    User getUserByEmail(@NotBlank @NotNull String email);
    List<User> getCandidateCoordinatorAssistance(String query);
    List<User> getCandidateAssistance(@NotBlank @NotNull String idClassroom, String query);
}
