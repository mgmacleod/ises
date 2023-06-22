package ises.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.GrnEntity;
import ises.rest.entities.ModelEntity;
import ises.rest.entities.SimulationConfiguration;

public interface GrnRepository extends JpaRepository<GrnEntity, Long> {

    public List<GrnEntity> findByConfig(SimulationConfiguration config);

    public GrnEntity findByModelDto(ModelEntity modelDto);

}
