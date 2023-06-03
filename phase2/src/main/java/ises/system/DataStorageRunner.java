package ises.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ises.rest.entities.dto.GrnDto;
import ises.rest.entities.dto.GrnEdgeDto;
import ises.rest.entities.dto.GrnVertexDto;
import ises.rest.entities.dto.ModelDto;
import ises.rest.entities.dto.ShapeDistributionDto;
import ises.rest.jpa.GrnEdgeRepository;
import ises.rest.jpa.GrnRepository;
import ises.rest.jpa.GrnVertexRepository;
import ises.rest.jpa.ModelStatsRepository;
import ises.rest.jpa.ShapeDistributionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Scope("prototype")
public class DataStorageRunner implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(DataStorageRunner.class);

	private final ModelStatsRepository modelRepository;
	private final ShapeDistributionRepository shapeRepository;
	private final GrnRepository grnRepository;
	private final GrnEdgeRepository edgeRepository;
	private final GrnVertexRepository vertexRepository;

	private ModelDto modelDto;
	private ShapeDistributionDto shapeDistroDto;
	private GrnDto grnDto;

	@Override
	public void run() {
		synchronized (this) {
			if (modelDto == null || shapeDistroDto == null || grnDto == null) {
				logger.warn("Invalid data provided; cannot proceed");
				return;
			}

			try {
				saveData();
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

	@Transactional
	public void saveData() {
		// Save the easy stuff
		modelRepository.save(modelDto);
		shapeRepository.save(shapeDistroDto);
		
		// Then the messy graph
		// Temporarily clear the vertices and edges
		List<GrnVertexDto> verticesTemp = new ArrayList<>(grnDto.getVertices());
		List<GrnEdgeDto> edgesTemp = new ArrayList<>(grnDto.getEdges());
	
		grnDto.setVertices(new ArrayList<>());
		grnDto.setEdges(new ArrayList<>());
	
		// Save the graph without the vertices and edges
		grnDto = grnRepository.save(grnDto);
	
		// Save vertices and map them by their name for further edge linking
		Map<String, GrnVertexDto> vertexMap = new HashMap<>();
		for (GrnVertexDto vertex : verticesTemp) {
			vertex.setGrn(grnDto);
			GrnVertexDto savedVertex = vertexRepository.save(vertex);
			vertexMap.put(savedVertex.getName(), savedVertex);
		}
	
		// Set source and target vertices for each edge and save them
		for (GrnEdgeDto edge : edgesTemp) {
			edge.setGrn(grnDto);
			edge.setSource(vertexMap.get(edge.getSource().getName()));
			edge.setTarget(vertexMap.get(edge.getTarget().getName()));
			edgeRepository.save(edge);
		}
	
		// Now set back the vertices and edges to the graph and save the graph
		grnDto.setVertices(verticesTemp);
		grnDto.setEdges(edgesTemp);
		grnRepository.save(grnDto);
	}

}
