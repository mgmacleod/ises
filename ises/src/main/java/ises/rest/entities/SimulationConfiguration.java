package ises.rest.entities;

/**
 * Represents all of the configuration values needed to control a simulation run.
 */
public class SimulationConfiguration {
	////////////////////////// Model parameters \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// shape parameters
	private int shapeMax = 128; // the size of the shape space
	private int distanceMax = 3; // the maximum 'distance' in shapes for binding

	// translation costs
	private int costRNA = 3; // per translation event
	private int costProtein = 2; // per translated protein

	// basal translation rate
	private double basalTranslationRate = 0.01;

	// max protein production and degradation rates added.
	// Not explicitly mentioned in the paper
	private int maxDegradationRate = 10; // max timesteps a protein can survive
	private int maxProductionRate = 10; // max number of proteins a gene can produce

	// energy signalling thresholds
	private int energy1Threshold = 500;
	private int energy2Threshold = 333;

	// energy yields from food (low, med, high)
	// the amount of energy produced when each type of food is available
	private int energy1Production = 5;
	private int energy2Production = 5;
	private int energy3Production = 10;
	private int energy4Production = 10;
	private int energy5Production = 15;
	private int energy6Production = 15;
	private int energy7Production = 20;
	private int energy8Production = 20;
	private int energy9Production = 25;

	// biomass yields from biosynthesis pathways (SAO)
	private int biomass1Production = 50;
	private int biomass2Production = 10;
	private int biomass3Production = 50;
	private int biomass4Production = 10;

	// costs for biosynthesis pathways (SAO)
	private int biomass1Cost = 75;
	private int biomass2Cost = 75;
	private int biomass3Cost = 5;
	private int biomass4Cost = 5;

	// stress parameters
	private int stress1Production = 25; // number of stress molecules removed per rsp activation
	private int stress2Production = 25;
	private int stress1Cost = 100; // cost per activation
	private int stress2Cost = 100;
	private int stress1Threshold = 100; // critical threshold; model dies if reached or exceeded
	private int stress2Threshold = 100;

	/////////////////////////// simulation parameters
	/////////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// max time and starting energy (as in original)
	private int startEnergy = 1000; // the amount of energy with which each model starts
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
	private double food1Rate = 0.0;
	private double food2Rate = 0.0;
	private double food3Rate = 0.0;
	private double food4Rate = 0.0;
	private double food5Rate = 0.0;
	private double food6Rate = 0.0;
	private double food7Rate = 0.0;
	private double food8Rate = 0.0;
	private double food9Rate = 0.0;

	private double foodRateBase = 0.08;
	private double foodRateFactor = 2.0;
	private int foodFlipInterval = 100;

	// simulation-side stress parameters (as in original)
	private int stressInProduction = 25; // the stress increment; number of molecules added
	private int stressInInterval = 25; // number of timesteps before stress enters

	////////////////////////////////// GA parameters \\\\\\\\\\\\\\\\\\\\\\\\\\\\
	// these are all as in original

	private int populationSize = 100;
	private int maxGeneration = 10;
	private int neutralGenerations = 100; // number of generations of neutral evolution
	private int initGenomeSize = 32;

	// mutation probabilities
	private double geneDupProbability = 0.001; // gene duplication
	private double geneDelProbability = 0.001; // gene deletion
	private double protShapeMutProbability = 0.005; // protein shape mutation
	private double protProdMutProbability = 0.005; // protein production rate mutation
	private double protDegMutProbability = 0.005; // protein degradation rate mutation
	private double bindSiteDupProbability = 0.008; // binding site duplication
	private double bindSiteDelProbability = 0.008; // binding site deletion
	private double bindSiteShapeMutProbability = 0.0008; // binding site shape mutation
	private double bindSiteFlipProbability = 0.0008; // binding site regulatory flip

	// data collection intervals
	// in use when 'collect data?' is selected in the ui

	// the gene regulatory network of the best model will be saved every x
	// generations
	private int sampleGrnInterval = 100;

	// the pertinent data from the best model will be saved every x generations
	private int sampleModelInterval = 50;

	public SimulationConfiguration() {
	}

	public int getShapeMax() {
		return shapeMax;
	}

	public void setShapeMax(int sMax) {
		this.shapeMax = sMax;
	}

	public int getDistanceMax() {
		return distanceMax;
	}

	public void setDistanceMax(int dMax) {
		this.distanceMax = dMax;
	}

	public int getCostRNA() {
		return costRNA;
	}

	public void setCostRNA(int cRNA) {
		this.costRNA = cRNA;
	}

	public int getCostProtein() {
		return costProtein;
	}

	public void setCostProtein(int cProtein) {
		this.costProtein = cProtein;
	}

