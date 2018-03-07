package rizki.practicum.learning.controller;
/*
    Created by : Rizki Maulana Akbar, On 03 - 2018 ;
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rizki.practicum.learning.dto.MyUserDetails;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class TF {

    final Logger g = LoggerFactory.getLogger(TF.class);

    @GetMapping("/test")
    public String test () {
        return "K";
    }
}
