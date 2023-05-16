package ises;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IsesApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsesApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(ISES ises) {
//		return (args) -> {
//			ises.init(new SimulationConfiguration());
//			ises.run();
//		};
//	}
}