	public double getBasalTranslationRate() {
		return basalTranslationRate;
	}

	public void setBasalTranslationRate(double kBasal) {
		this.basalTranslationRate = kBasal;
	}

	public int getMaxDegradationRate() {
		return maxDegradationRate;
	}

	public void setMaxDegradationRate(int maxDegRate) {
		this.maxDegradationRate = maxDegRate;
	}

	public int getMaxProductionRate() {
		return maxProductionRate;
	}

	public void setMaxProductionRate(int maxProdRate) {
		this.maxProductionRate = maxProdRate;
	}

	public int getEnergy1Threshold() {
		return energy1Threshold;
	}

	public void setEnergy1Threshold(int tEnergy1) {
		this.energy1Threshold = tEnergy1;
	}

	public int getEnergy2Threshold() {
		return energy2Threshold;
	}

	public void setEnergy2Threshold(int tEnergy2) {
		this.energy2Threshold = tEnergy2;
	}

	public int getEnergy1Production() {
		return energy1Production;
	}

	public void setEnergy1Production(int pEnergy1) {
		this.energy1Production = pEnergy1;
	}

	public int getEnergy2Production() {
		return energy2Production;
	}

	public void setEnergy2Production(int pEnergy2) {
		this.energy2Production = pEnergy2;
	}

	public int getEnergy3Production() {
		return energy3Production;
	}

	public void setEnergy3Production(int pEnergy3) {
		this.energy3Production = pEnergy3;
	}

	public int getEnergy4Production() {
		return energy4Production;
	}

	public void setEnergy4Production(int pEnergy4) {
		this.energy4Production = pEnergy4;
	}

	public int getEnergy5Production() {
		return energy5Production;
	}

	public void setEnergy5Production(int pEnergy5) {
		this.energy5Production = pEnergy5;
	}

	public int getEnergy6Production() {
		return energy6Production;
	}

	public void setEnergy6Production(int pEnergy6) {
		this.energy6Production = pEnergy6;
	}

	public int getEnergy7Production() {
		return energy7Production;
	}

	public void setEnergy7Production(int pEnergy7) {
		this.energy7Production = pEnergy7;
	}

	public int getEnergy8Production() {
		return energy8Production;
	}

	public void setEnergy8Production(int pEnergy8) {
		this.energy8Production = pEnergy8;
	}

	public int getEnergy9Production() {
		return energy9Production;
	}

	public void setEnergy9Production(int pEnergy9) {
		this.energy9Production = pEnergy9;
	}

	public int getBiomass1Production() {
		return biomass1Production;
	}

	public void setBiomass1Production(int pBio1) {
		this.biomass1Production = pBio1;
	}

	public int getBiomass2Production() {
		return biomass2Production;
	}

	public void setBiomass2Production(int pBio2) {
		this.biomass2Production = pBio2;
	}

	public int getBiomass3Production() {
		return biomass3Production;
	}

	public void setBiomass3Production(int pBio3) {
		this.biomass3Production = pBio3;
	}

	public int getBiomass4Production() {
		return biomass4Production;
	}

	public void setBiomass4Production(int pBio4) {
		this.biomass4Production = pBio4;
	}

	public int getBiomass1Cost() {
		return biomass1Cost;
	}

	public void setBiomass1Cost(int cBio1) {
		this.biomass1Cost = cBio1;
	}

	public int getBiomass2Cost() {
		return biomass2Cost;
	}

	public void setBiomass2Cost(int cBio2) {
		this.biomass2Cost = cBio2;
	}

	public int getBiomass3Cost() {
		return biomass3Cost;
	}

	public void setBiomass3Cost(int cBio3) {
		this.biomass3Cost = cBio3;
	}

	public int getBiomass4Cost() {
		return biomass4Cost;
	}

	public void setBiomass4Cost(int cBio4) {
		this.biomass4Cost = cBio4;
	}

	public int getStress1Production() {
		return stress1Production;
	}

	public void setStress1Production(int pStress1) {
		this.stress1Production = pStress1;
	}

	public int getStress2Production() {
		return stress2Production;
	}

	public void setStress2Production(int pStress2) {
		this.stress2Production = pStress2;
	}

	public int getStress1Cost() {
		return stress1Cost;
	}

	public void setStress1Cost(int cStress1) {
		this.stress1Cost = cStress1;
	}

	public int getStress2Cost() {
		return stress2Cost;
	}

	public void setStress2Cost(int cStress2) {
		this.stress2Cost = cStress2;
	}

	public int getStress1Threshold() {
		return stress1Threshold;
	}

	public void setStress1Threshold(int tStress1) {
		this.stress1Threshold = tStress1;
	}

	public int getStress2Threshold() {
		return stress2Threshold;
	}

