package ises.rest.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.dto.GrnVertexEntity;

public interface GrnVertexRepository extends JpaRepository<GrnVertexEntity, Long> {

}
