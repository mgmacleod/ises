package ises.rest.entities.dto;

import java.math.BigDecimal;

import org.jgrapht.graph.DefaultWeightedEdge;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ises.model.network.GeneRegulatoryNetwork;
import ises.sys.Constants;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "grn_edge")
public class GrnEdgeDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "grn_edge_id")
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "grn_id", nullable = false)
	@JsonIgnore
	private GrnDto grn;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "source_vertex_id", nullable = false)
	private GrnVertexDto source;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "target_vertex_id", nullable = false)
	private GrnVertexDto target;

	@Column(name = "weight", nullable = false)
	private BigDecimal weight;

	public GrnEdgeDto() {
	}

	public GrnEdgeDto(GeneRegulatoryNetwork grn, DefaultWeightedEdge edge, GrnDto grnDto) {
		source = new GrnVertexDto(grn.getEdgeSource(edge), grnDto);
		target = new GrnVertexDto(grn.getEdgeTarget(edge), grnDto);
		weight = new BigDecimal(String.format(Constants.BIG_DECIMAL_FORMAT_STRING, grn.getEdgeWeight(edge)));
		this.grn = grnDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GrnDto getGrn() {
		return grn;
	}

	public void setGrn(GrnDto grn) {
		this.grn = grn;
	}

	public GrnVertexDto getSource() {
		return source;
	}

	public void setSource(GrnVertexDto source) {
		this.source = source;
	}

	public GrnVertexDto getTarget() {
		return target;
	}

	public void setTarget(GrnVertexDto target) {
		this.target = target;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

}
