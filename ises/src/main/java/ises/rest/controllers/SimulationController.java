package ises.rest.controllers;

import java.util.Optional;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import ises.rest.entities.SimulationConfiguration;
import ises.rest.jpa.SimulationConfigurationRepository;
import ises.sys.Evolver;

/**
 * Basic REST controller for managing simulation runs
 */
@RestController
@RequestScope
@RequestMapping("/ises")
public class SimulationController {

	private final Evolver evolver;
	private final AsyncTaskExecutor executor;
	private final SimulationConfigurationRepository configRepository;

	public SimulationController(Evolver evolver, AsyncTaskExecutor executor, SimulationConfigurationRepository configRepository) {
		this.evolver = evolver;
		this.executor = executor;
		this.configRepository = configRepository;
	}

	@PostMapping("/simulation")
	public SimulationConfiguration runSimulation(@RequestBody SimulationConfiguration config) {
		SimulationConfiguration savedConfig = configRepository.save(config);
		evolver.initializeForRun(config);
		executor.execute(evolver);

		return savedConfig;
	}

	@GetMapping("/simulation/{id}")
	public SimulationConfiguration getSimulation(@PathVariable("id") long id) {

		Optional<SimulationConfiguration> configOptional = configRepository.findById(id);

		if (configOptional.isPresent()) {
			return configOptional.get();
		} else {
			return null;
		}

	}

}
