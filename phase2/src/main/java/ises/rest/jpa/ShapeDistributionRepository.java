package ises.rest.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.SimulationConfiguration;
import ises.rest.entities.dto.ShapeDistributionDto;

public interface ShapeDistributionRepository extends JpaRepository<ShapeDistributionDto, Long> {

    public List<ShapeDistributionDto> findByConfig(SimulationConfiguration config);

}
