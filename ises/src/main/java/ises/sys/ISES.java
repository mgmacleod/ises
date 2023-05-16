package ises.sys;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ises.model.cellular.Model;
import ises.model.network.GRN;
import ises.rest.entities.SimulationConfiguration;
import ises.sim.Simulator;
import ises.stats.ShapeDistribution;

@Service
@Scope("prototype")
public class ISES implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(ISES.class);

	private GA ga;
	private Simulator sim;
	private Model currBest, currWorst;
	private boolean running, done;

	private String modelStatus, gaSimStatus;
	private GRN currGRN;

	private Vector<GRN> sampleGRNs;
	private ArrayList<Model> sampleModels;
	private SimulationConfiguration config;

	public ISES() {
	}

	public void init(SimulationConfiguration config) {
		this.config = config;
		ga = new GA(this, config);
		sim = new Simulator(config);
		running = false;
		done = false;

		int numGRN = config.getMaxGen() / config.getiSampleGRN();
		if (numGRN < 0)
			numGRN = 0;

		sampleGRNs = new Vector<GRN>(numGRN + 5);

		int numModels = config.getMaxGen() / config.getiSampleModel();
		if (numModels < 0)
			numModels = 0;

		sampleModels = new ArrayList<Model>(numModels + 5);

	}

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

	public void nextGen() {
		ga.nextGen();
	}

	public void storeCurrBest() {
		sampleModels.add(currBest);
	}

	public void storeCurrGRN() {
		currGRN.setName("generation " + ga.getGen());
		sampleGRNs.add(currGRN);
	}

	public void storeFinalGRN() {
		if (!sampleGRNs.get(sampleGRNs.size() - 1).getName().equals("final GRN")) {
			currGRN.setName("final GRN");
			sampleGRNs.add(currGRN);
		}
	}

	public void prepareData() {
		int size = sampleModels.size();

		ArrayList<String> modelStats = new ArrayList<String>(size);
		ArrayList<String> shapeDistros = new ArrayList<String>(size);
		ArrayList<String> shapeStats = new ArrayList<String>(size);

		for (Model m : sampleModels) {
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

			modelStatsFile.println("#si " + config.getiSampleModel());
			modelStatsFile.println("#nm " + sampleModels.size());
			for (String s : modelStats) {
				modelStatsFile.println(s);
			}

			modelStatsFile.close();

			for (String s : shapeStats)
				shapeStatsFile.println(s);

			shapeStatsFile.close();

			for (String s : shapeDistros)
				shapeDistrosFile.println(s);

			shapeDistrosFile.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean isDone() {
		return done;
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

	public void simulatePop() {
		for (Model m : ga.getPop()) {
			sim.setCurrModel(m);
			sim.go();
		}
	}

	public void start() {
		running = true;
	}

	public void pause() {
		running = false;
	}

	public void stop() {
		running = false;
	}

	public void run() {
		logger.info("Starting run");
		ga.preEvolve(); // neutral evolution for config.neutralGen generations
		start();

		while (running) {
			logger.debug("GA running generation " + getGa().getGen() + "...");
			simulatePop();

			nextGen();
			logger.debug(getModelStatus());

			running = !ga.isDone();
		}

		logger.debug("GA done");
		logger.info("Finished run");
	}

	public String getModelStatus() {
		modelStatus = "\nBest Model\n" + "----------------------------\n" + "Fitness: " + currBest.getFitness() + "\n"
				+ "Energy: " + currBest.getEnergy() + "\n" + "Stress: " + currBest.getStress() + "\n" + "Biomass: "
				+ currBest.getBiomass() + "\n" + "Ancestral index: " + currBest.getAncestralIndex() + "\n"
				+ "# binding sites: " + currBest.getNumSites() + "\n" + "# genes: " + currBest.getNumGenes() + "\n\n" +

				"Worst Model\n" + "----------------------------\n" + "Fitness: " + currWorst.getFitness() + "\n"
				+ "Energy: " + currWorst.getEnergy() + "\n" + "Stress: " + currWorst.getStress() + "\n" + "Biomass: "
				+ currWorst.getBiomass() + "\n" + "Ancestral index: " + currWorst.getAncestralIndex() + "\n"
				+ "# binding sites: " + currWorst.getNumSites() + "\n" + "# genes: " + currWorst.getNumGenes() + "\n\n";

		return modelStatus;
	}

	public void setConfig(SimulationConfiguration config) {
		this.config = config;
	}

}
