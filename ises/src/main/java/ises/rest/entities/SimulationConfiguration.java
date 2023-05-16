package ises.rest.entities;

public class SimulationConfiguration {
	////////////////////////// Model parameters \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// shape parameters
	private int sMax = 128; // the size of the shape space
	private int dMax = 3; // the maximum 'distance' in shapes for binding

	// translation costs
	private int cRNA = 3; // per translation event
	private int cProtein = 2; // per translated protein

	// basal translation rate
	private double kBasal = 0.01;

	// max protein production and degradation rates added.
	// Not explicitly mentioned in the paper
	private int maxDegRate = 10; // max timesteps a protein can survive
	private int maxProdRate = 10; // max number of proteins a gene can produce

	// energy signalling thresholds
	private int tEnergy1 = 500;
	private int tEnergy2 = 333;

	// energy yields from food (low, med, high)
	// the amount of energy produced when each type of food is available
	private int pEnergy1 = 5;
	private int pEnergy2 = 5;
	private int pEnergy3 = 10;
	private int pEnergy4 = 10;
	private int pEnergy5 = 15;
	private int pEnergy6 = 15;
	private int pEnergy7 = 20;
	private int pEnergy8 = 20;
	private int pEnergy9 = 25;

	// biomass yields from biosynthesis pathways (SAO)
	private int pBio1 = 50;
	private int pBio2 = 10;
	private int pBio3 = 50;
	private int pBio4 = 10;

	// costs for biosynthesis pathways (SAO)
	private int cBio1 = 75;
	private int cBio2 = 75;
	private int cBio3 = 5;
	private int cBio4 = 5;

	// stress parameters
	private int pStress1 = 25; // number of stress molecules removed per rsp activation
	private int pStress2 = 25;
	private int cStress1 = 100; // cost per activation
	private int cStress2 = 100;
	private int tStress1 = 100; // critical threshold; model dies if reached or exceeded
	private int tStress2 = 100;

	/////////////////////////// simulation parameters
	/////////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// max time and starting energy (as in original)
	private int startEnergy = 1000; // the amount of energy with which each model starts
	private int maxTime = 2000; // number of timesteps in the simulation

	// food availability rates
	/**
	 * this implementation assigns a probability to each food and makes it available
	 * to the model at each timestep with this probability
	 * 
	 * the kFood1-9 parameters don't use the values set here; rather their values
	 * are set dynamically based on the base probability (kFoodBase) and the factor
	 * (kFoodFactor) by which kFoodBase is divided or multiplied randomly with
	 * frequency iFoodFlip
	 * 
	 */
	private double kFood1 = 0.0;
	private double kFood2 = 0.0;
	private double kFood3 = 0.0;
	private double kFood4 = 0.0;
	private double kFood5 = 0.0;
	private double kFood6 = 0.0;
	private double kFood7 = 0.0;
	private double kFood8 = 0.0;
	private double kFood9 = 0.0;

	private double kFoodBase = 0.08;
	private double kFoodFactor = 2.0;
	private int iFoodFlip = 100;

	// simulation-side stress parameters (as in original)
	private int pStressIn = 25; // the stress increment; number of molecules added
	private int iStressIn = 25; // number of timesteps before stress enters

	////////////////////////////////// GA parameters \\\\\\\\\\\\\\\\\\\\\\\\\\\\
	// these are all as in original

	private int popSize = 100;
	private int maxGen = 10;
	private int neutralGen = 100; // number of generations of neutral evolution
	private int initGenomeSize = 32;

	// mutation probabilities
	private double mDupGene = 0.001; // gene duplication
	private double mDelGene = 0.001; // gene deletion
	private double mShapeP = 0.005; // protein shape mutation
	private double mProdP = 0.005; // protein production rate mutation
	private double mStabP = 0.005; // protein degradation rate mutation
	private double mDupBS = 0.008; // binding site duplication
	private double mDelBS = 0.008; // binding site deletion
	private double mShapeBS = 0.0008; // binding site shape mutation
	private double mFlipBS = 0.0008; // binding site regulatory flip

	// data collection intervals
	// in use when 'collect data?' is selected in the ui

	// the gene regulatory network of the best model will be saved every x
	// generations
	private int iSampleGRN = 100;

	// the pertinent data from the best model will be saved every x generations
	private int iSampleModel = 50;

	public SimulationConfiguration() {
	}

	public int getsMax() {
		return sMax;
	}

	public void setsMax(int sMax) {
		this.sMax = sMax;
	}

	public int getdMax() {
		return dMax;
	}

	public void setdMax(int dMax) {
		this.dMax = dMax;
	}

