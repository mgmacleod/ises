package ises.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.ModelEntity;
import ises.rest.entities.ShapeDistributionEntity;
import ises.rest.entities.SimulationConfiguration;

public interface ShapeDistributionRepository extends JpaRepository<ShapeDistributionEntity, Long> {

    public List<ShapeDistributionEntity> findByConfig(SimulationConfiguration config);

    public ShapeDistributionEntity findByModelDto(ModelEntity modelDto);

}
