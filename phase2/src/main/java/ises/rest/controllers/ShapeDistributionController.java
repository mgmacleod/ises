package ises.rest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ises.rest.entities.dto.ShapeDistributionDto;
import ises.rest.jpa.ModelStatsRepository;
import ises.rest.jpa.ShapeDistributionRepository;
import ises.rest.jpa.SimulationConfigurationRepository;
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
    public ResponseEntity<List<ShapeDistributionDto>> getShapeDistrosBySimulationId(@PathVariable("id") Long id) {
        return ResponseEntity.of(
                simulationRepository.findById(id)
                        .map(c -> shapeRepository.findByConfig(c)));
    }

    @GetMapping("/shape/{id}")
    public ResponseEntity<ShapeDistributionDto> getShapeDistroById(@PathVariable("id") Long id) {
        return ResponseEntity.of(shapeRepository.findById(id));

    }

    @GetMapping("/model/{id}/shape")
    public ResponseEntity<ShapeDistributionDto> getShapeDistroByModelId(@PathVariable("id") Long id) {
        return ResponseEntity.of(
                modelStatsRepository.findById(id)
                        .map(m -> shapeRepository.findByModelDto(m)));
    }

}
