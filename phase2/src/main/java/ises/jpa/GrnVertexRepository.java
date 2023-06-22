package ises.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.GrnVertexEntity;

public interface GrnVertexRepository extends JpaRepository<GrnVertexEntity, Long> {

}
