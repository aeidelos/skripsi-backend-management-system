package rizki.practicum.learning;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rizki.practicum.learning.service.generator.RoleGeneratorService;
import rizki.practicum.learning.service.storage.StorageService;
import rizki.practicum.learning.service.storage.StorageServiceInterface;

@SpringBootApplication
public class LearningManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningManagementSystemApplication.class, args);
	}

	@Bean
	InitializingBean prepareData(){
		return() -> {
			roleGeneratorService.populate();
		};
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

	@Autowired
	private RoleGeneratorService roleGeneratorService;
}
