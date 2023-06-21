package ises.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.SimulationConfiguration;
import ises.rest.entities.dto.GrnEntity;
import ises.rest.entities.dto.ModelEntity;

public interface GrnRepository extends JpaRepository<GrnEntity, Long> {

    public List<GrnEntity> findByConfig(SimulationConfiguration config);

    public GrnEntity findByModelDto(ModelEntity modelDto);

}
