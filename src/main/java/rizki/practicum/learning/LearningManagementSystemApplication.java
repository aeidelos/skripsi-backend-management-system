package rizki.practicum.learning;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import rizki.practicum.learning.entity.MyUserDetails;
import rizki.practicum.learning.repository.UserRepository;
import rizki.practicum.learning.service.generator.RoleGeneratorServiceImpl;
import rizki.practicum.learning.service.generator.UserGeneratorServiceImpl;
import rizki.practicum.learning.service.storage.StorageServiceImpl;

@SpringBootApplication
public class LearningManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningManagementSystemApplication.class, args);
	}

	@Bean
	InitializingBean prepareData(){
		return() -> {
			roleGeneratorServiceImpl.populate();
			userGeneratorServiceImpl.populate();
		};
	}

	@Bean
	CommandLineRunner init(StorageServiceImpl storageServiceImpl) {
		return (args) -> {
			storageServiceImpl.deleteAll();
			storageServiceImpl.init();
		};
	}

	@Autowired
	private RoleGeneratorServiceImpl roleGeneratorServiceImpl;

	@Autowired
	private UserGeneratorServiceImpl userGeneratorServiceImpl;

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder,
									  UserRepository userRepository) throws Exception {
		authenticationManagerBuilder
				.userDetailsService(s -> new MyUserDetails(userRepository.findByEmail(s)));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("PUT","POST","GET","PATCH","DELETE","OPTIONS");
			}
		};
	}

	@Bean
	public FilterRegistrationBean customCorsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}

}
