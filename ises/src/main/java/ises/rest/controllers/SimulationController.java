package ises.rest.controllers;

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

	public SimulationController(ISES ises) {
		this.ises = ises;
	}

	@PostMapping("/simulation/run")
	public String runSimulation(@RequestBody SimulationConfiguration config) {
		ises.init(config);
		ises.run();

		return "Done";
	}

}
