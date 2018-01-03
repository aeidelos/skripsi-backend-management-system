package rizki.practicum.learning.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsUtils;
import rizki.practicum.learning.service.role.RoleDefinition;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        String KALAB = RoleDefinition.HeadLaboratory.initial.toUpperCase();
        String MHS = RoleDefinition.Practican.initial.toUpperCase();
        String ASSISTAN = RoleDefinition.Assistance.initial.toUpperCase();
        String KOAS = RoleDefinition.CoordinatorAssistance.initial.toUpperCase();
        http.authorizeRequests()
                .requestMatchers(CorsUtils::isCorsRequest).permitAll()
                .antMatchers("/oauth").permitAll()
                .antMatchers(RoutesConfig.UserRoutes.USER_UPDATE).authenticated()
                .antMatchers(RoutesConfig.PracticumRoutes.PREFIX+"/**").authenticated()
                .antMatchers(RoutesConfig.VALIDITY_TOKEN).authenticated();
    }
}
