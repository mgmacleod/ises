package ises.rest.entities.dto;

import java.util.List;
import java.util.stream.Collectors;

import ises.model.network.GeneRegulatoryNetwork;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "grn")
public class GrnDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "grn_id")
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "grn")
//	@JoinColumn(name = "grn_id")
	private List<GrnVertexDto> vertices;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "grn")
//	@JoinColumn(name = "grn_id")
	private List<GrnEdgeDto> edges;

	public GrnDto() {
	}

	public GrnDto(GeneRegulatoryNetwork grn) {
		name = grn.getName();
		vertices = grn.vertexSet().stream().map(v -> new GrnVertexDto(v, this)).collect(Collectors.toList());
		edges = grn.edgeSet().stream().map(e -> new GrnEdgeDto(grn, e, this)).collect(Collectors.toList());
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

}
