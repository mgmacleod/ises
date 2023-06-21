package ises.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.dto.GrnEdgeEntity;

public interface GrnEdgeRepository extends JpaRepository<GrnEdgeEntity, Long> {

}
