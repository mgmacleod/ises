package ises.rest.entities.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ises.rest.entities.SimulationConfiguration;
import ises.stats.ShapeDistribution;
import ises.system.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shape_distro")
public class ShapeDistributionDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "shape_distro_id")
	private Long id;

	@Column(name = "num_shapes", nullable = false)
	private Integer numShapes;

	@Column(name = "most_common_shape", nullable = false)
	private Integer mostCommonShape;

	@Column(name = "Least_common_shape", nullable = false)
	private Integer LeastCommonShape;

	@Column(name = "highest_freq", nullable = false)
	private Integer highestFreq;

	@Column(name = "lowest_freq", nullable = false)
	private Integer lowestFreq;

	@Column(name = "num_populated", nullable = false)
	private Integer numPopulated;

	@Column(name = "num_unpopulated", nullable = false)
	private Integer numUnpopulated;

	@Column(name = "population_ratio", nullable = false)
	private BigDecimal populationRatio;

	@Column(name = "mean_shape", nullable = false)
	private BigDecimal meanShape;

	@Column(name = "mean_freq", nullable = false)
	private BigDecimal meanFreq;

	@Column(name = "shape_std_deviation", nullable = false)
	private BigDecimal shapeStdDeviation;

	@Column(name = "frequency_std_deviation", nullable = false)
	private BigDecimal frequencyStdDeviation;

	@ManyToOne
	@JoinColumn(name = "sim_id", nullable = false)
	@JsonIgnore
	private SimulationConfiguration config;

	@OneToOne
	@JoinColumn(name = "model_id", nullable = false)
	@JsonIgnore
	private ModelDto modelDto;

	public ShapeDistributionDto() {
	}

	public ShapeDistributionDto(ShapeDistribution sd, ModelDto modelDto) {
		config = sd.getConfig();
		numShapes = sd.getNumEntries();
		mostCommonShape = sd.getMcShape();
		LeastCommonShape = sd.getLcShape();
		highestFreq = sd.getHighestFreq();
		lowestFreq = sd.getLowestFreq();
		numPopulated = sd.getNumPopulated();
		numUnpopulated = sd.getNumUnpopulated();
		populationRatio = new BigDecimal(String.format(Constants.BIG_DECIMAL_FORMAT_STRING, sd.getPopUnpopRatio()));
		meanShape = new BigDecimal(String.format(Constants.BIG_DECIMAL_FORMAT_STRING, sd.getMeanShape()));
		meanFreq = new BigDecimal(String.format(Constants.BIG_DECIMAL_FORMAT_STRING, sd.getMeanFreq()));
		shapeStdDeviation = new BigDecimal(String.format(Constants.BIG_DECIMAL_FORMAT_STRING, sd.getSdShape()));
		frequencyStdDeviation = new BigDecimal(String.format(Constants.BIG_DECIMAL_FORMAT_STRING, sd.getSdFreq()));
		this.modelDto = modelDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumShapes() {
		return numShapes;
	}

	public void setNumShapes(Integer numShapes) {
		this.numShapes = numShapes;
	}

	public Integer getMostCommonShape() {
		return mostCommonShape;
	}

	public void setMostCommonShape(Integer mostCommonShape) {
		this.mostCommonShape = mostCommonShape;
	}

	public Integer getLeastCommonShape() {
		return LeastCommonShape;
	}

	public void setLeastCommonShape(Integer leastCommonShape) {
		LeastCommonShape = leastCommonShape;
	}

	public Integer getHighestFreq() {
		return highestFreq;
	}

	public void setHighestFreq(Integer highestFreq) {
		this.highestFreq = highestFreq;
	}

	public Integer getLowestFreq() {
		return lowestFreq;
	}

	public void setLowestFreq(Integer lowestFreq) {
		this.lowestFreq = lowestFreq;
	}

	public Integer getNumPopulated() {
		return numPopulated;
	}

	public void setNumPopulated(Integer numPopulated) {
		this.numPopulated = numPopulated;
	}

	public Integer getNumUnpopulated() {
		return numUnpopulated;
	}

	public void setNumUnpopulated(Integer numUnpopulated) {
		this.numUnpopulated = numUnpopulated;
	}

	public BigDecimal getPopulationRatio() {
		return populationRatio;
	}

	public void setPopulationRatio(BigDecimal populationRatio) {
		this.populationRatio = populationRatio;
	}

	public BigDecimal getMeanShape() {
		return meanShape;
	}

	public void setMeanShape(BigDecimal meanShape) {
		this.meanShape = meanShape;
	}

	public BigDecimal getMeanFreq() {
		return meanFreq;
	}

	public void setMeanFreq(BigDecimal meanFreq) {
		this.meanFreq = meanFreq;
	}

	public BigDecimal getShapeStdDeviation() {
		return shapeStdDeviation;
	}

	public void setShapeStdDeviation(BigDecimal shapeStdDeviation) {
		this.shapeStdDeviation = shapeStdDeviation;
	}

	public BigDecimal getFrequencyStdDeviation() {
		return frequencyStdDeviation;
	}

	public void setFrequencyStdDeviation(BigDecimal frequencyStdDeviation) {
		this.frequencyStdDeviation = frequencyStdDeviation;
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
