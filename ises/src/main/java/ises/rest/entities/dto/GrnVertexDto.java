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

@Entity
@Table(name = "grn_vertex")
public class GrnVertexDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "grn_vertex_id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "grn_id")
	@JsonIgnore
	private GrnDto grn;

	public GrnVertexDto() {
	}

	public GrnVertexDto(GrnVertex vertex, GrnDto grn) {
		name = vertex.getName();
		type = vertex.getType().name();
		this.grn = grn;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public GrnDto getGrn() {
		return grn;
	}

	public void setGrn(GrnDto grn) {
		this.grn = grn;
	}

}
