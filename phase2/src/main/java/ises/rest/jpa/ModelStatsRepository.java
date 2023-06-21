package ises.rest.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.SimulationConfiguration;
import ises.rest.entities.dto.ModelEntity;

public interface ModelStatsRepository extends JpaRepository<ModelEntity, Long> {

    public List<ModelEntity> findByConfig(SimulationConfiguration config);

}
