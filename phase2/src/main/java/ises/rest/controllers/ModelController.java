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
        List<ModelDto> models = getModelIdsBySimulationId(id);

        if (models == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<List<Long>>(models.stream().map(m -> m.getId()).collect(Collectors.toList()),
                HttpStatus.OK);

    }

    private List<ModelDto> getModelIdsBySimulationId(Long id) {
        Optional<SimulationConfiguration> configOptional = simulationRepository.findById(id);

        if (configOptional.isEmpty()) {
            return null;
        }

        List<ModelDto> models = modelRepository.findByConfig(configOptional.get());
        return models;
    }

    @GetMapping("/simulation/{id}/model")
    public ResponseEntity<List<ModelDto>> getAllModelsBySimulationId(@PathVariable("id") Long id) {
        List<ModelDto> models = getModelIdsBySimulationId(id);

        if (models == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<List<ModelDto>>(models, HttpStatus.OK);
    }

    @GetMapping("/model/{id}")
    public ResponseEntity<ModelDto> getModelById(@PathVariable("id") Long id) {
        Optional<ModelDto> modelOptional = modelRepository.findById(id);

        if (modelOptional.isPresent()) {
            return new ResponseEntity<ModelDto>(modelOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<ModelDto>(HttpStatus.NOT_FOUND);
        }
    }

}
