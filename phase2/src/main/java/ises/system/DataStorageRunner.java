package ises.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ises.rest.entities.dto.GrnEdgeEntity;
import ises.rest.entities.dto.GrnEntity;
import ises.rest.entities.dto.GrnVertexEntity;
import ises.rest.entities.dto.ModelEntity;
import ises.rest.entities.dto.ShapeDistributionEntity;
import ises.rest.jpa.GrnEdgeRepository;
import ises.rest.jpa.GrnRepository;
import ises.rest.jpa.GrnVertexRepository;
import ises.rest.jpa.ModelStatsRepository;
import ises.rest.jpa.ShapeDistributionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
@Scope("prototype")
public class DataStorageRunner implements Runnable {

	private final ModelStatsRepository modelRepository;
	private final ShapeDistributionRepository shapeRepository;
	private final GrnRepository grnRepository;
	private final GrnEdgeRepository edgeRepository;
	private final GrnVertexRepository vertexRepository;

	private ModelEntity modelDto;
	private ShapeDistributionEntity shapeDistroDto;
	private GrnEntity grnDto;

	@Override
	public void run() {
		synchronized (this) {
			if (modelDto == null || shapeDistroDto == null || grnDto == null) {
				log.warn("Invalid data provided; cannot proceed");
				return;
			}

			try {
				saveData();
			} catch (Exception e) {
				log.error("Unable to save simulation data", e);
			}
		}
	}

	public void initForRun(ModelEntity modelDto, ShapeDistributionEntity shapeDistroDto, GrnEntity grnDto) {
		this.modelDto = modelDto;
		this.shapeDistroDto = shapeDistroDto;
		this.grnDto = grnDto;
	}

	@Transactional
	public void saveData() {
		// Save the easy stuff first
		modelRepository.save(modelDto);
		shapeRepository.save(shapeDistroDto);

		// Then the messy graph

		// Temporarily clear the vertices and edges
		List<GrnVertexEntity> verticesTemp = new ArrayList<>(grnDto.getVertices());
		List<GrnEdgeEntity> edgesTemp = new ArrayList<>(grnDto.getEdges());

		grnDto.setVertices(new ArrayList<>());
		grnDto.setEdges(new ArrayList<>());

		// Save the graph without the vertices and edges
		grnDto = grnRepository.save(grnDto);

		// Save vertices and map them by their name for further edge linking
		Map<String, GrnVertexEntity> vertexMap = new HashMap<>();
		for (GrnVertexEntity vertex : verticesTemp) {
			vertex.setGrn(grnDto);
			GrnVertexEntity savedVertex = vertexRepository.save(vertex);
			vertexMap.put(savedVertex.getName(), savedVertex);
		}

		// Set source and target vertices for each edge and save them
		for (GrnEdgeEntity edge : edgesTemp) {
			edge.setGrn(grnDto);
			edge.setSource(vertexMap.get(edge.getSource().getName()));
			edge.setTarget(vertexMap.get(edge.getTarget().getName()));
			edgeRepository.save(edge);
		}

		// Now set back the vertices and edges to the graph and save the graph
		grnDto.setVertices(verticesTemp);
		grnDto.setEdges(edgesTemp);
		grnRepository.save(grnDto);

		log.debug("Saved GRN: " + grnDto);
	}

}
