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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shape_distro")
public class ShapeDistributionEntity {

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
	private ModelEntity modelDto;

	public ShapeDistributionEntity(ShapeDistribution sd, ModelEntity modelDto) {
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

}
