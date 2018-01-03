package rizki.practicum.learning.service.authorization;

import org.springframework.security.core.GrantedAuthority;
import rizki.practicum.learning.entity.MyUserDetails;

import java.util.Collection;

public interface AuthorizationService {
    MyUserDetails getUserDetails();
}
