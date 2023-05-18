package ises.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ises.Thing;
import ises.model.cellular.Model;
import ises.rest.entities.SimulationConfiguration;

/**
 * Provides a basic simulation engine to evaluate the behaviour of {@link Model}s
 */
@Service
@Scope("prototype")
public class Simulator extends Thing {
	private static final Logger logger = LoggerFactory.getLogger(Simulator.class);

	private Model currModel;
	private int currTime, stressCounter;
	private boolean running;
	private SimulationConfiguration config;

	public Simulator() {
	}

	public void initializeForRun(SimulationConfiguration config) {
		this.config = config;
		running = false;
	}

	public void start() {
		running = true;
		simulate();
	}

	private void simulate() {
		if (currModel == null) {
			logger.debug("null Model in the simulator!! Abandon ship!!");
			throw new IllegalArgumentException("Attempted to simulate a null model");
		}

		currTime = 1;
		stressCounter = 0;

		while (currTime <= config.getMaxTime() && currModel.isAlive()) {
			calcStressLevels();
			calcFoodAvail();

			currModel.step();

			currTime++;
			stressCounter++;
		}

		// calculate fitness
		int f = currTime;
		if (currModel.isAlive()) {
			f += currModel.getBiomass() - 1;
		}

		currModel.setFitness(f);

		currModel.calcMeans(currTime);
	}

	private void calcFoodAvail() {
		double rand = random();
		if (rand < config.getFood1Rate()) {
			currModel.food1Available();
		}

		if (Math.random() < config.getFood2Rate()) {
			currModel.food2Available();
		}

		if (Math.random() < config.getFood3Rate()) {
			currModel.food3Available();
		}

		if (Math.random() < config.getFood4Rate()) {
			currModel.food4Available();
		}

		if (Math.random() < config.getFood5Rate()) {
			currModel.food5Available();
		}

		if (Math.random() < config.getFood6Rate()) {
			currModel.food6Available();
		}

		if (Math.random() < config.getFood7Rate()) {
			currModel.food7Available();
		}

		if (Math.random() < config.getFood8Rate()) {
			currModel.food8Available();
		}

		if (Math.random() < config.getFood9Rate()) {
			currModel.food9Available();
		}

	}

	private void calcStressLevels() {
		if (stressCounter >= config.getStressInInterval()) {
			currModel.addStress();
			stressCounter = 0;
		}
	}

	public void setCurrModel(Model currModel) {
		this.currModel = currModel;
		this.currModel.initialize();
	}

	public boolean isRunning() {
		return running;
	}

}
