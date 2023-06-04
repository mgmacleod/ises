package ises.rest.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents all of the configuration values needed to control a simulation
 * run.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "sim_config")
public class SimulationConfiguration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sim_id")
	private Long id;

	@Column(name = "created_on", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime createdOn = LocalDateTime.now();

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private SimulationStatus status = SimulationStatus.NEW;

	////////////////////////// Model parameters \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// shape parameters
	@NotNull
	@Positive
	@Column(name = "shape_max", nullable = false)
	private Integer shapeMax = 128; // the size of the shape space

	@NotNull
	@Positive
	@Column(name = "distance_max", nullable = false)
	private Integer distanceMax = 3; // the maximum 'distance' in shapes for binding

	// translation costs
	@NotNull
	@Positive
	@Column(name = "cost_rna", nullable = false)
	private Integer costRNA = 3; // per translation event

	@NotNull
	@Positive
	@Column(name = "cost_protein", nullable = false)
	private Integer costProtein = 2; // per translated protein

	// basal translation rate
	@NotNull
	@Positive
	@Column(name = "basal_translation_rate", nullable = false)
	private Double basalTranslationRate = 0.01;

	// max protein production and degradation rates added.
	@NotNull
	@Positive
	@Column(name = "max_degradation_rate", nullable = false)
	private Integer maxDegradationRate = 10; // max timesteps a protein can survive

	@NotNull
	@Positive
	@Column(name = "max_production_rate", nullable = false)
	private Integer maxProductionRate = 10; // max number of proteins a gene can produce

	// energy signalling thresholds
	@NotNull
	@Positive
	@Column(name = "energy_1_threshold", nullable = false)
	private Integer energy1Threshold = 500;

	@NotNull
	@Positive
	@Column(name = "energy_2_threshold", nullable = false)
	private Integer energy2Threshold = 333;

	// energy yields from food (low, med, high)
	// the amount of energy produced when each type of food is available
	@NotNull
	@Positive
	@Column(name = "energy_1_production", nullable = false)
	private Integer energy1Production = 5;

	@NotNull
	@Positive
	@Column(name = "energy_2_production", nullable = false)
	private Integer energy2Production = 5;

	@NotNull
	@Positive
	@Column(name = "energy_3_production", nullable = false)
	private Integer energy3Production = 10;

	@NotNull
	@Positive
	@Column(name = "energy_4_production", nullable = false)
	private Integer energy4Production = 10;

	@NotNull
	@Positive
	@Column(name = "energy_5_production", nullable = false)
	private Integer energy5Production = 15;

	@NotNull
	@Positive
	@Column(name = "energy_6_production", nullable = false)
	private Integer energy6Production = 15;

	@NotNull
	@Positive
	@Column(name = "energy_7_production", nullable = false)
	private Integer energy7Production = 20;

	@NotNull
	@Positive
	@Column(name = "energy_8_production", nullable = false)
	private Integer energy8Production = 20;

	@NotNull
	@Positive
	@Column(name = "energy_9_production", nullable = false)
	private Integer energy9Production = 25;

	// biomass yields from biosynthesis pathways (SAO)
	@NotNull
	@Positive
	@Column(name = "biomass_1_production", nullable = false)
	private Integer biomass1Production = 50;

	@NotNull
	@Positive
	@Column(name = "biomass_2_production", nullable = false)
	private Integer biomass2Production = 10;

	@NotNull
	@Positive
	@Column(name = "biomass_3_production", nullable = false)
	private Integer biomass3Production = 50;

	@NotNull
	@Positive
	@Column(name = "biomass_4_production", nullable = false)
	private Integer biomass4Production = 10;

	// costs for biosynthesis pathways (SAO)
	@NotNull
	@Positive
	@Column(name = "biomass_1_cost", nullable = false)
	private Integer biomass1Cost = 75;

	@NotNull
	@Positive
	@Column(name = "biomass_2_cost", nullable = false)
	private Integer biomass2Cost = 75;

	@NotNull
	@Positive
	@Column(name = "biomass_3_cost", nullable = false)
	private Integer biomass3Cost = 5;

	@NotNull
	@Positive
	@Column(name = "biomass_4_cost", nullable = false)
	private Integer biomass4Cost = 5;

	// stress parameters
	@NotNull
	@Positive
	@Column(name = "stress_1_out_production", nullable = false)
	private Integer stress1OutProduction = 25; // number of stress molecules removed per rsp activation

	@NotNull
	@Positive
	@Column(name = "stress_2_out_production", nullable = false)
	private Integer stress2OutProduction = 25;

	@NotNull
	@Positive
	@Column(name = "stress_1_cost", nullable = false)
	private Integer stress1Cost = 100; // cost per activation

	@NotNull
	@Positive
	@Column(name = "stress_2_cost", nullable = false)
	private Integer stress2Cost = 100;

	@NotNull
	@Positive
	@Column(name = "stress_1_threshold", nullable = false)
	private Integer stress1Threshold = 100; // critical threshold; model dies if reached or exceeded

	@NotNull
	@Positive
	@Column(name = "stress_2_threshold", nullable = false)
	private Integer stress2Threshold = 100;

	/////////////////////////// simulation parameters
	/////////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// max time and starting energy (as in original)
	@NotNull
	@Positive
	@Column(name = "start_energy", nullable = false)
	private Integer startEnergy = 1000; // the amount of energy with which each model starts

	@NotNull
	@Positive
	@Column(name = "max_time", nullable = false)
	private Integer maxTime = 2000; // number of timesteps in the simulation

	// food availability rates
	/**
	 * this implementation assigns a probability to each food and makes it available
	 * to the model at each timestep with this
	 * probability
	 * 
	 * the kFood1-9 parameters don't use the values set here; rather their values
	 * are set dynamically based on the base
	 * probability (kFoodBase) and the factor (kFoodFactor) by which kFoodBase is
	 * divided or multiplied randomly with
	 * frequency iFoodFlip
	 * 
	 */
	@JsonIgnore
	@Transient
	private Double food1Rate = 0.0;

	@JsonIgnore
	@Transient
	private Double food2Rate = 0.0;

	@JsonIgnore
	@Transient
	private Double food3Rate = 0.0;

	@JsonIgnore
	@Transient
	private Double food4Rate = 0.0;

	@JsonIgnore
	@Transient
	private Double food5Rate = 0.0;

	@JsonIgnore
	@Transient
	private Double food6Rate = 0.0;

	@JsonIgnore
	@Transient
	private Double food7Rate = 0.0;

	@JsonIgnore
	@Transient
	private Double food8Rate = 0.0;

	@JsonIgnore
	@Transient
	private Double food9Rate = 0.0;

	@NotNull
	@Positive
	@Column(name = "food_rate_base", nullable = false)
	private Double foodRateBase = 0.08;

	@NotNull
	@Positive
	@Column(name = "food_rate_factor", nullable = false)
	private Double foodRateFactor = 2.0;

	@NotNull
	@Positive
	@Column(name = "foog_flip_interval", nullable = false)
	private Integer foodFlipInterval = 100;

	// simulation-side stress parameters
	@NotNull
	@Positive
	@Column(name = "stress_1_in_production", nullable = false)
	private Integer stress1InProduction = 10; // the stress 1 increment; number of molecules added

	@NotNull
	@Positive
	@Column(name = "stress_1_in_interval", nullable = false)
	private Integer stress1InInterval = 100; // number of timesteps before stress 2 enters

	@NotNull
	@Positive
	@Column(name = "stress_2_in_production", nullable = false)
	private Integer stress2InProduction = 10; // the stress 1 increment; number of molecules added

	@NotNull
	@Positive
	@Column(name = "stress_2_in_interval", nullable = false)
	private Integer stress2InInterval = 100; // number of timesteps before stress 1 enters

	////////////////////////////////// GA parameters \\\\\\\\\\\\\\\\\\\\\\\\\\\\

	@NotNull
	@Positive
	@Column(name = "population_size", nullable = false)
	private Integer populationSize = 100;

	@NotNull
	@Positive
	@Column(name = "max_generation", nullable = false)
	private Integer maxGeneration = 1000;

	@NotNull
	@Positive
	@Column(name = "neutral_generations", nullable = false)
	private Integer neutralGenerations = 100; // number of generations of neutral evolution

	@NotNull
	@Positive
	@Column(name = "init_genome_size", nullable = false)
	private Integer initGenomeSize = 32;

	// mutation probabilities
	@NotNull
	@Positive
	@Column(name = "gene_dup_probability", nullable = false)
	private Double geneDupProbability = 0.001; // gene duplication

	@NotNull
	@Positive
	@Column(name = "gene_del_probability", nullable = false)
	private Double geneDelProbability = 0.001; // gene deletion

	@NotNull
	@Positive
	@Column(name = "prot_shape_mut_probability", nullable = false)
	private Double protShapeMutProbability = 0.005; // protein shape mutation

	@NotNull
	@Positive
	@Column(name = "prot_prod_mut_probability", nullable = false)
	private Double protProdMutProbability = 0.005; // protein production rate mutation

	@NotNull
	@Positive
	@Column(name = "prot_deg_mut_probability", nullable = false)
	private Double protDegMutProbability = 0.005; // protein degradation rate mutation

	@NotNull
	@Positive
	@Column(name = "bind_site_dup_probability", nullable = false)
	private Double bindSiteDupProbability = 0.008; // binding site duplication

	@NotNull
	@Positive
	@Column(name = "bind_site_del_probability", nullable = false)
	private Double bindSiteDelProbability = 0.008; // binding site deletion

	@NotNull
	@Positive
	@Column(name = "bind_site_shape_mut_probability", nullable = false)
	private Double bindSiteShapeMutProbability = 0.0008; // binding site shape mutation

	@NotNull
	@Positive
	@Column(name = "bind_site_flip_probability", nullable = false)
	private Double bindSiteFlipProbability = 0.0008; // binding site regulatory flip

	// data collection intervals

	// the gene regulatory network of the best model will be saved every x
	// generations
	@NotNull
	@Positive
	@Column(name = "sample_grn_interval", nullable = false)
	private Integer sampleGrnInterval = 100;

	// the pertinent data from the best model will be saved every x generations
	@NotNull
	@Positive
	@Column(name = "sample_model_interval", nullable = false)
	private Integer sampleModelInterval = 50;

}
