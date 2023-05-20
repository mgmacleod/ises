package ises.rest.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.dto.GrnEdgeDto;

public interface GrnEdgeRepository extends JpaRepository<GrnEdgeDto, Long> {

}
