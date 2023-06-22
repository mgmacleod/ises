package ises.rest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ises.jpa.ModelStatsRepository;
import ises.jpa.ShapeDistributionRepository;
import ises.jpa.SimulationConfigurationRepository;
import ises.rest.entities.ShapeDistributionEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ShapeDistributionController {

    private final SimulationConfigurationRepository simulationRepository;
    private final ShapeDistributionRepository shapeRepository;
    private final ModelStatsRepository modelStatsRepository;

    @GetMapping("/simulation/{id}/shape/ids")
    public ResponseEntity<List<Long>> getAllShapeDistroIdsBySimulationId(@PathVariable("id") Long id) {
        return ResponseEntity.of(
                simulationRepository.findById(id)
                        .map(c -> shapeRepository.findByConfig(c)
                                .stream().map(s -> s.getId())
                                .collect(Collectors.toList())));
    }

    @GetMapping("/simulation/{id}/shape")
    public ResponseEntity<List<ShapeDistributionEntity>> getShapeDistrosBySimulationId(@PathVariable("id") Long id) {
        return ResponseEntity.of(
                simulationRepository.findById(id)
                        .map(c -> shapeRepository.findByConfig(c)));
    }

    @GetMapping("/shape/{id}")
    public ResponseEntity<ShapeDistributionEntity> getShapeDistroById(@PathVariable("id") Long id) {
        return ResponseEntity.of(shapeRepository.findById(id));

    }

    @GetMapping("/model/{id}/shape")
    public ResponseEntity<ShapeDistributionEntity> getShapeDistroByModelId(@PathVariable("id") Long id) {
        return ResponseEntity.of(
                modelStatsRepository.findById(id)
                        .map(m -> shapeRepository.findByModelDto(m)));
    }

}
