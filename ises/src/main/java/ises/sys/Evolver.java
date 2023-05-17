package ises.sys;

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

/**
 * Provides the genetic algorithm to evolve a population of model 'organisms' and collect data about those organisms
 * throughout the run
 */
@Service
@Scope("prototype")
public class Evolver implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(Evolver.class);

	private LinkedList<Model> population, offspring;
	private int generation, modelCount, grnCount, foodCount;
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
		population = new LinkedList<>();
		offspring = new LinkedList<>();
		generation = 1;
		foodCount = config.getiFoodFlip();
		modelCount = config.getiSampleModel();
		grnCount = config.getiSampleGRN();
		running = false;
		done = false;

		for (int i = 0; i < config.getPopSize(); i++) {
			population.add(new Model(i, config));
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
		currGRN.setName("generation " + generation);
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

	private void preEvolve() {
		for (int i = 0; i < config.getNeutralGen(); i++) {
			for (Model m : population) {
				m.preEvolve();
			}
		}

	}

	private void nextGen() {
		if (generation > config.getMaxGen()) {
			running = false;
			done = true;

			return;
		}

		logger.debug("GA running generation " + generation + "...");
		Model best, worst;

		if (foodCount == config.getiFoodFlip()) {
			flipFoodProbs();
			foodCount = 0;
		}

		offspring = new LinkedList<>();
		Collections.sort(population);

		best = population.getLast();
		worst = population.getFirst();

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

		while (population.size() > n) {
			population.removeFirst();
		}

		for (Model m : population) {
			offspring.add(m.replicate());
		}

		population.addAll(offspring);

		for (Model m : population) {
			m.mutate();
		}

		generation++;
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
			simulatePopulation();
			nextGen();

			running = !done;
		}

		logger.debug("GA done");
		logger.info("Finished run");
	}

	private void simulatePopulation() {
		for (Model m : population) {
			sim.setCurrModel(m);
			sim.start();
		}
	}

	private String getModelStatus() {
		modelStatus = "\nBest Model\n" + "----------------------------\n" + "Fitness: " + currBest.getFitness() + "\n" + "Energy: "
				+ currBest.getEnergy() + "\n" + "Stress: " + currBest.getStress() + "\n" + "Biomass: " + currBest.getBiomass() + "\n"
				+ "Ancestral index: " + currBest.getAncestralIndex() + "\n" + "# binding sites: " + currBest.getNumSites() + "\n" + "# genes: "
				+ currBest.getNumGenes() + "\n\n" +

				"Worst Model\n" + "----------------------------\n" + "Fitness: " + currWorst.getFitness() + "\n" + "Energy: " + currWorst.getEnergy()
				+ "\n" + "Stress: " + currWorst.getStress() + "\n" + "Biomass: " + currWorst.getBiomass() + "\n" + "Ancestral index: "
				+ currWorst.getAncestralIndex() + "\n" + "# binding sites: " + currWorst.getNumSites() + "\n" + "# genes: " + currWorst.getNumGenes()
				+ "\n\n";

		return modelStatus;
	}

}
