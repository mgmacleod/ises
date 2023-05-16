package ises.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ises.Thing;
import ises.model.cellular.Model;
import ises.rest.entities.SimulationConfiguration;

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

	public void go() {
		running = true;
		simulate();
	}

	public void pause() {
		running = false;
	}

	public void resume() {
		running = true;
		simulate();
	}

	public void simulate() {
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

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	private void calcFoodAvail() {
		double rand = random();
		if (rand < config.getkFood1()) {
			currModel.food1Available();
		}

		if (Math.random() < config.getkFood2()) {
			currModel.food2Available();
		}

		if (Math.random() < config.getkFood3()) {
			currModel.food3Available();
		}

		if (Math.random() < config.getkFood4()) {
			currModel.food4Available();
		}

		if (Math.random() < config.getkFood5()) {
			currModel.food5Available();
		}

		if (Math.random() < config.getkFood6()) {
			currModel.food6Available();
		}

		if (Math.random() < config.getkFood7()) {
			currModel.food7Available();
		}

		if (Math.random() < config.getkFood8()) {
			currModel.food8Available();
		}

		if (Math.random() < config.getkFood9()) {
			currModel.food9Available();
		}

	}

	public void calcStressLevels() {
		if (stressCounter >= config.getiStressIn()) {
			currModel.addStress();
			stressCounter = 0;
		}
	}

	public Model getCurrModel() {
		return currModel;
	}

	public void setCurrModel(Model currModel) {
		this.currModel = currModel;
		this.currModel.initialize();
	}

	public int getCurrTime() {
		return currTime;
	}

	public void setCurrTime(int currTime) {
		this.currTime = currTime;
	}

	public int getStressCounter() {
		return stressCounter;
	}

	public void setStressCounter(int stressCounter) {
		this.stressCounter = stressCounter;
	}

	public void setConfig(SimulationConfiguration config) {
		this.config = config;
	}

}
