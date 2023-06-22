package ises.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public Optional<UserEntity> findByUsername(String username);

}
