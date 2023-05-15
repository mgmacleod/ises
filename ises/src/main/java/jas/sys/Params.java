package jas.sys;

public class Params {
	// Except where explicitly noted, everything as as in the original paper

	////////////////////////// Model parameters \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// shape parameters
	public static int sMax = 128; // the size of the shape space
	public static int dMax = 3; // the maximum 'distance' in shapes for binding

	// translation costs
	public static int cRNA = 3; // per translation event
	public static int cProtein = 2; // per translated protein

	// basal translation rate
	public static double kBasal = 0.01;

	// max protein production and degradation rates added.
	// Not explicitly mentioned in the paper
	public static int maxDegRate = 10; // max timesteps a protein can survive
	public static int maxProdRate = 10; // max number of proteins a gene can produce

	// energy signalling thresholds
	public static int tEnergy1 = 500;
	public static int tEnergy2 = 333;

	// energy yields from food (low, med, high)
	// the amount of energy produced when each type of food is available
	public static int pEnergy1 = 5;
	public static int pEnergy2 = 5;
	public static int pEnergy3 = 10;
	public static int pEnergy4 = 10;
	public static int pEnergy5 = 15;
	public static int pEnergy6 = 15;
	public static int pEnergy7 = 20;
	public static int pEnergy8 = 20;
	public static int pEnergy9 = 25;

	// biomass yields from biosynthesis pathways (SAO)
	public static int pBio1 = 50;
	public static int pBio2 = 10;
	public static int pBio3 = 50;
	public static int pBio4 = 10;

	// costs for biosynthesis pathways (SAO)
	public static int cBio1 = 75;
	public static int cBio2 = 75;
	public static int cBio3 = 5;
	public static int cBio4 = 5;

	// stress parameters
	public static int pStress1 = 25; // number of stress molecules removed per rsp activation
	public static int pStress2 = 25;
	public static int cStress1 = 100; // cost per activation
	public static int cStress2 = 100;
	public static int tStress1 = 100; // critical threshold; model dies if reached or exceeded
	public static int tStress2 = 100;

	/////////////////////////// simulation parameters
	/////////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// max time and starting energy (as in original)
	public static int startEnergy = 1000; // the amount of energy with which each model starts
	public static int maxTime = 2000; // number of timesteps in the simulation

	// food availability rates (not explicitly described in the original)
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
	public static double kFood1 = 0.0;
	public static double kFood2 = 0.0;
	public static double kFood3 = 0.0;
	public static double kFood4 = 0.0;
	public static double kFood5 = 0.0;
	public static double kFood6 = 0.0;
	public static double kFood7 = 0.0;
	public static double kFood8 = 0.0;
	public static double kFood9 = 0.0;

	public static double kFoodBase = 0.08;
	public static double kFoodFactor = 2.0;
	public static int iFoodFlip = 100;

	// simulation-side stress parameters (as in original)
	public static int pStressIn = 25; // the stress increment; number of molecules added
	public static int iStressIn = 25; // number of timesteps before stress enters

	////////////////////////////////// GA parameters \\\\\\\\\\\\\\\\\\\\\\\\\\\\
	// these are all as in original

	public static int popSize = 100;
	public static int maxGen = 1000;
	public static int neutralGen = 100; // number of generations of neutral evolution
	public static int initGenomeSize = 32;

	// mutation probabilities
	public static double mDupGene = 0.001; // gene duplication
	public static double mDelGene = 0.001; // gene deletion
	public static double mShapeP = 0.005; // protein shape mutation
	public static double mProdP = 0.005; // protein production rate mutation
	public static double mStabP = 0.005; // protein degradation rate mutation
	public static double mDupBS = 0.008; // binding site duplication
	public static double mDelBS = 0.008; // binding site deletion
	public static double mShapeBS = 0.0008; // binding site shape mutation
	public static double mFlipBS = 0.0008; // binding site regulatory flip

	// data collection intervals
	// in use when 'collect data?' is selected in the ui

	// the gene regulatory network of the best model will be saved every x
	// generations
	public static int iSampleGRN = 100;

	// the pertinent data from the best model will be saved every x generations
	public static int iSampleModel = 50;

	// initialization methods, called from the 'presets' menu in the main
	// application window
	// they only affect food availability rates; everything else will be as
	// specified above

	public static void initEasy() {
		kFoodBase = 0.12;
		kFoodFactor = 1.0;
		iFoodFlip = maxGen + 1;
	}

	public static void initMedium() {
		kFoodBase = 0.08;
		kFoodFactor = 1.0;
		iFoodFlip = maxGen + 1;
	}

	public static void initEasyFlip() {
		kFoodBase = 0.12;
		kFoodFactor = 2.0;
		iFoodFlip = 100;
	}

	public static void initMediumFlip() {
		kFoodBase = 0.08;
		kFoodFactor = 2.0;
		iFoodFlip = 100;
	}

}
