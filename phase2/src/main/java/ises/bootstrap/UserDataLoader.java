package ises.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ises.rest.entities.security.UserDto;
import ises.rest.jpa.security.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserDataLoader implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserDataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		if (userRepository.count() <= 0) {
			loadSecurityData();
		}

	}

	private void loadSecurityData() {

		UserDto simUser = new UserDto();
		simUser.setUsername("simulator");
		simUser.setPassword(passwordEncoder.encode("5bfd13c9-c012-4505-be16-32b6845d9863"));
		userRepository.save(simUser);

		log.debug("user loaded: " + userRepository.count());
	}

}
