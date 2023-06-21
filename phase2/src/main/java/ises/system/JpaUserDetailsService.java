package ises.system;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ises.jpa.UserRepository;
import ises.rest.entities.security.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("Getting user data from JPA");

		Optional<UserEntity> userOptional = userRepository.findByUsername(username);
		UserEntity user = null;

		if (userOptional.isPresent()) {
			user = userOptional.get();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority("USER");
			Set<SimpleGrantedAuthority> authorities = new HashSet<>();
			authorities.add(sga);

			return new User(user.getUsername(), user.getPassword(), user.getEnabled(), user.getAccountNonExpired(),
					user.getCredentialsNonExpired(),
					user.getAccountNonLocked(), authorities);
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
	}

}
