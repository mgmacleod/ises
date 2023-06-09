package ises.system;

import java.util.Collections;
import java.util.LinkedList;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Provides the genetic algorithm to evolve a population of model 'organisms'
 * and collect data about those organisms
 * throughout the run.
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Scope("prototype")
public class Evolver implements Runnable {

	private final ApplicationContext applicationContext;
	private final Simulator simulator;
	private final AsyncTaskExecutor dataStorageExecutor;
	private final SimulationConfigurationRepository simulationRepo;
	private DataStorageRunner dataStorageRunner;
	private LinkedList<Model> population, offspring;
	private int generation, modelSampleCounter, foodFlipCounter;
	private boolean running, done;
	private Model currBest, currWorst;
	private String modelStatus;
	private GeneRegulatoryNetwork currGRN;
	private SimulationConfiguration config;

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

		simulator.initializeForRun(config);
	}

	@JmsListener(destination = Constants.CANCEL_QUEUE_NAME, containerFactory = "simFactory")
	public void receiveCancelMessage(Long idToCancel) {
		if (config != null && idToCancel.equals(config.getId())) {
			cancel();
			return;
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

	private void storeData() {
		ModelDto modelDto = new ModelDto(currBest);
		modelDto.setGeneration(generation == 1 ? generation : generation - 1);
		ShapeDistributionDto shapeDistroDto = new ShapeDistributionDto(new ShapeDistribution(currBest), modelDto);
		GrnDto grnDto = new GrnDto(currGRN, config, modelDto);
		dataStorageRunner = applicationContext.getBean(DataStorageRunner.class);
		dataStorageRunner.initForRun(modelDto, shapeDistroDto, grnDto);

		dataStorageExecutor.execute(dataStorageRunner);
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
		setupBestAndWorstModels();

		if (generation > config.getMaxGeneration()) {
			storeData(); // save the final state
			running = false;
			done = true;

			return;
		}

		log.info("GA running generation " + generation + "...");

		// sample data
		if (modelSampleCounter == config.getSampleModelInterval()) {
			modelSampleCounter = 0;
			storeData();
		}

		// Update food availability probabilities
		if (foodFlipCounter == config.getFoodFlipInterval()) {
			simulator.flipFoodProbs();
			foodFlipCounter = 0;
		}

		createNextGenPopulation();

		generation++;
		modelSampleCounter++;
		foodFlipCounter++;

		log.debug(getModelStatus());
	}

	private void createNextGenPopulation() {
		offspring = new LinkedList<>();

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
	}

	private void setupBestAndWorstModels() {
		Model best, worst;
		Collections.sort(population);
		best = population.getLast();
		worst = population.getFirst();

		currGRN = (GeneRegulatoryNetwork) best.getGRN().clone();
		currGRN.setName("Generation " + (generation == 1 ? generation : generation - 1));
		currBest = new Model(best);
		currWorst = new Model(worst);
	}

	@Override
	public void run() {
		log.info("Starting run");
		preEvolve(); // neutral evolution for config.neutralGen generations
		start();

		while (running) {
			simulatePopulation();
			nextGen();

			running = !done;
		}

		config.setStatus(SimulationStatus.DONE);
		simulationRepo.save(config);

		log.debug("GA done");
		log.info("Finished run");
	}

	private void simulatePopulation() {
		for (Model m : population) {
			simulator.setCurrModel(m);
			simulator.start();
		}
	}

	private String getModelStatus() {
		modelStatus = "\nBest Model\n" + "----------------------------\n" + "Fitness: " + currBest.getFitness() + "\n"
				+ "Energy: "
				+ currBest.getEnergy() + "\n" + "Stress1: " + currBest.getStress1() + "\nStress2: "
				+ currBest.getStress2() + "\n" + "Biomass: "
				+ currBest.getBiomass() + "\n" + "Ancestral index: " + currBest.getAncestralIndex() + "\n"
				+ "# binding sites: "
				+ currBest.getNumSites() + "\n" + "# genes: " + currBest.getNumGenes() + "\n\n" +

				"Worst Model\n" + "----------------------------\n" + "Fitness: " + currWorst.getFitness() + "\n"
				+ "Energy: " + currWorst.getEnergy()
				+ "\n" + "Stress1: " + currWorst.getStress1() + "\nStress2: " + currWorst.getStress2() + "\n"
				+ "Biomass: " + currWorst.getBiomass()
				+ "\n" + "Ancestral index: " + currWorst.getAncestralIndex() + "\n" + "# binding sites: "
				+ currWorst.getNumSites() + "\n"
				+ "# genes: " + currWorst.getNumGenes() + "\n\n";

		return modelStatus;
	}

	private void cancel() {
		log.debug("Cancelling simulation " + config.getId());
		running = false;
		done = true;
		config.setStatus(SimulationStatus.CANCELLED);
		simulationRepo.save(config);

	}

}
