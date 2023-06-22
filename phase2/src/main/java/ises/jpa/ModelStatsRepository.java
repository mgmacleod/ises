package ises.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.ModelEntity;
import ises.rest.entities.SimulationConfiguration;

public interface ModelStatsRepository extends JpaRepository<ModelEntity, Long> {

    public List<ModelEntity> findByConfig(SimulationConfiguration config);

}