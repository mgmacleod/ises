package ises.rest.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.dto.GrnVertexDto;

public interface GrnVertexRepository extends JpaRepository<GrnVertexDto, Long> {

}
