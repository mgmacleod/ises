package ises.rest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ises.rest.entities.dto.ModelDto;
import ises.rest.jpa.ModelStatsRepository;
import ises.rest.jpa.SimulationConfigurationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ModelController {

    private final SimulationConfigurationRepository simulationRepository;
    private final ModelStatsRepository modelRepository;

    @GetMapping("/simulation/{id}/model/ids")
    public ResponseEntity<List<Long>> getAllModelIdsBySimulationId(@PathVariable("id") Long id) {
        return ResponseEntity.of(
                simulationRepository.findById(id)
                        .map(c -> modelRepository.findByConfig(c)
                                .stream().map(m -> m.getId())
                                .collect(Collectors.toList())));
    }

    @GetMapping("/simulation/{id}/model")
    public ResponseEntity<List<ModelDto>> getAllModelsBySimulationId(@PathVariable("id") Long id) {
        return ResponseEntity.of(
                simulationRepository.findById(id)
                        .map(c -> modelRepository.findByConfig(c)));
    }

    @GetMapping("/model/{id}")
    public ResponseEntity<ModelDto> getModelById(@PathVariable("id") Long id) {
        return ResponseEntity.of(modelRepository.findById(id));
    }

}
