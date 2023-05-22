package ises.rest.entities.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ises.model.network.GeneRegulatoryNetwork;
import ises.rest.entities.SimulationConfiguration;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "grn")
public class GrnDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "grn_id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sim_id", nullable = false)
	@JsonIgnore
	private SimulationConfiguration config;

	@OneToOne
	@JoinColumn(name = "model_id", nullable = false)
	@JsonIgnore
	private ModelDto modelDto;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "grn")
	private List<GrnVertexDto> vertices;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "grn")
	private List<GrnEdgeDto> edges;

	public GrnDto() {
	}

	public GrnDto(GeneRegulatoryNetwork grn, SimulationConfiguration config, ModelDto modelDto) {
		name = grn.getName();
		vertices = grn.vertexSet().stream().map(v -> new GrnVertexDto(v, this)).collect(Collectors.toList());
		edges = grn.edgeSet().stream().map(e -> new GrnEdgeDto(grn, e, this)).collect(Collectors.toList());
		this.config = config;
		this.modelDto = modelDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GrnVertexDto> getVertices() {
		return vertices;
	}

	public void setVertices(List<GrnVertexDto> vertices) {
		this.vertices = vertices;
	}

	public List<GrnEdgeDto> getEdges() {
		return edges;
	}

	public void setEdges(List<GrnEdgeDto> edges) {
		this.edges = edges;
	}

	public SimulationConfiguration getConfig() {
		return config;
	}

	public void setConfig(SimulationConfiguration config) {
		this.config = config;
	}

	public ModelDto getModelDto() {
		return modelDto;
	}

	public void setModelDto(ModelDto modelDto) {
		this.modelDto = modelDto;
	}

}
