package ises.rest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ises.rest.entities.dto.GrnDto;
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
        return ResponseEntity.of(
                simulationRepository.findById(id)
                        .map(c -> grnRepository.findByConfig(c)
                                .stream().map(g -> g.getId())
                                .collect(Collectors.toList())));

    }

    @GetMapping("/simulation/{id}/grn")
    public ResponseEntity<List<GrnDto>> getAllGrnsBySimulationId(@PathVariable("id") Long id) {
        return ResponseEntity.of(
                simulationRepository.findById(id)
                        .map(c -> grnRepository.findByConfig(c)));
    }

    @GetMapping("/grn/{id}")
    public ResponseEntity<GrnDto> getGrnById(@PathVariable("id") Long id) {
        return ResponseEntity.of(grnRepository.findById(id));
    }

    @GetMapping("/model/{id}/grn")
    public ResponseEntity<GrnDto> getGrnByModelId(@PathVariable("id") Long id) {
        return ResponseEntity.of(
                modelStatsRepository.findById(id)
                        .map(m -> grnRepository.findByModelDto(m)));

    }

}
