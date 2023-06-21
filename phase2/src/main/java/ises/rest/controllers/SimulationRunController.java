package ises.rest.controllers;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationContext;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ises.jpa.SimulationConfigurationRepository;
import ises.rest.entities.SimulationConfiguration;
import ises.rest.entities.SimulationStatus;
import ises.system.Evolver;
import ises.system.EvolverImpl1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Basic REST controller for managing simulation runs. This class is responsible
 * only for starting a simulation.
 * 
 * @see ises.rest.controllers.SimulationDataController
 */
@RequiredArgsConstructor
@RestController
public class SimulationRunController {

	private final ApplicationContext applicationContext;
	private final AsyncTaskExecutor simulationRunExecutor;
	private final SimulationConfigurationRepository configRepository;

	/**
	 * Takes a {@link SimulationConfiguration}; sets its created on and status
	 * values to now and Running, respectively; persists the {@code config}; and
	 * returns the
	 * saved config.
	 * <p>
	 * In a separate thread, it starts an {@link Evolver} and feeds it the
	 * config
	 * and makes it go.
	 *
	 * @param config
	 * @return
	 */
	@PostMapping("/simulation")
	public SimulationConfiguration runSimulation(@Valid @RequestBody SimulationConfiguration config) {
		// force created on date to be 'now'
		config.setCreatedOn(LocalDateTime.now());

		// set the status to RUNNING
		config.setStatus(SimulationStatus.RUNNING);

		SimulationConfiguration savedConfig = configRepository.save(config);
		Evolver evolver = applicationContext.getBean(EvolverImpl1.class);
		evolver.initializeForRun(savedConfig);
		simulationRunExecutor.execute(evolver);

		return savedConfig;
	}

}
