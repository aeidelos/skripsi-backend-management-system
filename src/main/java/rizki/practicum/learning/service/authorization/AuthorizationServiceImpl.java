package rizki.practicum.learning.service.authorization;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.dto.MyUserDetails;

@Service
public class AuthorizationServiceImpl implements  AuthorizationService {
    @Override
    public MyUserDetails getUserDetails() {
        // get user detail for authorization
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        return myUserDetails;
    }
}
