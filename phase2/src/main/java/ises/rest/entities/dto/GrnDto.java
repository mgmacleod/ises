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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
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
	@ToString.Exclude
	@JsonIgnore
	private SimulationConfiguration config;

	@OneToOne
	@JoinColumn(name = "model_id", nullable = false)
	@ToString.Exclude
	@JsonIgnore
	private ModelDto modelDto;

	@OneToMany(mappedBy = "grn")
	private List<GrnVertexDto> vertices;

	@OneToMany(mappedBy = "grn")
	private List<GrnEdgeDto> edges;

	public GrnDto(GeneRegulatoryNetwork grn, SimulationConfiguration config, ModelDto modelDto) {
		name = grn.getName();
		vertices = grn.vertexSet().stream().map(v -> new GrnVertexDto(v, this)).collect(Collectors.toList());
		edges = grn.edgeSet().stream().map(e -> new GrnEdgeDto(grn, e, this)).collect(Collectors.toList());
		this.config = config;
		this.modelDto = modelDto;
	}

	public void addVertex(GrnVertexDto vertex) {
        vertices.add(vertex);
        vertex.setGrn(this);
    }

    public void removeVertex(GrnVertexDto vertex) {
        vertices.remove(vertex);
        vertex.setGrn(null);
    }

}
