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
import ises.rest.entities.dto.GrnDto;
import ises.rest.entities.dto.ModelDto;
import ises.rest.jpa.GrnRepository;
import ises.rest.jpa.ModelStatsRepository;
import ises.rest.jpa.SimulationConfigurationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class GrnController {

    private final SimulationConfigurationRepository simulationRepository;
    private final GrnRepository grnRepository;
    private final ModelStatsRepository modelStatsRepository;

    @GetMapping("/simulation/{id}/grn/ids")
    public ResponseEntity<List<Long>> getAllGrnIdsBySimulationId(@PathVariable("id") Long id) {
        Optional<SimulationConfiguration> configOptional = simulationRepository.findById(id);

        if (configOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(grnRepository.findByConfig(configOptional.get()).stream().map(g -> g.getId())
                .collect(Collectors.toList()), HttpStatus.OK);

    }

    @GetMapping("/simulation/{id}/grn")
    public ResponseEntity<List<GrnDto>> getAllGrnsBySimulationId(@PathVariable("id") Long id) {
        Optional<SimulationConfiguration> configOptional = simulationRepository.findById(id);

        if (configOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(grnRepository.findByConfig(configOptional.get()), HttpStatus.OK);
    }

    @GetMapping("/grn/{id}")
    public ResponseEntity<GrnDto> getGrnById(@PathVariable("id") Long id) {
        Optional<GrnDto> grnOptional = grnRepository.findById(id);

        if (grnOptional.isPresent()) {
            return new ResponseEntity<GrnDto>(grnOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/model/{id}/grn")
    public ResponseEntity<GrnDto> getGrnByModelId(@PathVariable("id") Long id) {
        Optional<ModelDto> modelOptional = modelStatsRepository.findById(id);

        if (modelOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<GrnDto>(grnRepository.findByModelDto(modelOptional.get()), HttpStatus.OK);
    }

}
