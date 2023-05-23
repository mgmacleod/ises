package ises.rest.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Represents all of the configuration values needed to control a simulation run.
 */
@Entity
@Table(name = "sim_config")
public class SimulationConfiguration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sim_id")
	private Long id;

	@Column(name = "created_on", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime createdOn = LocalDateTime.now();

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
	@Column(name = "stress_1_production", nullable = false)
	private Integer stress1Production = 25; // number of stress molecules removed per rsp activation

	@NotNull
	@Positive
	@Column(name = "stress_2_production", nullable = false)
	private Integer stress2Production = 25;

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
	 * this implementation assigns a probability to each food and makes it available to the model at each timestep with this
	 * probability
	 * 
	 * the kFood1-9 parameters don't use the values set here; rather their values are set dynamically based on the base
	 * probability (kFoodBase) and the factor (kFoodFactor) by which kFoodBase is divided or multiplied randomly with
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
	@Column(name = "stress_in_production", nullable = false)
	private Integer stressInProduction = 25; // the stress increment; number of molecules added

	@NotNull
	@Positive
	@Column(name = "stress_in_interval", nullable = false)
	private Integer stressInInterval = 25; // number of timesteps before stress enters

	////////////////////////////////// GA parameters \\\\\\\\\\\\\\\\\\\\\\\\\\\\

	@NotNull
	@Positive
	@Column(name = "population_size", nullable = false)
	private Integer populationSize = 100;

	@NotNull
	@Positive
	@Column(name = "max_generation", nullable = false)
	private Integer maxGeneration = 10;

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

	public SimulationConfiguration() {
	}

	public Integer getShapeMax() {
		return shapeMax;
	}

	public void setShapeMax(Integer sMax) {
		shapeMax = sMax;
	}

	public Integer getDistanceMax() {
		return distanceMax;
	}

	public void setDistanceMax(Integer dMax) {
		distanceMax = dMax;
	}

	public Integer getCostRNA() {
		return costRNA;
	}

	public void setCostRNA(Integer cRNA) {
		costRNA = cRNA;
	}

	public Integer getCostProtein() {
		return costProtein;
	}

	public void setCostProtein(Integer cProtein) {
		costProtein = cProtein;
	}

	public Double getBasalTranslationRate() {
		return basalTranslationRate;
	}

	public void setBasalTranslationRate(Double kBasal) {
		basalTranslationRate = kBasal;
	}

	public Integer getMaxDegradationRate() {
		return maxDegradationRate;
	}

	public void setMaxDegradationRate(Integer maxDegRate) {
		maxDegradationRate = maxDegRate;
	}

	public Integer getMaxProductionRate() {
		return maxProductionRate;
	}

	public void setMaxProductionRate(Integer maxProdRate) {
		maxProductionRate = maxProdRate;
	}

	public Integer getEnergy1Threshold() {
		return energy1Threshold;
	}

	public void setEnergy1Threshold(Integer tEnergy1) {
		energy1Threshold = tEnergy1;
	}

	public Integer getEnergy2Threshold() {
		return energy2Threshold;
	}

	public void setEnergy2Threshold(Integer tEnergy2) {
		energy2Threshold = tEnergy2;
	}

	public Integer getEnergy1Production() {
		return energy1Production;
	}

	public void setEnergy1Production(Integer pEnergy1) {
		energy1Production = pEnergy1;
	}

	public Integer getEnergy2Production() {
		return energy2Production;
	}

	public void setEnergy2Production(Integer pEnergy2) {
		energy2Production = pEnergy2;
	}

	public Integer getEnergy3Production() {
		return energy3Production;
	}

	public void setEnergy3Production(Integer pEnergy3) {
		energy3Production = pEnergy3;
	}

	public Integer getEnergy4Production() {
		return energy4Production;
	}

	public void setEnergy4Production(Integer pEnergy4) {
		energy4Production = pEnergy4;
	}

	public Integer getEnergy5Production() {
		return energy5Production;
	}

	public void setEnergy5Production(Integer pEnergy5) {
		energy5Production = pEnergy5;
	}

	public Integer getEnergy6Production() {
		return energy6Production;
	}

	public void setEnergy6Production(Integer pEnergy6) {
		energy6Production = pEnergy6;
	}

	public Integer getEnergy7Production() {
		return energy7Production;
	}

	public void setEnergy7Production(Integer pEnergy7) {
		energy7Production = pEnergy7;
	}

	public Integer getEnergy8Production() {
		return energy8Production;
	}

	public void setEnergy8Production(Integer pEnergy8) {
		energy8Production = pEnergy8;
	}

	public Integer getEnergy9Production() {
		return energy9Production;
	}

	public void setEnergy9Production(Integer pEnergy9) {
		energy9Production = pEnergy9;
	}

	public Integer getBiomass1Production() {
		return biomass1Production;
	}

	public void setBiomass1Production(Integer pBio1) {
		biomass1Production = pBio1;
	}

	public Integer getBiomass2Production() {
		return biomass2Production;
	}

	public void setBiomass2Production(Integer pBio2) {
		biomass2Production = pBio2;
	}

	public Integer getBiomass3Production() {
		return biomass3Production;
	}

	public void setBiomass3Production(Integer pBio3) {
		biomass3Production = pBio3;
	}

	public Integer getBiomass4Production() {
		return biomass4Production;
	}

	public void setBiomass4Production(Integer pBio4) {
		biomass4Production = pBio4;
	}

	public Integer getBiomass1Cost() {
		return biomass1Cost;
	}

	public void setBiomass1Cost(Integer cBio1) {
		biomass1Cost = cBio1;
	}

	public Integer getBiomass2Cost() {
		return biomass2Cost;
	}

	public void setBiomass2Cost(Integer cBio2) {
		biomass2Cost = cBio2;
	}

	public Integer getBiomass3Cost() {
		return biomass3Cost;
	}

	public void setBiomass3Cost(Integer cBio3) {
		biomass3Cost = cBio3;
	}

	public Integer getBiomass4Cost() {
		return biomass4Cost;
	}

	public void setBiomass4Cost(Integer cBio4) {
		biomass4Cost = cBio4;
	}

	public Integer getStress1Production() {
		return stress1Production;
	}

	public void setStress1Production(Integer pStress1) {
		stress1Production = pStress1;
	}

	public Integer getStress2Production() {
		return stress2Production;
	}

	public void setStress2Production(Integer pStress2) {
		stress2Production = pStress2;
	}

	public Integer getStress1Cost() {
		return stress1Cost;
	}

	public void setStress1Cost(Integer cStress1) {
		stress1Cost = cStress1;
	}

	public Integer getStress2Cost() {
		return stress2Cost;
	}

	public void setStress2Cost(Integer cStress2) {
		stress2Cost = cStress2;
	}

	public Integer getStress1Threshold() {
		return stress1Threshold;
	}

	public void setStress1Threshold(Integer tStress1) {
		stress1Threshold = tStress1;
	}

	public Integer getStress2Threshold() {
		return stress2Threshold;
	}

	public void setStress2Threshold(Integer tStress2) {
		stress2Threshold = tStress2;
	}

	public Integer getStartEnergy() {
		return startEnergy;
	}

	public void setStartEnergy(Integer startEnergy) {
		this.startEnergy = startEnergy;
	}

	public Integer getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(Integer maxTime) {
		this.maxTime = maxTime;
	}

	public Double getFood1Rate() {
		return food1Rate;
	}

	public void setFood1Rate(Double kFood1) {
		food1Rate = kFood1;
	}

	public Double getFood2Rate() {
		return food2Rate;
	}

	public void setFood2Rate(Double kFood2) {
		food2Rate = kFood2;
	}

	public Double getFood3Rate() {
		return food3Rate;
	}

	public void setFood3Rate(Double kFood3) {
		food3Rate = kFood3;
	}

	public Double getFood4Rate() {
		return food4Rate;
	}

	public void setFood4Rate(Double kFood4) {
		food4Rate = kFood4;
	}

	public Double getFood5Rate() {
		return food5Rate;
	}

	public void setFood5Rate(Double kFood5) {
		food5Rate = kFood5;
	}

	public Double getFood6Rate() {
		return food6Rate;
	}

	public void setFood6Rate(Double kFood6) {
		food6Rate = kFood6;
	}

	public Double getFood7Rate() {
		return food7Rate;
	}

	public void setFood7Rate(Double kFood7) {
		food7Rate = kFood7;
	}

	public Double getFood8Rate() {
		return food8Rate;
	}

	public void setFood8Rate(Double kFood8) {
		food8Rate = kFood8;
	}

	public Double getFood9Rate() {
		return food9Rate;
	}

	public void setFood9Rate(Double kFood9) {
		food9Rate = kFood9;
	}

	public Double getFoodRateBase() {
		return foodRateBase;
	}

	public void setFoodRateBase(Double kFoodBase) {
		foodRateBase = kFoodBase;
	}

	public Double getFoodRateFactor() {
		return foodRateFactor;
	}

	public void setFoodRateFactor(Double kFoodFactor) {
		foodRateFactor = kFoodFactor;
	}

	public Integer getFoodFlipInterval() {
		return foodFlipInterval;
	}

	public void setFoodFlipInterval(Integer iFoodFlip) {
		foodFlipInterval = iFoodFlip;
	}

	public Integer getStressInProduction() {
		return stressInProduction;
	}

	public void setStressInProduction(Integer pStressIn) {
		stressInProduction = pStressIn;
	}

	public Integer getStressInInterval() {
		return stressInInterval;
	}

	public void setStressInInterval(Integer iStressIn) {
		stressInInterval = iStressIn;
	}

	public Integer getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(Integer popSize) {
		populationSize = popSize;
	}

	public Integer getMaxGeneration() {
		return maxGeneration;
	}

	public void setMaxGeneration(Integer maxGen) {
		maxGeneration = maxGen;
	}

	public Integer getNeutralGenerations() {
		return neutralGenerations;
	}

	public void setNeutralGenerations(Integer neutralGen) {
		neutralGenerations = neutralGen;
	}

	public Integer getInitGenomeSize() {
		return initGenomeSize;
	}

	public void setInitGenomeSize(Integer initGenomeSize) {
		this.initGenomeSize = initGenomeSize;
	}

	public Double getGeneDupProbability() {
		return geneDupProbability;
	}

	public void setGeneDupProbability(Double mDupGene) {
		geneDupProbability = mDupGene;
	}

	public Double getGeneDelProbability() {
		return geneDelProbability;
	}

	public void setGeneDelProbability(Double mDelGene) {
		geneDelProbability = mDelGene;
	}

	public Double getProtShapeMutProbability() {
		return protShapeMutProbability;
	}

	public void setProtShapeMutProbability(Double mShapeP) {
		protShapeMutProbability = mShapeP;
	}

	public Double getProtProdMutProbability() {
		return protProdMutProbability;
	}

	public void setProtProdMutProbability(Double mProdP) {
		protProdMutProbability = mProdP;
	}

	public Double getProtDegMutProbability() {
		return protDegMutProbability;
	}

	public void setProtDegMutProbability(Double mStabP) {
		protDegMutProbability = mStabP;
	}

	public Double getBindSiteDupProbability() {
		return bindSiteDupProbability;
	}

	public void setBindSiteDupProbability(Double mDupBS) {
		bindSiteDupProbability = mDupBS;
	}

	public Double getBindSiteDelProbability() {
		return bindSiteDelProbability;
	}

	public void setBindSiteDelProbability(Double mDelBS) {
		bindSiteDelProbability = mDelBS;
	}

	public Double getBindSiteShapeMutProbability() {
		return bindSiteShapeMutProbability;
	}

	public void setBindSiteShapeMutProbability(Double mShapeBS) {
		bindSiteShapeMutProbability = mShapeBS;
	}

	public Double getBindSiteFlipProbability() {
		return bindSiteFlipProbability;
	}

	public void setBindSiteFlipProbability(Double mFlipBS) {
		bindSiteFlipProbability = mFlipBS;
	}

	public Integer getSampleGrnInterval() {
		return sampleGrnInterval;
	}

	public void setSampleGrnInterval(Integer iSampleGRN) {
		sampleGrnInterval = iSampleGRN;
	}

	public Integer getSampleModelInterval() {
		return sampleModelInterval;
	}

	public void setSampleModelInterval(Integer iSampleModel) {
		sampleModelInterval = iSampleModel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

}
