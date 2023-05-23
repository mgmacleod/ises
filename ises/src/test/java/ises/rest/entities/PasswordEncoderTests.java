package ises.rest.entities;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTests {

	static final String PASSWORD = "5bfd13c9-c012-4505-be16-32b6845d9863";

	@Test
	void testBCrypt() {
		PasswordEncoder bcrypt = new BCryptPasswordEncoder();

		System.out.println(bcrypt.encode(PASSWORD));
		System.out.println(bcrypt.encode(PASSWORD));
	}

}
