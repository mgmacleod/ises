package ises.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

	////////////////////////// Model parameters \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// shape parameters
	@Column(name = "shape_max")
	private int shapeMax = 128; // the size of the shape space

	@Column(name = "distance_max")
	private int distanceMax = 3; // the maximum 'distance' in shapes for binding

	// translation costs
	@Column(name = "cost_rna")
	private int costRNA = 3; // per translation event

	@Column(name = "cost_protein")
	private int costProtein = 2; // per translated protein

	// basal translation rate
	@Column(name = "basal_translation_rate")
	private double basalTranslationRate = 0.01;

	// max protein production and degradation rates added.
	@Column(name = "max_degradation_rate")
	private int maxDegradationRate = 10; // max timesteps a protein can survive

	@Column(name = "max_production_rate")
	private int maxProductionRate = 10; // max number of proteins a gene can produce

	// energy signalling thresholds
	@Column(name = "energy_1_threshold")
	private int energy1Threshold = 500;

	@Column(name = "energy_2_threshold")
	private int energy2Threshold = 333;

	// energy yields from food (low, med, high)
	// the amount of energy produced when each type of food is available
	@Column(name = "energy_1_production")
	private int energy1Production = 5;

	@Column(name = "energy_2_production")
	private int energy2Production = 5;

	@Column(name = "energy_3_production")
	private int energy3Production = 10;

	@Column(name = "energy_4_production")
	private int energy4Production = 10;

	@Column(name = "energy_5_production")
	private int energy5Production = 15;

	@Column(name = "energy_6_production")
	private int energy6Production = 15;

	@Column(name = "energy_7_production")
	private int energy7Production = 20;

	@Column(name = "energy_8_production")
	private int energy8Production = 20;

	@Column(name = "energy_9_production")
	private int energy9Production = 25;

	// biomass yields from biosynthesis pathways (SAO)
	@Column(name = "biomass_1_production")
	private int biomass1Production = 50;

	@Column(name = "biomass_2_production")
	private int biomass2Production = 10;

	@Column(name = "biomass_3_production")
	private int biomass3Production = 50;

	@Column(name = "biomass_4_production")
	private int biomass4Production = 10;

	// costs for biosynthesis pathways (SAO)
	@Column(name = "biomass_1_cost")
	private int biomass1Cost = 75;

	@Column(name = "biomass_2_cost")
	private int biomass2Cost = 75;

	@Column(name = "biomass_3_cost")
	private int biomass3Cost = 5;

	@Column(name = "biomass_4_cost")
	private int biomass4Cost = 5;

	// stress parameters
	@Column(name = "stress_1_production")
	private int stress1Production = 25; // number of stress molecules removed per rsp activation

	@Column(name = "stress_2_production")
	private int stress2Production = 25;

	@Column(name = "stress_1_cost")
	private int stress1Cost = 100; // cost per activation

	@Column(name = "stress_2_cost")
	private int stress2Cost = 100;

	@Column(name = "stress_1_threshold")
	private int stress1Threshold = 100; // critical threshold; model dies if reached or exceeded

	@Column(name = "stress_2_threshold")
	private int stress2Threshold = 100;

	/////////////////////////// simulation parameters
	/////////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// max time and starting energy (as in original)
	@Column(name = "start_energy")
	private int startEnergy = 1000; // the amount of energy with which each model starts

	@Column(name = "max_time")
	private int maxTime = 2000; // number of timesteps in the simulation

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
	@Column(name = "food_1_rate")
	private double food1Rate = 0.0;

	@JsonIgnore
	@Column(name = "food_2_rate")
	private double food2Rate = 0.0;

	@JsonIgnore
	@Column(name = "food_3_rate")
	private double food3Rate = 0.0;

	@JsonIgnore
	@Column(name = "food_4_rate")
	private double food4Rate = 0.0;

	@JsonIgnore
	@Column(name = "food_5_rate")
	private double food5Rate = 0.0;

	@JsonIgnore
	@Column(name = "food_6_rate")
	private double food6Rate = 0.0;

	@JsonIgnore
	@Column(name = "food_7_rate")
	private double food7Rate = 0.0;

	@JsonIgnore
	@Column(name = "food_8_rate")
	private double food8Rate = 0.0;

	@JsonIgnore
	@Column(name = "food_9_rate")
	private double food9Rate = 0.0;

	@Column(name = "food_rate_base")
	private double foodRateBase = 0.08;

	@Column(name = "food_rate_factor")
	private double foodRateFactor = 2.0;

	@Column(name = "foog_flip_interval")
	private int foodFlipInterval = 100;

	// simulation-side stress parameters (as in original)
	@Column(name = "stress_in_production")
	private int stressInProduction = 25; // the stress increment; number of molecules added

	@Column(name = "stress_in_interval")
	private int stressInInterval = 25; // number of timesteps before stress enters

	////////////////////////////////// GA parameters \\\\\\\\\\\\\\\\\\\\\\\\\\\\

	@Column(name = "population_size")
	private int populationSize = 100;

	@Column(name = "max_generation")
	private int maxGeneration = 10;

	@Column(name = "neutral_generations")
	private int neutralGenerations = 100; // number of generations of neutral evolution

	@Column(name = "init_genome_size")
	private int initGenomeSize = 32;

	// mutation probabilities
	@Column(name = "gene_dup_probability")
	private double geneDupProbability = 0.001; // gene duplication

	@Column(name = "gene_del_probability")
	private double geneDelProbability = 0.001; // gene deletion

	@Column(name = "prot_shape_mut_probability")
	private double protShapeMutProbability = 0.005; // protein shape mutation

	@Column(name = "prot_prod_mut_probability")
	private double protProdMutProbability = 0.005; // protein production rate mutation

	@Column(name = "prot_deg_mut_probability")
	private double protDegMutProbability = 0.005; // protein degradation rate mutation

	@Column(name = "bind_site_dup_probability")
	private double bindSiteDupProbability = 0.008; // binding site duplication

	@Column(name = "bind_site_del_probability")
	private double bindSiteDelProbability = 0.008; // binding site deletion

	@Column(name = "bind_site_shape_mut_probability")
	private double bindSiteShapeMutProbability = 0.0008; // binding site shape mutation

	@Column(name = "bind_site_flip_probability")
	private double bindSiteFlipProbability = 0.0008; // binding site regulatory flip

	// data collection intervals
	// in use when 'collect data?' is selected in the ui

	// the gene regulatory network of the best model will be saved every x
	// generations
	@Column(name = "sample_grn_interval")
	private int sampleGrnInterval = 100;

	// the pertinent data from the best model will be saved every x generations
	@Column(name = "sample_model_interval")
	private int sampleModelInterval = 50;

	public SimulationConfiguration() {
	}

	public int getShapeMax() {
		return shapeMax;
	}

	public void setShapeMax(int sMax) {
		shapeMax = sMax;
	}

	public int getDistanceMax() {
		return distanceMax;
	}

	public void setDistanceMax(int dMax) {
		distanceMax = dMax;
	}

	public int getCostRNA() {
		return costRNA;
	}

	public void setCostRNA(int cRNA) {
		costRNA = cRNA;
	}

	public int getCostProtein() {
		return costProtein;
	}

	public void setCostProtein(int cProtein) {
		costProtein = cProtein;
	}

	public double getBasalTranslationRate() {
		return basalTranslationRate;
	}

	public void setBasalTranslationRate(double kBasal) {
		basalTranslationRate = kBasal;
	}

	public int getMaxDegradationRate() {
		return maxDegradationRate;
	}

	public void setMaxDegradationRate(int maxDegRate) {
		maxDegradationRate = maxDegRate;
	}

	public int getMaxProductionRate() {
		return maxProductionRate;
	}

	public void setMaxProductionRate(int maxProdRate) {
		maxProductionRate = maxProdRate;
	}

	public int getEnergy1Threshold() {
		return energy1Threshold;
	}

	public void setEnergy1Threshold(int tEnergy1) {
		energy1Threshold = tEnergy1;
	}

	public int getEnergy2Threshold() {
		return energy2Threshold;
	}

	public void setEnergy2Threshold(int tEnergy2) {
		energy2Threshold = tEnergy2;
	}

	public int getEnergy1Production() {
		return energy1Production;
	}

	public void setEnergy1Production(int pEnergy1) {
		energy1Production = pEnergy1;
	}

	public int getEnergy2Production() {
		return energy2Production;
	}

	public void setEnergy2Production(int pEnergy2) {
		energy2Production = pEnergy2;
	}

	public int getEnergy3Production() {
		return energy3Production;
	}

	public void setEnergy3Production(int pEnergy3) {
		energy3Production = pEnergy3;
	}

	public int getEnergy4Production() {
		return energy4Production;
	}

	public void setEnergy4Production(int pEnergy4) {
		energy4Production = pEnergy4;
	}

	public int getEnergy5Production() {
		return energy5Production;
	}

	public void setEnergy5Production(int pEnergy5) {
		energy5Production = pEnergy5;
	}

	public int getEnergy6Production() {
		return energy6Production;
	}

	public void setEnergy6Production(int pEnergy6) {
		energy6Production = pEnergy6;
	}

	public int getEnergy7Production() {
		return energy7Production;
	}

	public void setEnergy7Production(int pEnergy7) {
		energy7Production = pEnergy7;
	}

	public int getEnergy8Production() {
		return energy8Production;
	}

	public void setEnergy8Production(int pEnergy8) {
		energy8Production = pEnergy8;
	}

	public int getEnergy9Production() {
		return energy9Production;
	}

	public void setEnergy9Production(int pEnergy9) {
		energy9Production = pEnergy9;
	}

	public int getBiomass1Production() {
		return biomass1Production;
	}

	public void setBiomass1Production(int pBio1) {
		biomass1Production = pBio1;
	}

	public int getBiomass2Production() {
		return biomass2Production;
	}

	public void setBiomass2Production(int pBio2) {
		biomass2Production = pBio2;
	}

	public int getBiomass3Production() {
		return biomass3Production;
	}

	public void setBiomass3Production(int pBio3) {
		biomass3Production = pBio3;
	}

	public int getBiomass4Production() {
		return biomass4Production;
	}

	public void setBiomass4Production(int pBio4) {
		biomass4Production = pBio4;
	}

	public int getBiomass1Cost() {
		return biomass1Cost;
	}

	public void setBiomass1Cost(int cBio1) {
		biomass1Cost = cBio1;
	}

	public int getBiomass2Cost() {
		return biomass2Cost;
	}

	public void setBiomass2Cost(int cBio2) {
		biomass2Cost = cBio2;
	}

	public int getBiomass3Cost() {
		return biomass3Cost;
	}

	public void setBiomass3Cost(int cBio3) {
		biomass3Cost = cBio3;
	}

	public int getBiomass4Cost() {
		return biomass4Cost;
	}

	public void setBiomass4Cost(int cBio4) {
		biomass4Cost = cBio4;
	}

	public int getStress1Production() {
		return stress1Production;
	}

	public void setStress1Production(int pStress1) {
		stress1Production = pStress1;
	}

	public int getStress2Production() {
		return stress2Production;
	}

	public void setStress2Production(int pStress2) {
		stress2Production = pStress2;
	}

	public int getStress1Cost() {
		return stress1Cost;
	}

	public void setStress1Cost(int cStress1) {
		stress1Cost = cStress1;
	}

	public int getStress2Cost() {
		return stress2Cost;
	}

	public void setStress2Cost(int cStress2) {
		stress2Cost = cStress2;
	}

	public int getStress1Threshold() {
		return stress1Threshold;
	}

	public void setStress1Threshold(int tStress1) {
		stress1Threshold = tStress1;
	}

	public int getStress2Threshold() {
		return stress2Threshold;
	}

	public void setStress2Threshold(int tStress2) {
		stress2Threshold = tStress2;
	}

	public int getStartEnergy() {
		return startEnergy;
	}

	public void setStartEnergy(int startEnergy) {
		this.startEnergy = startEnergy;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public double getFood1Rate() {
		return food1Rate;
	}

	public void setFood1Rate(double kFood1) {
		food1Rate = kFood1;
	}

	public double getFood2Rate() {
		return food2Rate;
	}

	public void setFood2Rate(double kFood2) {
		food2Rate = kFood2;
	}

	public double getFood3Rate() {
		return food3Rate;
	}

	public void setFood3Rate(double kFood3) {
		food3Rate = kFood3;
	}

	public double getFood4Rate() {
		return food4Rate;
	}

	public void setFood4Rate(double kFood4) {
		food4Rate = kFood4;
	}

	public double getFood5Rate() {
		return food5Rate;
	}

	public void setFood5Rate(double kFood5) {
		food5Rate = kFood5;
	}

	public double getFood6Rate() {
		return food6Rate;
	}

	public void setFood6Rate(double kFood6) {
		food6Rate = kFood6;
	}

	public double getFood7Rate() {
		return food7Rate;
	}

	public void setFood7Rate(double kFood7) {
		food7Rate = kFood7;
	}

	public double getFood8Rate() {
		return food8Rate;
	}

	public void setFood8Rate(double kFood8) {
		food8Rate = kFood8;
	}

	public double getFood9Rate() {
		return food9Rate;
	}

	public void setFood9Rate(double kFood9) {
		food9Rate = kFood9;
	}

	public double getFoodRateBase() {
		return foodRateBase;
	}

	public void setFoodRateBase(double kFoodBase) {
		foodRateBase = kFoodBase;
	}

	public double getFoodRateFactor() {
		return foodRateFactor;
	}

	public void setFoodRateFactor(double kFoodFactor) {
		foodRateFactor = kFoodFactor;
	}

	public int getFoodFlipInterval() {
		return foodFlipInterval;
	}

	public void setFoodFlipInterval(int iFoodFlip) {
		foodFlipInterval = iFoodFlip;
	}

	public int getStressInProduction() {
		return stressInProduction;
	}

	public void setStressInProduction(int pStressIn) {
		stressInProduction = pStressIn;
	}

	public int getStressInInterval() {
		return stressInInterval;
	}

	public void setStressInInterval(int iStressIn) {
		stressInInterval = iStressIn;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int popSize) {
		populationSize = popSize;
	}

	public int getMaxGeneration() {
		return maxGeneration;
	}

	public void setMaxGeneration(int maxGen) {
		maxGeneration = maxGen;
	}

	public int getNeutralGenerations() {
		return neutralGenerations;
	}

	public void setNeutralGenerations(int neutralGen) {
		neutralGenerations = neutralGen;
	}

	public int getInitGenomeSize() {
		return initGenomeSize;
	}

	public void setInitGenomeSize(int initGenomeSize) {
		this.initGenomeSize = initGenomeSize;
	}

	public double getGeneDupProbability() {
		return geneDupProbability;
	}

	public void setGeneDupProbability(double mDupGene) {
		geneDupProbability = mDupGene;
	}

	public double getGeneDelProbability() {
		return geneDelProbability;
	}

	public void setGeneDelProbability(double mDelGene) {
		geneDelProbability = mDelGene;
	}

	public double getProtShapeMutProbability() {
		return protShapeMutProbability;
	}

	public void setProtShapeMutProbability(double mShapeP) {
		protShapeMutProbability = mShapeP;
	}

	public double getProtProdMutProbability() {
		return protProdMutProbability;
	}

	public void setProtProdMutProbability(double mProdP) {
		protProdMutProbability = mProdP;
	}

	public double getProtDegMutProbability() {
		return protDegMutProbability;
	}

	public void setProtDegMutProbability(double mStabP) {
		protDegMutProbability = mStabP;
	}

	public double getBindSiteDupProbability() {
		return bindSiteDupProbability;
	}

	public void setBindSiteDupProbability(double mDupBS) {
		bindSiteDupProbability = mDupBS;
	}

	public double getBindSiteDelProbability() {
		return bindSiteDelProbability;
	}

	public void setBindSiteDelProbability(double mDelBS) {
		bindSiteDelProbability = mDelBS;
	}

	public double getBindSiteShapeMutProbability() {
		return bindSiteShapeMutProbability;
	}

	public void setBindSiteShapeMutProbability(double mShapeBS) {
		bindSiteShapeMutProbability = mShapeBS;
	}

	public double getBindSiteFlipProbability() {
		return bindSiteFlipProbability;
	}

	public void setBindSiteFlipProbability(double mFlipBS) {
		bindSiteFlipProbability = mFlipBS;
	}

	public int getSampleGrnInterval() {
		return sampleGrnInterval;
	}

	public void setSampleGrnInterval(int iSampleGRN) {
		sampleGrnInterval = iSampleGRN;
	}

	public int getSampleModelInterval() {
		return sampleModelInterval;
	}

	public void setSampleModelInterval(int iSampleModel) {
		sampleModelInterval = iSampleModel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
