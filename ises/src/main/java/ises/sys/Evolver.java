package ises.sys;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ises.model.cellular.Model;
import ises.model.network.GeneRegulatoryNetwork;
import ises.rest.entities.SimulationConfiguration;
import ises.stats.ShapeDistribution;

@Service
@Scope("prototype")
public class Evolver implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(Evolver.class);

	private LinkedList<Model> pop, offspring;
	private int gen, modelCount, grnCount, foodCount;
	private boolean running, done;
	private Model currBest, currWorst;
	private String modelStatus;
	private GeneRegulatoryNetwork currGRN;
	private Vector<GeneRegulatoryNetwork> sampleGRNs;
	private ArrayList<Model> sampleModels;
	private SimulationConfiguration config;
	private final Simulator sim;

	public Evolver(Simulator sim) {
		this.sim = sim;
	}

	public void initializeForRun(SimulationConfiguration config) {
		this.config = config;
		pop = new LinkedList<>();
		offspring = new LinkedList<>();
		gen = 1;
		foodCount = config.getiFoodFlip();
		modelCount = config.getiSampleModel();
		grnCount = config.getiSampleGRN();
		running = false;
		done = false;

		for (int i = 0; i < config.getPopSize(); i++) {
			pop.add(new Model(i, config));
		}

		sim.initializeForRun(config);

		int numGRN = config.getMaxGen() / config.getiSampleGRN();
		if (numGRN < 0) {
			numGRN = 0;
		}

		sampleGRNs = new Vector<>(numGRN + 5);

		int numModels = config.getMaxGen() / config.getiSampleModel();
		if (numModels < 0) {
			numModels = 0;
		}

		sampleModels = new ArrayList<>(numModels + 5);
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

	private void storeCurrBest() {
		sampleModels.add(currBest);
	}

	private void storeCurrGRN() {
		currGRN.setName("generation " + getGen());
		sampleGRNs.add(currGRN);
	}

	@SuppressWarnings("unused")
	private void storeFinalGRN() {
		if (!sampleGRNs.get(sampleGRNs.size() - 1).getName().equals("final GRN")) {
			currGRN.setName("final GRN");
			sampleGRNs.add(currGRN);
		}
	}

	private void flipFoodProbs() {
		ArrayList<Integer> foods = new ArrayList<>();
		ArrayList<Integer> multFoods = new ArrayList<>();

		for (int i = 1; i < 10; i++) {
			foods.add(Integer.valueOf(i));
		}

		Collections.shuffle(foods);

		for (int i = 0; i < 4; i++) {
			multFoods.add(foods.remove(i));
		}

		for (Integer i : multFoods) {
			if (i.intValue() == 1) {
				config.setkFood1(config.getkFoodBase() * config.getkFoodFactor());
			} else if (i.intValue() == 2) {
				config.setkFood2(config.getkFoodBase() * config.getkFoodFactor());
			} else if (i.intValue() == 3) {
				config.setkFood3(config.getkFoodBase() * config.getkFoodFactor());
			} else if (i.intValue() == 4) {
				config.setkFood4(config.getkFoodBase() * config.getkFoodFactor());
			} else if (i.intValue() == 5) {
				config.setkFood5(config.getkFoodBase() * config.getkFoodFactor());
			} else if (i.intValue() == 6) {
				config.setkFood6(config.getkFoodBase() * config.getkFoodFactor());
			} else if (i.intValue() == 7) {
				config.setkFood7(config.getkFoodBase() * config.getkFoodFactor());
			} else if (i.intValue() == 8) {
				config.setkFood8(config.getkFoodBase() * config.getkFoodFactor());
			} else if (i.intValue() == 9) {
				config.setkFood9(config.getkFoodBase() * config.getkFoodFactor());
			}
		}

		for (Integer i : foods) {
			if (i.intValue() == 1) {
				config.setkFood1(config.getkFoodBase() / config.getkFoodFactor());
			} else if (i.intValue() == 2) {
				config.setkFood2(config.getkFoodBase() / config.getkFoodFactor());
			} else if (i.intValue() == 3) {
				config.setkFood3(config.getkFoodBase() / config.getkFoodFactor());
			} else if (i.intValue() == 4) {
				config.setkFood4(config.getkFoodBase() / config.getkFoodFactor());
			} else if (i.intValue() == 5) {
				config.setkFood5(config.getkFoodBase() / config.getkFoodFactor());
			} else if (i.intValue() == 6) {
				config.setkFood6(config.getkFoodBase() / config.getkFoodFactor());
			} else if (i.intValue() == 7) {
				config.setkFood7(config.getkFoodBase() / config.getkFoodFactor());
			} else if (i.intValue() == 8) {
				config.setkFood8(config.getkFoodBase() / config.getkFoodFactor());
			} else if (i.intValue() == 9) {
				config.setkFood9(config.getkFoodBase() / config.getkFoodFactor());
			}
		}

	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public LinkedList<Model> getPop() {
		return pop;
	}

	public int getGen() {
		return gen;
	}

	private void preEvolve() {
		for (int i = 0; i < config.getNeutralGen(); i++) {
			for (Model m : pop) {
				m.preEvolve();
			}
		}

	}

	public boolean isDone() {
		return done;
	}

	private void nextGen() {
		if (gen > config.getMaxGen()) {
			running = false;
			done = true;

			return;
		}

		logger.debug("GA running generation " + gen + "...");
		Model best, worst;

		if (foodCount == config.getiFoodFlip()) {
			flipFoodProbs();
			foodCount = 0;
		}

		offspring = new LinkedList<>();
		Collections.sort(pop);

		best = pop.getLast();
		worst = pop.getFirst();

		currGRN = (GeneRegulatoryNetwork) best.getGRN().clone();
		currBest = new Model(best);
		currWorst = new Model(worst);

		// sample data
		if (modelCount == config.getiSampleModel()) {
			modelCount = 0;
			storeCurrBest();
		}

		if (grnCount == config.getiSampleGRN()) {
			grnCount = 0;
			storeCurrGRN();
		}

		int n = config.getPopSize() / 2;

		while (pop.size() > n) {
			pop.removeFirst();
		}

		for (Model m : pop) {
			offspring.add(m.replicate());
		}

		pop.addAll(offspring);

		for (Model m : pop) {
			m.mutate();
		}

		gen++;
		modelCount++;
		grnCount++;
		foodCount++;

		logger.debug(getModelStatus());
	}

	@Override
	public void run() {
		logger.info("Starting run");
		preEvolve(); // neutral evolution for config.neutralGen generations
		start();

		while (running) {
			simulatePop();
			nextGen();

			running = !done;
		}

		logger.debug("GA done");
		logger.info("Finished run");
	}

	private void simulatePop() {
		for (Model m : getPop()) {
			sim.setCurrModel(m);
			sim.go();
		}
	}

	@SuppressWarnings("unused")
	private void prepareData() {
		int size = sampleModels.size();

		ArrayList<String> modelStats = new ArrayList<>(size);
		ArrayList<String> shapeDistros = new ArrayList<>(size);
		ArrayList<String> shapeStats = new ArrayList<>(size);

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

			for (String s : shapeStats) {
				shapeStatsFile.println(s);
			}

			shapeStatsFile.close();

			for (String s : shapeDistros) {
				shapeDistrosFile.println(s);
			}

			shapeDistrosFile.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String getModelStatus() {
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

}
