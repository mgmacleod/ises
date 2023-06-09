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
import ises.rest.entities.dto.ShapeDistributionDto;
import ises.rest.jpa.ShapeDistributionRepository;
import ises.rest.jpa.SimulationConfigurationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ShapeDistributionController {

    private final SimulationConfigurationRepository simulationRepository;
    private final ShapeDistributionRepository shapeRepository;

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

}
