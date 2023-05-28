package ises.rest.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.dto.GrnDto;

public interface GrnRepository extends JpaRepository<GrnDto, Long> {

}
