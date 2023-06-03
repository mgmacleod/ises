package ises.rest.entities.dto;

import java.math.BigDecimal;

import org.jgrapht.graph.DefaultWeightedEdge;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ises.model.network.GeneRegulatoryNetwork;
import ises.system.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "grn_edge")
public class GrnEdgeDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "grn_edge_id")
	@EqualsAndHashCode.Exclude
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grn_id", nullable = false)
	@JsonIgnore
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private GrnDto grn;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "source_vertex_id", nullable = false)
	private GrnVertexDto source;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "target_vertex_id", nullable = false)
	private GrnVertexDto target;

	@Column(name = "weight", nullable = false)
	private BigDecimal weight;

	public GrnEdgeDto(GeneRegulatoryNetwork grn, DefaultWeightedEdge edge, GrnDto grnDto) {
		source = new GrnVertexDto(grn.getEdgeSource(edge), grnDto);
		target = new GrnVertexDto(grn.getEdgeTarget(edge), grnDto);
		weight = new BigDecimal(String.format(Constants.BIG_DECIMAL_FORMAT_STRING, grn.getEdgeWeight(edge)));
		this.grn = grnDto;
	}

}
