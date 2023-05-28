package ises.system;

import java.util.Collections;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import ises.model.cellular.Model;
import ises.model.network.GeneRegulatoryNetwork;
import ises.rest.entities.SimulationConfiguration;
import ises.rest.entities.SimulationStatus;
import ises.rest.entities.dto.GrnDto;
import ises.rest.entities.dto.ModelDto;
import ises.rest.entities.dto.ShapeDistributionDto;
import ises.rest.jpa.SimulationConfigurationRepository;
import ises.stats.ShapeDistribution;

/**
 * Provides the genetic algorithm to evolve a population of model 'organisms' and collect data about those organisms
 * throughout the run.
 */
@Service
@Scope("prototype")
public class Evolver implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(Evolver.class);

	private final ApplicationContext applicationContext;
	private final Simulator sim;
	private final AsyncTaskExecutor executor;
	private final SimulationConfigurationRepository simulationRepo;
	private DataStorageRunner dataStorageRunner;
	private LinkedList<Model> population, offspring;
	private int generation, modelSampleCounter, foodFlipCounter;
	private boolean running, done;
	private Model currBest, currWorst;
	private String modelStatus;
	private GeneRegulatoryNetwork currGRN;
	private SimulationConfiguration config;

	public Evolver(Simulator sim, SimulationConfigurationRepository simulationRepo, @Qualifier("dataStorageExecutor") AsyncTaskExecutor executor,
			ApplicationContext applicationContext) {

		this.sim = sim;
		this.executor = executor;
		this.applicationContext = applicationContext;
		this.simulationRepo = simulationRepo;
	}

	public void initializeForRun(SimulationConfiguration config) {
		this.config = config;
		population = new LinkedList<>();
		offspring = new LinkedList<>();
		generation = 1;
		foodFlipCounter = config.getFoodFlipInterval();
		modelSampleCounter = config.getSampleModelInterval();
		running = false;
		done = false;

		for (int i = 0; i < config.getPopulationSize(); i++) {
			population.add(new Model(i, config));
		}

		sim.initializeForRun(config);
	}

	@JmsListener(destination = Constants.CANCEL_QUEUE_NAME, containerFactory = "simFactory")
	public void receiveCancelMessage(Long idToCancel) {
		if (config != null && idToCancel.equals(config.getId())) {
			cancel();
			return;
		}

		logger.debug("Received request to cancel simulation " + idToCancel + " but my simulation is " + config.getId() + "; ignoring");
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
		ShapeDistributionDto shapeDistroDto = new ShapeDistributionDto(new ShapeDistribution(currBest), modelDto);
		GrnDto grnDto = new GrnDto(currGRN, config, modelDto);
		dataStorageRunner = applicationContext.getBean(DataStorageRunner.class);
		dataStorageRunner.initForRun(modelDto, shapeDistroDto, grnDto);

		executor.execute(dataStorageRunner);
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

		if (foodFlipCounter == config.getFoodFlipInterval()) {
			sim.flipFoodProbs();
			foodFlipCounter = 0;
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
		if (modelSampleCounter == config.getSampleModelInterval()) {
			modelSampleCounter = 0;
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
		modelSampleCounter++;
		foodFlipCounter++;

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

		config.setStatus(SimulationStatus.DONE);
		simulationRepo.save(config);

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
				+ currBest.getEnergy() + "\n" + "Stress1: " + currBest.getStress1() + "\nStress2: " + currBest.getStress2() + "\n" + "Biomass: "
				+ currBest.getBiomass() + "\n" + "Ancestral index: " + currBest.getAncestralIndex() + "\n" + "# binding sites: "
				+ currBest.getNumSites() + "\n" + "# genes: " + currBest.getNumGenes() + "\n\n" +

				"Worst Model\n" + "----------------------------\n" + "Fitness: " + currWorst.getFitness() + "\n" + "Energy: " + currWorst.getEnergy()
				+ "\n" + "Stress1: " + currWorst.getStress1() + "\nStress2: " + currWorst.getStress2() + "\n" + "Biomass: " + currWorst.getBiomass()
				+ "\n" + "Ancestral index: " + currWorst.getAncestralIndex() + "\n" + "# binding sites: " + currWorst.getNumSites() + "\n"
				+ "# genes: " + currWorst.getNumGenes() + "\n\n";

		return modelStatus;
	}

	private void cancel() {
		logger.debug("Cancelling simulation " + config.getId());
		running = false;
		done = true;
		config.setStatus(SimulationStatus.CANCELLED);
		simulationRepo.save(config);

	}

}
