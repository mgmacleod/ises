package ises.rest.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ises.rest.entities.dto.ModelDto;

public interface ModelStatsRepository extends JpaRepository<ModelDto, Long> {

    public List<ModelDto> findBySimId(Long simId);

}
