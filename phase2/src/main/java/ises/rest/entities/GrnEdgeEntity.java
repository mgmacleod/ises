package ises.rest.entities;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "grn_edge")
public class GrnEdgeEntity {

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
	private GrnEntity grn;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "source_vertex_id", nullable = false)
	private GrnVertexEntity source;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "target_vertex_id", nullable = false)
	private GrnVertexEntity target;

	@Column(name = "weight", nullable = false)
	private BigDecimal weight;

	public GrnEdgeEntity(GeneRegulatoryNetwork grn, DefaultWeightedEdge edge, GrnEntity grnDto) {
		source = new GrnVertexEntity(grn.getEdgeSource(edge), grnDto);
		target = new GrnVertexEntity(grn.getEdgeTarget(edge), grnDto);
		weight = new BigDecimal(String.format(Constants.BIG_DECIMAL_FORMAT_STRING, grn.getEdgeWeight(edge)));
		this.grn = grnDto;
	}

}
