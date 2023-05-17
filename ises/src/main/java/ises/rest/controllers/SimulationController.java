package ises.rest.controllers;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import ises.rest.entities.SimulationConfiguration;
import ises.sys.Evolver;

/**
 * Basic REST controller for managing simulation runs
 */
@RestController
@RequestScope
public class SimulationController {

	private final Evolver evolver;
	private final AsyncTaskExecutor executor;

	public SimulationController(Evolver evolver, AsyncTaskExecutor executor) {
		this.evolver = evolver;
		this.executor = executor;
	}

	@PostMapping("/simulation/run")
	public String runSimulation(@RequestBody SimulationConfiguration config) {
		evolver.initializeForRun(config);
		executor.execute(evolver);

		return "Running";
	}

}
