package rizki.practicum.learning;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import rizki.practicum.learning.dto.MyUserDetails;
import rizki.practicum.learning.repository.UserRepository;
import rizki.practicum.learning.service.storage.DocumentStorageServiceImpl;
import rizki.practicum.learning.service.storage.ImageStorageServiceImpl;
import rizki.practicum.learning.service.storage.SourceCodeStorageServiceImpl;
import rizki.practicum.learning.service.storage.StorageServiceImpl;

import java.util.concurrent.*;

@SpringBootApplication
@EnableFeignClients
public class MainApplication {

    public static BlockingQueue<Runnable> plagiarismServiceRunnersQueue = new LinkedBlockingDeque<>();


    public static void main(String[] args) {
		ExecutorService exService = Executors.newFixedThreadPool(10);
		Thread mainThread = new Thread(() -> {
		    while (true){
                try {
                    exService.execute(plagiarismServiceRunnersQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
                    mainThread.start();
        SpringApplication.run(MainApplication.class, args);
	}

	@Bean
	InitializingBean prepareData(){
		return() -> {
			// generatorService.populate();
		};
	}

	@Bean
	CommandLineRunner init(StorageServiceImpl storageServiceImpl,
						   SourceCodeStorageServiceImpl sourceCodeStorageService,
						   DocumentStorageServiceImpl documentStorageService,
						   ImageStorageServiceImpl imageStorageService) {
		return (args) -> {
			// storageServiceImpl.deleteAll();
			storageServiceImpl.init();
			// sourceCodeStorageService.deleteAll();
			sourceCodeStorageService.init();
			// documentStorageService.deleteAll();
			documentStorageService.init();
			// imageStorageService.deleteAll();
			imageStorageService.init();
		};
	}

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder,
									  UserRepository userRepository) throws Exception {
		authenticationManagerBuilder
				.userDetailsService(new UserDetailsService() {
					@Override
					public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
						return new MyUserDetails(userRepository.findByEmail(s));
					}
				});
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


//		Thread t = new Thread(() -> {
//			while(true){
//				try {
//					plagiarismServiceRunnersQueue.take().run();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		t.start();