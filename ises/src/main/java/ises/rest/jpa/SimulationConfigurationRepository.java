package ises.rest.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.SimulationConfiguration;

public interface SimulationConfigurationRepository extends JpaRepository<SimulationConfiguration, Long> {

}
