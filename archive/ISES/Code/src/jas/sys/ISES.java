package jas.sys;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import jas.Thing;
import jas.gui.ProgramFrame;
import jas.model.cellular.Model;
import jas.model.network.GRN;
import jas.sim.Simulator;
import jas.stats.ShapeDistribution;

public class ISES extends Thing {
	
	/////////////////////////////  instance variables  \\\\\\\\\\\\\\\\\\\\\\\\
	
	protected GA				ga;
	protected Simulator			sim;
	protected Model				currBest, currWorst;
	protected boolean			running, done, collectingData;
	
	protected String			modelStatus, gaSimStatus;
	protected ProgramFrame		progFrame;
	protected GRN				currGRN;
	
	protected Vector<GRN>		sampleGRNs;
	protected ArrayList<Model>	sampleModels;
	
	
	////////////////////////////  constructors  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	

	public ISES(boolean collData) {
		collectingData = collData;
		ga = new GA(this, collData);
		sim = new Simulator(this);
		running = false;
		done = false;
		
		
		int numGRN = Params.maxGen / Params.iSampleGRN;
		if (numGRN < 0)
			numGRN = 0;
		
		sampleGRNs = new Vector<GRN>(numGRN + 5);
		
		int numModels = Params.maxGen / Params.iSampleModel;
		if (numModels < 0)
			numModels = 0;
		
		sampleModels = new ArrayList<Model>(numModels + 5);
		
		ga.preEvolve(); // neutral evolution for Params.neutralGen generations
	}
	
