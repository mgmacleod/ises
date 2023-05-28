package ises.rest.jpa.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.security.UserDto;

public interface UserRepository extends JpaRepository<UserDto, Long> {

	public Optional<UserDto> findByUsername(String username);

}
