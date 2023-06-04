package ises.rest.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ises.rest.entities.SimulationConfiguration;
import ises.rest.entities.SimulationStatus;
import ises.rest.jpa.SimulationConfigurationRepository;
import ises.system.Constants;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for handling data related to simulation runs. It can also
 * cancel an currently running simulation.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/simulation")
public class SimulationDataController {

    private final SimulationConfigurationRepository configRepository;
    private final JmsTemplate jmsTemplate;

    @PostMapping("/{id}/cancel")
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

    @GetMapping("/{id}")
    public ResponseEntity<SimulationConfiguration> findSimulation(@PathVariable("id") long id) {

        Optional<SimulationConfiguration> configOptional = configRepository.findById(id);

        if (configOptional.isPresent()) {
            return new ResponseEntity<>(configOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/status/{status}")
    public List<SimulationConfiguration> getSimulationsByStatus(@PathVariable("status") SimulationStatus status) {
        return configRepository.findByStatus(status);
    }

    @GetMapping("/status/{status}/id")
    public List<Long> getSimulationIdsByStatus(@PathVariable("status") SimulationStatus status) {
        List<SimulationConfiguration> sims = configRepository.findByStatus(status);

        return sims.stream().map(s -> s.getId()).collect(Collectors.toList());
    }
}
