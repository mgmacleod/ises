package ises.sys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import ises.Thing;
import ises.model.cellular.Model;
import ises.model.network.GRN;
import ises.rest.entities.SimulationConfiguration;

public class GA extends Thing {

	protected LinkedList<Model> pop, offspring;
	protected int gen, modelCount, grnCount, foodCount;
	protected ISES ises;
	protected boolean running, done;
	protected Model best, worst;
	private SimulationConfiguration config;

	public GA(ISES is, SimulationConfiguration config) {
		this.config = config;
		ises = is;
		pop = new LinkedList<Model>();
		offspring = new LinkedList<Model>();
		gen = 0;
		foodCount = config.getiFoodFlip();
		modelCount = config.getiSampleModel();
		grnCount = config.getiSampleGRN();
		running = false;
		done = false;

		for (int i = 0; i < config.getPopSize(); i++) {
			pop.add(new Model(i, config));
		}
	}

	public void go() {
		running = true;

		while (running)
			nextGen();
	}

	public void pause() {
		running = false;
	}

	public void resume() {
		go();
	}

	public void flipFoodProbs() {
		ArrayList<Integer> foods = new ArrayList<Integer>();
		ArrayList<Integer> multFoods = new ArrayList<Integer>();

		for (int i = 1; i < 10; i++)
			foods.add(Integer.valueOf(i));

		Collections.shuffle(foods);

		for (int i = 0; i < 4; i++)
			multFoods.add(foods.remove(i));

		for (Integer i : multFoods) {
			if (i.intValue() == 1)
				config.setkFood1(config.getkFoodBase() * config.getkFoodFactor());
			else if (i.intValue() == 2)
				config.setkFood2(config.getkFoodBase() * config.getkFoodFactor());
			else if (i.intValue() == 3)
				config.setkFood3(config.getkFoodBase() * config.getkFoodFactor());
			else if (i.intValue() == 4)
				config.setkFood4(config.getkFoodBase() * config.getkFoodFactor());
			else if (i.intValue() == 5)
				config.setkFood5(config.getkFoodBase() * config.getkFoodFactor());
			else if (i.intValue() == 6)
				config.setkFood6(config.getkFoodBase() * config.getkFoodFactor());
			else if (i.intValue() == 7)
				config.setkFood7(config.getkFoodBase() * config.getkFoodFactor());
			else if (i.intValue() == 8)
				config.setkFood8(config.getkFoodBase() * config.getkFoodFactor());
			else if (i.intValue() == 9)
				config.setkFood9(config.getkFoodBase() * config.getkFoodFactor());
		}

		for (Integer i : foods) {
			if (i.intValue() == 1)
				config.setkFood1(config.getkFoodBase() / config.getkFoodFactor());
			else if (i.intValue() == 2)
				config.setkFood2(config.getkFoodBase() / config.getkFoodFactor());
			else if (i.intValue() == 3)
				config.setkFood3(config.getkFoodBase() / config.getkFoodFactor());
			else if (i.intValue() == 4)
				config.setkFood4(config.getkFoodBase() / config.getkFoodFactor());
			else if (i.intValue() == 5)
				config.setkFood5(config.getkFoodBase() / config.getkFoodFactor());
			else if (i.intValue() == 6)
				config.setkFood6(config.getkFoodBase() / config.getkFoodFactor());
			else if (i.intValue() == 7)
				config.setkFood7(config.getkFoodBase() / config.getkFoodFactor());
			else if (i.intValue() == 8)
				config.setkFood8(config.getkFoodBase() / config.getkFoodFactor());
			else if (i.intValue() == 9)
				config.setkFood9(config.getkFoodBase() / config.getkFoodFactor());
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

	public Model getBest() {
		return best;
	}

	public Model getWorst() {
		return worst;
	}

	public void preEvolve() {
		for (int i = 0; i < config.getNeutralGen(); i++) {
			for (Model m : pop)
				m.preEvolve();
		}

	}

	public boolean isDone() {
		return done;
	}

	public void nextGen() {

		if (gen > config.getMaxGen()) {
			running = false;
			done = true;
			ises.setDone(true);

			return;
		}

		if (foodCount == config.getiFoodFlip()) {
			flipFoodProbs();
			foodCount = 0;
		}

		offspring = new LinkedList<Model>();
		Collections.sort(pop);

		best = pop.getLast();
		worst = pop.getFirst();

		ises.setCurrGRN(new GRN(best.getGRN()));
		ises.setCurrBest(new Model(best));
		ises.setCurrWorst(new Model(worst));

		// sample data
		if (modelCount == config.getiSampleModel()) {
			modelCount = 0;
			ises.storeCurrBest();
		}

		if (grnCount == config.getiSampleGRN()) {
			grnCount = 0;
			ises.storeCurrGRN();
		}

		int n = config.getPopSize() / 2;

		while (pop.size() > n)
			pop.removeFirst();

		for (Model m : pop)
			offspring.add(m.replicate());

		pop.addAll(offspring);

		for (Model m : pop) {
			m.mutate();
		}
		gen++;
		modelCount++;
		grnCount++;
		foodCount++;

	}

	public void setConfig(SimulationConfiguration config) {
		this.config = config;
	}

}
