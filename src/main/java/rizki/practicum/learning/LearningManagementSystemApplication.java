package rizki.practicum.learning;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rizki.practicum.learning.service.generator.RoleGeneratorServiceImpl;
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
}
