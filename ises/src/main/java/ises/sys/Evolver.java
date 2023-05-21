package ises.sys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import ises.model.cellular.Model;
import ises.model.network.GeneRegulatoryNetwork;
import ises.rest.entities.SimulationConfiguration;
import ises.rest.entities.dto.GrnDto;
import ises.rest.entities.dto.ModelDto;
import ises.rest.entities.dto.ShapeDistributionDto;
import ises.stats.ShapeDistribution;

/**
 * Provides the genetic algorithm to evolve a population of model 'organisms' and collect data about those organisms
 * throughout the run.
 */
@Service
@Scope("prototype")
public class Evolver implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(Evolver.class);

	private final Simulator sim;
	private final DataStorageRunner dataStorageRunner;
	private final AsyncTaskExecutor executor;
	private LinkedList<Model> population, offspring;
	private int generation, modelCount, foodCount;
	private boolean running, done;
	private Model currBest, currWorst;
	private String modelStatus;
	private GeneRegulatoryNetwork currGRN;
	private SimulationConfiguration config;

	public Evolver(Simulator sim, DataStorageRunner dataStorageRunner, @Qualifier("dataStorageExecutor") AsyncTaskExecutor executor) {
		this.sim = sim;
		this.dataStorageRunner = dataStorageRunner;
		this.executor = executor;
	}

	public void initializeForRun(SimulationConfiguration config) {
		this.config = config;
		population = new LinkedList<>();
		offspring = new LinkedList<>();
		generation = 1;
		foodCount = config.getFoodFlipInterval();
		modelCount = config.getSampleModelInterval();
		running = false;
		done = false;

		for (int i = 0; i < config.getPopulationSize(); i++) {
			population.add(new Model(i, config));
		}

		sim.initializeForRun(config);
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

	private void storeData() {
		ModelDto modelDto = new ModelDto(currBest);
		modelDto.setGeneration(generation);
		ShapeDistributionDto shapeDistroDto = new ShapeDistributionDto(new ShapeDistribution(currBest));
		GrnDto grnDto = new GrnDto(currGRN, config);
		dataStorageRunner.initForRun(modelDto, shapeDistroDto, grnDto);

		executor.execute(dataStorageRunner);
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
				config.setFood1Rate(config.getFoodRateBase() * config.getFoodRateFactor());
			} else if (i.intValue() == 2) {
				config.setFood2Rate(config.getFoodRateBase() * config.getFoodRateFactor());
			} else if (i.intValue() == 3) {
				config.setFood3Rate(config.getFoodRateBase() * config.getFoodRateFactor());
			} else if (i.intValue() == 4) {
				config.setFood4Rate(config.getFoodRateBase() * config.getFoodRateFactor());
			} else if (i.intValue() == 5) {
				config.setFood5Rate(config.getFoodRateBase() * config.getFoodRateFactor());
			} else if (i.intValue() == 6) {
				config.setFood6Rate(config.getFoodRateBase() * config.getFoodRateFactor());
			} else if (i.intValue() == 7) {
				config.setFood7Rate(config.getFoodRateBase() * config.getFoodRateFactor());
			} else if (i.intValue() == 8) {
				config.setFood8Rate(config.getFoodRateBase() * config.getFoodRateFactor());
			} else if (i.intValue() == 9) {
				config.setFood9Rate(config.getFoodRateBase() * config.getFoodRateFactor());
			}
		}

		for (Integer i : foods) {
			if (i.intValue() == 1) {
				config.setFood1Rate(config.getFoodRateBase() / config.getFoodRateFactor());
			} else if (i.intValue() == 2) {
				config.setFood2Rate(config.getFoodRateBase() / config.getFoodRateFactor());
			} else if (i.intValue() == 3) {
				config.setFood3Rate(config.getFoodRateBase() / config.getFoodRateFactor());
			} else if (i.intValue() == 4) {
				config.setFood4Rate(config.getFoodRateBase() / config.getFoodRateFactor());
			} else if (i.intValue() == 5) {
				config.setFood5Rate(config.getFoodRateBase() / config.getFoodRateFactor());
			} else if (i.intValue() == 6) {
				config.setFood6Rate(config.getFoodRateBase() / config.getFoodRateFactor());
			} else if (i.intValue() == 7) {
				config.setFood7Rate(config.getFoodRateBase() / config.getFoodRateFactor());
			} else if (i.intValue() == 8) {
				config.setFood8Rate(config.getFoodRateBase() / config.getFoodRateFactor());
			} else if (i.intValue() == 9) {
				config.setFood9Rate(config.getFoodRateBase() / config.getFoodRateFactor());
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
		for (int i = 0; i < config.getNeutralGenerations(); i++) {
			for (Model m : population) {
				m.preEvolve();
			}
		}

	}

	private void nextGen() {
		if (generation > config.getMaxGeneration()) {
			running = false;
			done = true;

			return;
		}

		logger.debug("GA running generation " + generation + "...");
		Model best, worst;

		if (foodCount == config.getFoodFlipInterval()) {
			flipFoodProbs();
			foodCount = 0;
		}

		offspring = new LinkedList<>();
		Collections.sort(population);

		best = population.getLast();
		worst = population.getFirst();

		currGRN = (GeneRegulatoryNetwork) best.getGRN().clone();
		currGRN.setName("Generation " + generation);
		currBest = new Model(best);
		currWorst = new Model(worst);

		// sample data
		if (modelCount == config.getSampleModelInterval()) {
			modelCount = 0;
			storeData();
		}

		int n = config.getPopulationSize() / 2;

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
