package ises.rest.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.SimulationConfiguration;
import ises.rest.entities.dto.GrnDto;
import ises.rest.entities.dto.ModelDto;

public interface GrnRepository extends JpaRepository<GrnDto, Long> {

    public List<GrnDto> findByConfig(SimulationConfiguration config);

    public GrnDto findByModelDto(ModelDto modelDto);

}
