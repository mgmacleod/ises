package ises.rest.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import ises.rest.entities.SimulationConfiguration;
import ises.rest.entities.SimulationStatus;
import ises.rest.jpa.SimulationConfigurationRepository;
import ises.system.Constants;
import ises.system.Evolver;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Basic REST controller for managing simulation runs
 */
@RequiredArgsConstructor
@RestController
@RequestScope
public class SimulationController {

	private final Evolver evolver;
	private final AsyncTaskExecutor simulationRunExecutor;
	private final SimulationConfigurationRepository configRepository;
	private final JmsTemplate jmsTemplate;

	/**
	 * Takes a {@link SimulationConfiguration}; sets its created on and status
	 * values to now and Running, respectively; persists the {@code config}; and
	 * returns the
	 * saved config.
	 * <p>
	 * In a separate thread, it starts an {@link Evolver} and feeds it the config
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
		evolver.initializeForRun(savedConfig);
		simulationRunExecutor.execute(evolver);

		return savedConfig;
	}

	@PostMapping("/simulation/{id}/cancel")
	public ResponseEntity<String> cancelSimulation(@PathVariable("id") long id) {
		Optional<SimulationConfiguration> configOptional = configRepository.findById(id);

		ResponseEntity<String> response = null;

		if (configOptional.isPresent()) {
			response = cancelSimulation(id, configOptional);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;
	}

	private ResponseEntity<String> cancelSimulation(Long id, Optional<SimulationConfiguration> configOptional) {
		ResponseEntity<String> response;
		SimulationConfiguration config = configOptional.get();
		if (config.getStatus() == SimulationStatus.RUNNING) {
			jmsTemplate.convertAndSend(Constants.CANCEL_QUEUE_NAME, id);
			response = new ResponseEntity<>("Simulation " + id + " cancellation requested", HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(
					"Simulation " + id + " has status " + config.getStatus() + " and cannot be cancelled",
					HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@GetMapping("/simulation/{id}")
	public ResponseEntity<SimulationConfiguration> findSimulation(@PathVariable("id") long id) {

		Optional<SimulationConfiguration> configOptional = configRepository.findById(id);

		if (configOptional.isPresent()) {
			return new ResponseEntity<>(configOptional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/simulation/status/{status}")
	public List<SimulationConfiguration> getSimulationsByStatus(@PathVariable("status") SimulationStatus status) {
		return configRepository.findByStatus(status);
	}

	@GetMapping("simulation/status/{status}/id")
	public List<Long> getSimIdsByStatus(@PathVariable("status") SimulationStatus status) {
		List<SimulationConfiguration> sims = configRepository.findByStatus(status);

		return sims.stream().map(s -> s.getId()).collect(Collectors.toList());
	}

}
