package ises.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ises.rest.entities.dto.GrnDto;
import ises.rest.entities.dto.ModelDto;
import ises.rest.entities.dto.ShapeDistributionDto;
import ises.rest.jpa.GrnRepository;
import ises.rest.jpa.ModelStatsRepository;
import ises.rest.jpa.ShapeDistributionRepository;

@Component
@Scope("prototype")
public class DataStorageRunner implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(DataStorageRunner.class);

	private final ModelStatsRepository modelStatsRepo;
	private final ShapeDistributionRepository shapeDistroRepo;
	private final GrnRepository grnRepo;

	private ModelDto modelDto;
	private ShapeDistributionDto shapeDistroDto;
	private GrnDto grnDto;

	public DataStorageRunner(ModelStatsRepository modelStatsRepo, ShapeDistributionRepository shapeDistroRepo, GrnRepository grnRepo) {
		this.modelStatsRepo = modelStatsRepo;
		this.shapeDistroRepo = shapeDistroRepo;
		this.grnRepo = grnRepo;
	}

	@Override
	public void run() {
		synchronized (this) {
			if (modelDto == null || shapeDistroDto == null || grnDto == null) {
				logger.warn("Invalid data provided; cannot proceed");
				return;
			}

			try {
				modelStatsRepo.save(modelDto);
				shapeDistroRepo.save(shapeDistroDto);
				grnRepo.save(grnDto);
			} catch (Exception e) {
				logger.error("Unable to save simulation data", e);
			}
		}
	}

	public void initForRun(ModelDto modelDto, ShapeDistributionDto shapeDistroDto, GrnDto grnDto) {
		this.modelDto = modelDto;
		this.shapeDistroDto = shapeDistroDto;
		this.grnDto = grnDto;
	}

}
