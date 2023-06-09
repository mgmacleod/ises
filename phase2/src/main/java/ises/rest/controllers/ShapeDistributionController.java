package ises.rest.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ises.rest.entities.SimulationConfiguration;
import ises.rest.entities.dto.ModelDto;
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
        List<ShapeDistributionDto> shapes = getShapeDistrosBySimId(id);

        if (shapes == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<List<Long>>(shapes.stream().map(s -> s.getId()).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    private List<ShapeDistributionDto> getShapeDistrosBySimId(Long id) {
        Optional<SimulationConfiguration> configOptional = simulationRepository.findById(id);

        if (configOptional.isEmpty()) {
            return null;
        }

        return shapeRepository.findByConfig(configOptional.get());
    }

    @GetMapping("/simulation/{id}/shape")
    public ResponseEntity<List<ShapeDistributionDto>> getShapeDistrosBySimulationId(@PathVariable("id") Long id) {
        List<ShapeDistributionDto> shapes = getShapeDistrosBySimId(id);

        if (shapes == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<List<ShapeDistributionDto>>(shapes, HttpStatus.OK);
    }

    @GetMapping("/shape/{id}")
    public ResponseEntity<ShapeDistributionDto> getShapeDistroById(@PathVariable("id") Long id) {
        Optional<ShapeDistributionDto> shapeOptional = shapeRepository.findById(id);

        if (shapeOptional.isPresent()) {
            return new ResponseEntity<>(shapeOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/model/{id}/shape")
    public ResponseEntity<ShapeDistributionDto> getShapeDistroByModelId(@PathVariable("id") Long id) {
        Optional<ModelDto> modelOptional = modelStatsRepository.findById(id);

        if (modelOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(shapeRepository.findByModelDto(modelOptional.get()), HttpStatus.OK);
    }

}
