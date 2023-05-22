package ises.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ises.rest.entities.security.Authority;
import ises.rest.entities.security.User;
import ises.rest.jpa.security.AuthorityRepository;
import ises.rest.jpa.security.UserRepository;

@Component
public class UserDataLoader implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(UserDataLoader.class);

	private final AuthorityRepository authorityRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserDataLoader(AuthorityRepository authorityRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.authorityRepository = authorityRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {
//		if (userRepository.count() == 0) {
		loadSecurityData();
//		}

	}

	private void loadSecurityData() {
		Authority auth = new Authority();
		auth.setRole("USER");
		authorityRepository.save(auth);

		User simUser = new User();
		simUser.setUsername("simulator");
		simUser.setPassword(passwordEncoder.encode("5bfd13c9-c012-4505-be16-32b6845d9863"));
		simUser.addAuthority(auth);
		userRepository.save(simUser);

		logger.debug("user loaded: " + userRepository.count());
	}

}
