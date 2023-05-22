package ises.rest.jpa.security;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.security.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);

}
