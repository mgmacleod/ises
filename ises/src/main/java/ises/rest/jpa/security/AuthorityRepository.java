package ises.rest.jpa.security;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.security.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
