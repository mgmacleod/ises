package ises.rest.entities.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ises.model.network.GeneRegulatoryNetwork;
import ises.rest.entities.SimulationConfiguration;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "grn")
public class GrnEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "grn_id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sim_id", nullable = false)
	@ToString.Exclude
	@JsonIgnore
	private SimulationConfiguration config;

	@OneToOne
	@JoinColumn(name = "model_id", nullable = false)
	@ToString.Exclude
	@JsonIgnore
	private ModelEntity modelDto;

	@OneToMany(mappedBy = "grn")
	private List<GrnVertexEntity> vertices;

	@OneToMany(mappedBy = "grn")
	private List<GrnEdgeEntity> edges;

	public GrnEntity(GeneRegulatoryNetwork grn, SimulationConfiguration config, ModelEntity modelDto) {
		name = grn.getName();
		vertices = grn.vertexSet().stream().map(v -> new GrnVertexEntity(v, this)).collect(Collectors.toList());
		edges = grn.edgeSet().stream().map(e -> new GrnEdgeEntity(grn, e, this)).collect(Collectors.toList());
		this.config = config;
		this.modelDto = modelDto;
	}

	public void addVertex(GrnVertexEntity vertex) {
		vertices.add(vertex);
		vertex.setGrn(this);
	}

	public void removeVertex(GrnVertexEntity vertex) {
		vertices.remove(vertex);
		vertex.setGrn(null);
	}

}