	public int getcRNA() {
		return cRNA;
	}

	public void setcRNA(int cRNA) {
		this.cRNA = cRNA;
	}

	public int getcProtein() {
		return cProtein;
	}

	public void setcProtein(int cProtein) {
		this.cProtein = cProtein;
	}

	public double getkBasal() {
		return kBasal;
	}

	public void setkBasal(double kBasal) {
		this.kBasal = kBasal;
	}

	public int getMaxDegRate() {
		return maxDegRate;
	}

	public void setMaxDegRate(int maxDegRate) {
		this.maxDegRate = maxDegRate;
	}

	public int getMaxProdRate() {
		return maxProdRate;
	}

	public void setMaxProdRate(int maxProdRate) {
		this.maxProdRate = maxProdRate;
	}

	public int gettEnergy1() {
		return tEnergy1;
	}

	public void settEnergy1(int tEnergy1) {
		this.tEnergy1 = tEnergy1;
	}

	public int gettEnergy2() {
		return tEnergy2;
	}

	public void settEnergy2(int tEnergy2) {
		this.tEnergy2 = tEnergy2;
	}

	public int getpEnergy1() {
		return pEnergy1;
	}

	public void setpEnergy1(int pEnergy1) {
		this.pEnergy1 = pEnergy1;
	}

	public int getpEnergy2() {
		return pEnergy2;
	}

	public void setpEnergy2(int pEnergy2) {
		this.pEnergy2 = pEnergy2;
	}

	public int getpEnergy3() {
		return pEnergy3;
	}

	public void setpEnergy3(int pEnergy3) {
		this.pEnergy3 = pEnergy3;
	}

	public int getpEnergy4() {
		return pEnergy4;
	}

	public void setpEnergy4(int pEnergy4) {
		this.pEnergy4 = pEnergy4;
	}

	public int getpEnergy5() {
		return pEnergy5;
	}

	public void setpEnergy5(int pEnergy5) {
		this.pEnergy5 = pEnergy5;
	}

	public int getpEnergy6() {
		return pEnergy6;
	}

	public void setpEnergy6(int pEnergy6) {
		this.pEnergy6 = pEnergy6;
	}

	public int getpEnergy7() {
		return pEnergy7;
	}

	public void setpEnergy7(int pEnergy7) {
		this.pEnergy7 = pEnergy7;
	}

	public int getpEnergy8() {
		return pEnergy8;
	}

	public void setpEnergy8(int pEnergy8) {
		this.pEnergy8 = pEnergy8;
	}

	public int getpEnergy9() {
		return pEnergy9;
	}

	public void setpEnergy9(int pEnergy9) {
		this.pEnergy9 = pEnergy9;
	}

	public int getpBio1() {
		return pBio1;
	}

	public void setpBio1(int pBio1) {
		this.pBio1 = pBio1;
	}

	public int getpBio2() {
		return pBio2;
	}

	public void setpBio2(int pBio2) {
		this.pBio2 = pBio2;
	}

	public int getpBio3() {
		return pBio3;
	}

	public void setpBio3(int pBio3) {
		this.pBio3 = pBio3;
	}

	public int getpBio4() {
		return pBio4;
	}

	public void setpBio4(int pBio4) {
		this.pBio4 = pBio4;
	}

	public int getcBio1() {
		return cBio1;
	}

	public void setcBio1(int cBio1) {
		this.cBio1 = cBio1;
	}

	public int getcBio2() {
		return cBio2;
	}

	public void setcBio2(int cBio2) {
		this.cBio2 = cBio2;
	}

	public int getcBio3() {
		return cBio3;
	}

	public void setcBio3(int cBio3) {
		this.cBio3 = cBio3;
	}

	public int getcBio4() {
		return cBio4;
	}

	public void setcBio4(int cBio4) {
		this.cBio4 = cBio4;
	}

	public int getpStress1() {
		return pStress1;
	}

	public void setpStress1(int pStress1) {
		this.pStress1 = pStress1;
	}

	public int getpStress2() {
		return pStress2;
	}

	public void setpStress2(int pStress2) {
		this.pStress2 = pStress2;
	}

	public int getcStress1() {
		return cStress1;
	}

	public void setcStress1(int cStress1) {
		this.cStress1 = cStress1;
	}

	public int getcStress2() {
		return cStress2;
	}

	public void setcStress2(int cStress2) {
		this.cStress2 = cStress2;
	}

	public int gettStress1() {
		return tStress1;
	}

	public void settStress1(int tStress1) {
		this.tStress1 = tStress1;
	}

	public int gettStress2() {
		return tStress2;
	}

