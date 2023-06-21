package ises.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.SimulationConfiguration;
import ises.rest.entities.dto.ModelEntity;
import ises.rest.entities.dto.ShapeDistributionEntity;

public interface ShapeDistributionRepository extends JpaRepository<ShapeDistributionEntity, Long> {

    public List<ShapeDistributionEntity> findByConfig(SimulationConfiguration config);

    public ShapeDistributionEntity findByModelDto(ModelEntity modelDto);

}
