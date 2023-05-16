package ises.rest.controllers;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import ises.rest.entities.SimulationConfiguration;
import ises.sys.ISES;

@RestController
@RequestScope
public class SimulationController {

	private final ISES ises;
	private final AsyncTaskExecutor executor;

	public SimulationController(ISES ises, AsyncTaskExecutor executor) {
		this.ises = ises;
		this.executor = executor;
	}

	@PostMapping("/simulation/run")
	public String runSimulation(@RequestBody SimulationConfiguration config) {
		ises.init(config);
		executor.execute(ises);

		return "Running";
	}

}
