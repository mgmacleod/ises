package ises.rest.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ises.model.network.GrnVertex;
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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "grn_vertex")
public class GrnVertexDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "grn_vertex_id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "type", nullable = false)
	private String type;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "grn_id", nullable = false)
	@JsonIgnore
	private GrnDto grn;

	public GrnVertexDto(GrnVertex vertex, GrnDto grn) {
		name = vertex.getName();
		type = vertex.getType().name();
		this.grn = grn;
	}

}
