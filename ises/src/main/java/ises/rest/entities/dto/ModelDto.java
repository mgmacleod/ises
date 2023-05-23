package ises.rest.entities.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ises.model.cellular.Model;
import ises.rest.entities.SimulationConfiguration;
import ises.sys.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "model_stats")
public class ModelDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "model_id")
	private Long id;

	@Column(name = "generation", nullable = false)
	private Integer generation;

	@Column(name = "energy", nullable = false)
	private Integer energy;

	@Column(name = "biomass", nullable = false)
	private Integer biomass;

	@Column(name = "stress1", nullable = false)
	private Integer stress1;

	@Column(name = "stress2", nullable = false)
	private Integer stress2;

	@Column(name = "fitness", nullable = false)
	private Integer fitness;

	@Column(name = "highest_energy", nullable = false)
	private Integer highestEnergy;

	@Column(name = "lowest_energy", nullable = false)
	private Integer lowestEnergy;

	@Column(name = "mean_energy", nullable = false)
	private BigDecimal meanEnergy;

	@Column(name = "mean_biomass", nullable = false)
	private BigDecimal meanBiomass;

	@ManyToOne
	@JoinColumn(name = "sim_id", nullable = false)
	@JsonIgnore
	private SimulationConfiguration config;

	public ModelDto() {
	}

	public ModelDto(Model model) {
		energy = model.getEnergy();
		biomass = model.getBiomass();
		stress1 = model.getStress1();
		stress2 = model.getStress2();
		fitness = model.getFitness();
		highestEnergy = model.getHighestEnergy();
		lowestEnergy = model.getLowestEnergy();
		meanEnergy = new BigDecimal(String.format(Constants.BIG_DECIMAL_FORMAT_STRING, model.getMeanEnergy()));
		meanBiomass = new BigDecimal(String.format(Constants.BIG_DECIMAL_FORMAT_STRING, model.getMeanBiomass()));
		config = model.getConfig();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getGeneration() {
		return generation;
	}

	public void setGeneration(Integer generation) {
		this.generation = generation;
	}

	public Integer getEnergy() {
		return energy;
	}

	public void setEnergy(Integer energy) {
		this.energy = energy;
	}

	public Integer getBiomass() {
		return biomass;
	}

	public void setBiomass(Integer biomass) {
		this.biomass = biomass;
	}

	public Integer getStress1() {
		return stress1;
	}

	public void setStress1(Integer stress) {
		stress1 = stress;
	}

	public Integer getStress2() {
		return stress2;
	}

	public void setStress2(Integer stress2) {
		this.stress2 = stress2;
	}

	public Integer getFitness() {
		return fitness;
	}

	public void setFitness(Integer fitness) {
		this.fitness = fitness;
	}

	public Integer getHighestEnergy() {
		return highestEnergy;
	}

	public void setHighestEnergy(Integer highestEnergy) {
		this.highestEnergy = highestEnergy;
	}

	public Integer getLowestEnergy() {
		return lowestEnergy;
	}

	public void setLowestEnergy(Integer lowestEnergy) {
		this.lowestEnergy = lowestEnergy;
	}

	public BigDecimal getMeanEnergy() {
		return meanEnergy;
	}

	public void setMeanEnergy(BigDecimal meanEnergy) {
		this.meanEnergy = meanEnergy;
	}

	public BigDecimal getMeanBiomass() {
		return meanBiomass;
	}

	public void setMeanBiomass(BigDecimal meanBiomass) {
		this.meanBiomass = meanBiomass;
	}

	public SimulationConfiguration getConfig() {
		return config;
	}

	public void setConfig(SimulationConfiguration config) {
		this.config = config;
	}

}
