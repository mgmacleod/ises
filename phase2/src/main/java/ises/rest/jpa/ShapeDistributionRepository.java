package ises.rest.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.dto.ShapeDistributionDto;

public interface ShapeDistributionRepository extends JpaRepository<ShapeDistributionDto, Long> {

}
