package ises.sys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import ises.Thing;
import ises.model.cellular.Model;
import ises.model.network.GRN;

public class GA extends Thing {

	protected LinkedList<Model> pop, offspring;
	protected int gen, modelCount, grnCount, foodCount;
	protected ISES ises;
	protected boolean running, done, collectingData;
	protected Model best, worst;

	public GA(ISES is, boolean collData) {
		ises = is;
		pop = new LinkedList<Model>();
		offspring = new LinkedList<Model>();
		gen = 0;
		foodCount = Params.iFoodFlip;
		modelCount = Params.iSampleModel;
		grnCount = Params.iSampleGRN;
		running = false;
		done = false;
		collectingData = collData;

		for (int i = 0; i < Params.popSize; i++) {
			pop.add(new Model(i));
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
				Params.kFood1 = Params.kFoodBase * Params.kFoodFactor;
			else if (i.intValue() == 2)
				Params.kFood2 = Params.kFoodBase * Params.kFoodFactor;
			else if (i.intValue() == 3)
				Params.kFood3 = Params.kFoodBase * Params.kFoodFactor;
			else if (i.intValue() == 4)
				Params.kFood4 = Params.kFoodBase * Params.kFoodFactor;
			else if (i.intValue() == 5)
				Params.kFood5 = Params.kFoodBase * Params.kFoodFactor;
			else if (i.intValue() == 6)
				Params.kFood6 = Params.kFoodBase * Params.kFoodFactor;
			else if (i.intValue() == 7)
				Params.kFood7 = Params.kFoodBase * Params.kFoodFactor;
			else if (i.intValue() == 8)
				Params.kFood8 = Params.kFoodBase * Params.kFoodFactor;
			else if (i.intValue() == 9)
				Params.kFood9 = Params.kFoodBase * Params.kFoodFactor;
		}

		for (Integer i : foods) {
			if (i.intValue() == 1)
				Params.kFood1 = Params.kFoodBase / Params.kFoodFactor;
			else if (i.intValue() == 2)
				Params.kFood2 = Params.kFoodBase / Params.kFoodFactor;
			else if (i.intValue() == 3)
				Params.kFood3 = Params.kFoodBase / Params.kFoodFactor;
			else if (i.intValue() == 4)
				Params.kFood4 = Params.kFoodBase / Params.kFoodFactor;
			else if (i.intValue() == 5)
				Params.kFood5 = Params.kFoodBase / Params.kFoodFactor;
			else if (i.intValue() == 6)
				Params.kFood6 = Params.kFoodBase / Params.kFoodFactor;
			else if (i.intValue() == 7)
				Params.kFood7 = Params.kFoodBase / Params.kFoodFactor;
			else if (i.intValue() == 8)
				Params.kFood8 = Params.kFoodBase / Params.kFoodFactor;
			else if (i.intValue() == 9)
				Params.kFood9 = Params.kFoodBase / Params.kFoodFactor;
		}

	}

	public void setCollectingData(boolean cd) {
		collectingData = cd;
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
		for (int i = 0; i < Params.neutralGen; i++) {
			for (Model m : pop)
				m.preEvolve();
		}

	}

	public boolean isDone() {
		return done;
	}

	public void nextGen() {

		if (gen > Params.maxGen) {
			running = false;
			done = true;
			ises.setDone(true);

			return;
		}

		if (foodCount == Params.iFoodFlip) {
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

		if (collectingData) {
			if (modelCount == Params.iSampleModel) {
				modelCount = 0;
				ises.storeCurrBest();
			}

			if (grnCount == Params.iSampleGRN) {
				grnCount = 0;
				ises.storeCurrGRN();
			}
		}

		int n = Params.popSize / 2;

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

}
