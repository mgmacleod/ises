package ises.rest.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return ResponseEntity.of(
                configRepository.findById(id)
                        .filter(c -> c.getStatus() == SimulationStatus.RUNNING)
                        .map(c -> {
                            jmsTemplate.convertAndSend(Constants.CANCEL_QUEUE_NAME, c.getId());
                            return String.format("Simulation %d cancellation requested", c.getId());
                        }).or(() -> Optional
                                .of(String.format("Simulation %d is not running and cannot be cancelled", id))));

    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulationConfiguration> findSimulation(@PathVariable("id") long id) {
        return ResponseEntity.of(configRepository.findById(id));
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
