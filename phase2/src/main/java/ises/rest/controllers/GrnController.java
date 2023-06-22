package ises.rest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ises.jpa.GrnRepository;
import ises.jpa.ModelStatsRepository;
import ises.jpa.SimulationConfigurationRepository;
import ises.rest.entities.GrnEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class GrnController {

    private final SimulationConfigurationRepository simulationRepository;
    private final GrnRepository grnRepository;
    private final ModelStatsRepository modelStatsRepository;

    @GetMapping("/simulation/{id}/grn/ids")
    public ResponseEntity<List<Long>> getAllGrnIdsBySimulationId(@PathVariable("id") Long id) {
        return ResponseEntity.of(
                simulationRepository.findById(id)
                        .map(c -> grnRepository.findByConfig(c)
                                .stream().map(g -> g.getId())
                                .collect(Collectors.toList())));

    }

    @GetMapping("/simulation/{id}/grn")
    public ResponseEntity<List<GrnEntity>> getAllGrnsBySimulationId(@PathVariable("id") Long id) {
        return ResponseEntity.of(
                simulationRepository.findById(id)
                        .map(c -> grnRepository.findByConfig(c)));
    }

    @GetMapping("/grn/{id}")
    public ResponseEntity<GrnEntity> getGrnById(@PathVariable("id") Long id) {
        return ResponseEntity.of(grnRepository.findById(id));
    }

    @GetMapping("/model/{id}/grn")
    public ResponseEntity<GrnEntity> getGrnByModelId(@PathVariable("id") Long id) {
        return ResponseEntity.of(
                modelStatsRepository.findById(id)
                        .map(m -> grnRepository.findByModelDto(m)));

    }

}