	public void setStress2Threshold(int tStress2) {
		this.stress2Threshold = tStress2;
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
		this.food1Rate = kFood1;
	}

	public double getFood2Rate() {
		return food2Rate;
	}

	public void setFood2Rate(double kFood2) {
		this.food2Rate = kFood2;
	}

	public double getFood3Rate() {
		return food3Rate;
	}

	public void setFood3Rate(double kFood3) {
		this.food3Rate = kFood3;
	}

	public double getFood4Rate() {
		return food4Rate;
	}

	public void setFood4Rate(double kFood4) {
		this.food4Rate = kFood4;
	}

	public double getFood5Rate() {
		return food5Rate;
	}

	public void setFood5Rate(double kFood5) {
		this.food5Rate = kFood5;
	}

	public double getFood6Rate() {
		return food6Rate;
	}

	public void setFood6Rate(double kFood6) {
		this.food6Rate = kFood6;
	}

	public double getFood7Rate() {
		return food7Rate;
	}

	public void setFood7Rate(double kFood7) {
		this.food7Rate = kFood7;
	}

	public double getFood8Rate() {
		return food8Rate;
	}

	public void setFood8Rate(double kFood8) {
		this.food8Rate = kFood8;
	}

	public double getFood9Rate() {
		return food9Rate;
	}

	public void setFood9Rate(double kFood9) {
		this.food9Rate = kFood9;
	}

	public double getFoodRateBase() {
		return foodRateBase;
	}

	public void setFoodRateBase(double kFoodBase) {
		this.foodRateBase = kFoodBase;
	}

	public double getFoodRateFactor() {
		return foodRateFactor;
	}

	public void setFoodRateFactor(double kFoodFactor) {
		this.foodRateFactor = kFoodFactor;
	}

	public int getFoodFlipInterval() {
		return foodFlipInterval;
	}

	public void setFoodFlipInterval(int iFoodFlip) {
		this.foodFlipInterval = iFoodFlip;
	}

	public int getStressInProduction() {
		return stressInProduction;
	}

	public void setStressInProduction(int pStressIn) {
		this.stressInProduction = pStressIn;
	}

	public int getStressInInterval() {
		return stressInInterval;
	}

	public void setStressInInterval(int iStressIn) {
		this.stressInInterval = iStressIn;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int popSize) {
		this.populationSize = popSize;
	}

	public int getMaxGeneration() {
		return maxGeneration;
	}

	public void setMaxGeneration(int maxGen) {
		this.maxGeneration = maxGen;
	}

	public int getNeutralGenerations() {
		return neutralGenerations;
	}

	public void setNeutralGenerations(int neutralGen) {
		this.neutralGenerations = neutralGen;
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
		this.geneDupProbability = mDupGene;
	}

	public double getGeneDelProbability() {
		return geneDelProbability;
	}

	public void setGeneDelProbability(double mDelGene) {
		this.geneDelProbability = mDelGene;
	}

	public double getProtShapeMutProbability() {
		return protShapeMutProbability;
	}

	public void setProtShapeMutProbability(double mShapeP) {
		this.protShapeMutProbability = mShapeP;
	}

	public double getProtProdMutProbability() {
		return protProdMutProbability;
	}

	public void setProtProdMutProbability(double mProdP) {
		this.protProdMutProbability = mProdP;
	}

	public double getProtDegMutProbability() {
		return protDegMutProbability;
	}

	public void setProtDegMutProbability(double mStabP) {
		this.protDegMutProbability = mStabP;
	}

	public double getBindSiteDupProbability() {
		return bindSiteDupProbability;
	}

	public void setBindSiteDupProbability(double mDupBS) {
		this.bindSiteDupProbability = mDupBS;
	}

	public double getBindSiteDelProbability() {
		return bindSiteDelProbability;
	}

	public void setBindSiteDelProbability(double mDelBS) {
		this.bindSiteDelProbability = mDelBS;
	}

	public double getBindSiteShapeMutProbability() {
		return bindSiteShapeMutProbability;
	}

	public void setBindSiteShapeMutProbability(double mShapeBS) {
		this.bindSiteShapeMutProbability = mShapeBS;
	}

	public double getBindSiteFlipProbability() {
		return bindSiteFlipProbability;
	}

	public void setBindSiteFlipProbability(double mFlipBS) {
		this.bindSiteFlipProbability = mFlipBS;
	}

	public int getSampleGrnInterval() {
		return sampleGrnInterval;
	}

	public void setSampleGrnInterval(int iSampleGRN) {
		this.sampleGrnInterval = iSampleGRN;
	}

	public int getSampleModelInterval() {
		return sampleModelInterval;
	}

	public void setSampleModelInterval(int iSampleModel) {
		this.sampleModelInterval = iSampleModel;
	}

}
