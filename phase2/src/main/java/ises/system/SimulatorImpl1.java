package ises.system;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ises.Thing;
import ises.model.cellular.Model;
import ises.rest.entities.SimulationConfiguration;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Provides a basic simulation engine to evaluate the behaviour of
 * {@link Model}s
 */
@Slf4j
@NoArgsConstructor
@Service
@Scope("prototype")
public class SimulatorImpl1 extends Thing implements Simulator {

	private Model currModel;
	private int currTime, stress1Counter, stress2Counter;
	private boolean running;
	private SimulationConfiguration config;

	@Override
	public void initializeForRun(SimulationConfiguration config) {
		this.config = config;
		running = false;
	}

	@Override
	public void start() {
		running = true;
		simulate();
	}

	@Override
	public void flipFoodProbs() {
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

	private void simulate() {
		if (currModel == null) {
			log.debug("null Model in the simulator!! Abandon ship!!");
			throw new IllegalArgumentException("Attempted to simulate a null model");
		}

		currTime = 1;
		stress1Counter = stress2Counter = 0;

		while (currTime <= config.getMaxTime() && currModel.isAlive()) {
			calcStressLevels();
			calcFoodAvail();

			currModel.step();

			currTime++;
			stress1Counter++;
			stress2Counter++;
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
		if (stress1Counter >= config.getStress1InInterval()) {
			currModel.addStress1();
			stress1Counter = 0;
		}

		if (stress2Counter >= config.getStress2InInterval()) {
			currModel.addStress2();
			stress2Counter = 0;
		}
	}

	@Override
	public void setCurrModel(Model currModel) {
		this.currModel = currModel;
		this.currModel.initialize();
	}

	@Override
	public boolean isRunning() {
		return running;
	}

}
