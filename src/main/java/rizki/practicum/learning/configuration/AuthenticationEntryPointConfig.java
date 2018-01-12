//package rizki.practicum.learning.configuration;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class AuthenticationEntryPointConfig implements AuthenticationEntryPoint {
//    @Override
//    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        if (e.getClass().getSimpleName().equals("InsufficientAuthenticationException")) {
//            if (httpServletRequest.getHeader("username") != null) {
//                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(httpServletRequest.getHeader("username"), httpServletRequest.getHeader("password"));
//                SecurityContextHolder.getContext().setAuthentication(auth);
//                httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
//            }
//        }
//    }
//}