	public void settStress2(int tStress2) {
		this.tStress2 = tStress2;
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

	public double getkFood1() {
		return kFood1;
	}

	public void setkFood1(double kFood1) {
		this.kFood1 = kFood1;
	}

	public double getkFood2() {
		return kFood2;
	}

	public void setkFood2(double kFood2) {
		this.kFood2 = kFood2;
	}

	public double getkFood3() {
		return kFood3;
	}

	public void setkFood3(double kFood3) {
		this.kFood3 = kFood3;
	}

	public double getkFood4() {
		return kFood4;
	}

	public void setkFood4(double kFood4) {
		this.kFood4 = kFood4;
	}

	public double getkFood5() {
		return kFood5;
	}

	public void setkFood5(double kFood5) {
		this.kFood5 = kFood5;
	}

	public double getkFood6() {
		return kFood6;
	}

	public void setkFood6(double kFood6) {
		this.kFood6 = kFood6;
	}

	public double getkFood7() {
		return kFood7;
	}

	public void setkFood7(double kFood7) {
		this.kFood7 = kFood7;
	}

	public double getkFood8() {
		return kFood8;
	}

	public void setkFood8(double kFood8) {
		this.kFood8 = kFood8;
	}

	public double getkFood9() {
		return kFood9;
	}

	public void setkFood9(double kFood9) {
		this.kFood9 = kFood9;
	}

	public double getkFoodBase() {
		return kFoodBase;
	}

	public void setkFoodBase(double kFoodBase) {
		this.kFoodBase = kFoodBase;
	}

	public double getkFoodFactor() {
		return kFoodFactor;
	}

	public void setkFoodFactor(double kFoodFactor) {
		this.kFoodFactor = kFoodFactor;
	}

	public int getiFoodFlip() {
		return iFoodFlip;
	}

	public void setiFoodFlip(int iFoodFlip) {
		this.iFoodFlip = iFoodFlip;
	}

	public int getpStressIn() {
		return pStressIn;
	}

	public void setpStressIn(int pStressIn) {
		this.pStressIn = pStressIn;
	}

	public int getiStressIn() {
		return iStressIn;
	}

	public void setiStressIn(int iStressIn) {
		this.iStressIn = iStressIn;
	}

	public int getPopSize() {
		return popSize;
	}

	public void setPopSize(int popSize) {
		this.popSize = popSize;
	}

	public int getMaxGen() {
		return maxGen;
	}

	public void setMaxGen(int maxGen) {
		this.maxGen = maxGen;
	}

	public int getNeutralGen() {
		return neutralGen;
	}

	public void setNeutralGen(int neutralGen) {
		this.neutralGen = neutralGen;
	}

	public int getInitGenomeSize() {
		return initGenomeSize;
	}

	public void setInitGenomeSize(int initGenomeSize) {
		this.initGenomeSize = initGenomeSize;
	}

	public double getmDupGene() {
		return mDupGene;
	}

	public void setmDupGene(double mDupGene) {
		this.mDupGene = mDupGene;
	}

	public double getmDelGene() {
		return mDelGene;
	}

	public void setmDelGene(double mDelGene) {
		this.mDelGene = mDelGene;
	}

	public double getmShapeP() {
		return mShapeP;
	}

	public void setmShapeP(double mShapeP) {
		this.mShapeP = mShapeP;
	}

	public double getmProdP() {
		return mProdP;
	}

	public void setmProdP(double mProdP) {
		this.mProdP = mProdP;
	}

	public double getmStabP() {
		return mStabP;
	}

	public void setmStabP(double mStabP) {
		this.mStabP = mStabP;
	}

	public double getmDupBS() {
		return mDupBS;
	}

	public void setmDupBS(double mDupBS) {
		this.mDupBS = mDupBS;
	}

	public double getmDelBS() {
		return mDelBS;
	}

	public void setmDelBS(double mDelBS) {
		this.mDelBS = mDelBS;
	}

	public double getmShapeBS() {
		return mShapeBS;
	}

	public void setmShapeBS(double mShapeBS) {
		this.mShapeBS = mShapeBS;
	}

	public double getmFlipBS() {
		return mFlipBS;
	}

	public void setmFlipBS(double mFlipBS) {
		this.mFlipBS = mFlipBS;
	}

	public int getiSampleGRN() {
		return iSampleGRN;
	}

	public void setiSampleGRN(int iSampleGRN) {
		this.iSampleGRN = iSampleGRN;
	}

	public int getiSampleModel() {
		return iSampleModel;
	}

	public void setiSampleModel(int iSampleModel) {
		this.iSampleModel = iSampleModel;
	}

}