	public ISES(ProgramFrame c, boolean collData) {
		this(collData);
		progFrame = c;
	}
	
	
	
	
	///////////////////////////////  Methods  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public Model getCurrBest() {
		return currBest;
	}

	public GA getGa() {
		return ga;
	}

	public String getGaSimStatus() {
		return gaSimStatus;
	}

	public Simulator getSim() {
		return sim;
	}

	public void go() {
		running = true;
		
		while (running) {
			goOnce();
			
		
		}
	}
	
	public GRN getCurrGRN() {
		return currGRN;
	}

	public void setCurrGRN(GRN currGRN) {
		this.currGRN = currGRN;
	}
	
	public Vector<GRN> getSampleGRNs() {
		return sampleGRNs;
	}
	
	public ArrayList<Model> getSampleModels() {
		return sampleModels;
	}

	public void goOnce() {
		
		
		
	}
	
	public void nextGen() {
		ga.nextGen();
		//currBest = new Model(ga.getBest());
		//currWorst = new Model(ga.getWorst());
	}
	
	public void storeCurrBest() {
		sampleModels.add(currBest);
	}
	
	public void storeCurrGRN() {
		//int i = sampleGRNs.size() * Params.iSampleGRN;
		
		currGRN.setName("generation " + ga.getGen());
		sampleGRNs.add(currGRN);
	}
	
	public void storeFinalGRN() {
		if (!sampleGRNs.get(sampleGRNs.size()-1).getName().equals("final GRN")) {
			currGRN.setName("final GRN");
			sampleGRNs.add(currGRN);
		}
	}
	
	public void prepareData() {
		int size = sampleModels.size();
		
		ArrayList<String> modelStats = new ArrayList<String>(size);
		ArrayList<String> shapeDistros = new ArrayList<String>(size);
		ArrayList<String> shapeStats = new ArrayList<String>(size);
		
		for (Model m: sampleModels) {
			modelStats.add(m.getStatString());
			
			ShapeDistribution sd = new ShapeDistribution(m);
			shapeDistros.add(sd.getDistroString());
			shapeStats.add(sd.getStatString());
			
		}
		
		try {
			String sep = File.separator;
			String filename = "." + sep + "data" + sep + "modelStats.txt";
			PrintWriter modelStatsFile = new PrintWriter(new FileWriter(filename));
			filename = "." + sep + "data" + sep + "shapeStats.txt";
			PrintWriter shapeStatsFile = new PrintWriter(new FileWriter(filename));
			filename = "." + sep + "data" + sep + "shapeDistros.txt";
			PrintWriter shapeDistrosFile = new PrintWriter(new FileWriter(filename));
			
			
			modelStatsFile.println("#si " + Params.iSampleModel);
			modelStatsFile.println("#nm " + sampleModels.size());
			for (String s: modelStats) {
				modelStatsFile.println(s);
			}
			
			modelStatsFile.close();
			
			for (String s: shapeStats) 
				shapeStatsFile.println(s);
			
			shapeStatsFile.close();
			
			for (String s: shapeDistros)
				shapeDistrosFile.println(s);
			
			shapeDistrosFile.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		/*
		ArrayList<Integer> energyVals = new ArrayList<Integer>(sampleModels.size());
		ArrayList<Integer> biomassVals = new ArrayList<Integer>(sampleModels.size());
		ArrayList<Integer> fitnessVals = new ArrayList<Integer>(sampleModels.size());
		ArrayList<Integer> nGeneVals = new ArrayList<Integer>(sampleModels.size());
		ArrayList<Integer> nSiteVals = new ArrayList<Integer>(sampleModels.size());
		
		
		
		for (Model m: sampleModels) {
			energyVals.add(new Integer(m.getEnergy()));
			biomassVals.add(new Integer(m.getBiomass()));
			fitnessVals.add(new Integer(m.getFitness()));
			nGeneVals.add(new Integer(m.getNumGenes()));
			nSiteVals.add(new Integer(m.getNumSites()));
		}
		
		try {
			PrintWriter energyFile = new PrintWriter(new FileWriter("energy.txt"));
			PrintWriter biomassFile = new PrintWriter(new FileWriter("biomass.txt"));
			PrintWriter fitnessFile = new PrintWriter(new FileWriter("fitness.txt"));
			PrintWriter nGeneFile = new PrintWriter(new FileWriter("nGene.txt"));
			PrintWriter nSiteFile = new PrintWriter(new FileWriter("nSite.txt"));
			
			energyFile.println("#si " + Params.iSampleModel);
			energyFile.println("#nm " + sampleModels.size());
			for (Integer i: energyVals) {
				String s = "" + i;
				energyFile.println(s);
			}
			
			energyFile.close();
			
			for (Integer i: biomassVals)
				biomassFile.println("" + i);
			
			biomassFile.close();
			
			for (Integer i: fitnessVals)
				fitnessFile.println("" + i);
			
			fitnessFile.close();
			
			for (Integer i: nGeneVals)
				nGeneFile.println("" + i);
			
			nGeneFile.close();
			
			for (Integer i: nSiteVals)
				nSiteFile.println("" + i);
			
			nSiteFile.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		*/
	}
	
	public void initDataLists() {
		int numGRN = Params.maxGen / Params.iSampleGRN;
		if (numGRN < 0)
			numGRN = 0;
		
		sampleGRNs = new Vector<GRN>(numGRN + 5);
		
		int numModels = Params.maxGen / Params.iSampleModel;
		if (numModels < 0)
			numModels = 0;
		
		sampleModels = new ArrayList<Model>(numModels + 5);
	}

	public boolean isDone() {
		return done;
	}
	
	public void pause() {
		running = false;
	
	}
	
	public void resume() {
		go();
	}
	
	public void setCurrBest(Model cb) {
		currBest = cb;
		
	}
	
	public void setCurrWorst(Model cw) {
		currWorst = cw;
	}
	
	public void setDone(boolean done) {
		this.done = done;
	}
	
	public void setCollectingData(boolean cd) {
		collectingData = cd;
		ga.setCollectingData(cd);
		
	}
	
	public void simulatePop() {
		for (Model m: ga.getPop()) {
			sim.setCurrModel(m);
			sim.go();
		}
	}
	
	public void stop() {
		running = false;
	}
	
	public String getModelStatus() {
		modelStatus = 	"Best Model\n" + 
						"----------------------------\n" +
						"Fitness: " + currBest.getFitness() + "\n" +
						"Energy: " + currBest.getEnergy() + "\n" +
						"Stress: " + currBest.getStress() + "\n" +
						"Biomass: " + currBest.getBiomass() + "\n" + 
						"Ancestral index: " + currBest.getAncestralIndex() + "\n" +
						"# binding sites: " + currBest.getNumSites() + "\n" +
						"# genes: " + currBest.getNumGenes() + "\n\n" +
						
						"Worst Model\n" +
						"----------------------------\n" +
						"Fitness: " + currWorst.getFitness() + "\n" +
						"Energy: " + currWorst.getEnergy() + "\n" +
						"Stress: " + currWorst.getStress() + "\n" +
						"Biomass: " + currWorst.getBiomass() + "\n" +
						"Ancestral index: " + currWorst.getAncestralIndex() + "\n" + 
						"# binding sites: " + currWorst.getNumSites() + "\n" +
						"# genes: " + currWorst.getNumGenes() + "\n\n";
						
		return modelStatus;
	}
	
	public void updateUI() {
		
	}
	
	public void prepareGRN(Rectangle bounds) {
		//currGRN.calcLocations(bounds);
		//currBest.prepareGRN(bounds);
	}

}
