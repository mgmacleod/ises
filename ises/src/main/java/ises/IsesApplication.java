package ises;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ises.sys.ISES;

@SpringBootApplication
public class IsesApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsesApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ISES ises) {
		return (args) -> {
			ises.run();
		};
	}
}
