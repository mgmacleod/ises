package ises.rest.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.dto.ModelDto;

public interface ModelStatsRepository extends JpaRepository<ModelDto, Long> {

}
