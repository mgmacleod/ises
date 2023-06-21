package ises.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.SimulationConfiguration;
import ises.rest.entities.SimulationStatus;

public interface SimulationConfigurationRepository extends JpaRepository<SimulationConfiguration, Long> {

	public List<SimulationConfiguration> findByStatus(SimulationStatus status);

}
