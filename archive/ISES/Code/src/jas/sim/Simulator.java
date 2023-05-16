package jas.sim;

import jas.Thing;
import jas.model.cellular.Model;
import jas.sys.ISES;
import jas.sys.Params;

public class Simulator extends Thing {
	protected ISES 				ises;
	protected Model				currModel;
	protected int				currTime, stressCounter, fc1, fc2, fc3;
	protected boolean			running;
	
	public Simulator() {
	}
	
	public Simulator(ISES i) {
		ises = i;
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
			print("null Model in the simulator!! Abandon ship!!");
			System.exit(1);
		}
		
		currTime = 1;
		stressCounter = 0;
		fc1 = 0;
		fc2 = 0;
		fc3 = 0;
		
		while(currTime <= Params.maxTime && currModel.isAlive()) {
			calcStressLevels();
			calcFoodAvail();
			
			currModel.step();
			
			currTime++;
			stressCounter++;
		}
		
		// calculate fitness
		int f = currTime;
		if (currModel.isAlive())  {
			f += currModel.getBiomass()-1;
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
		if (rand < Params.kFood1) {
			currModel.food1Available();
		}
		
		if (Math.random() < Params.kFood2) {
			currModel.food2Available();
		}
		
		if (Math.random() < Params.kFood3) {
			currModel.food3Available();
		}
		
		if (Math.random() < Params.kFood4) {
			currModel.food4Available();
		}
		
		if (Math.random() < Params.kFood5) {
			currModel.food5Available();
		}
		
		if (Math.random() < Params.kFood6) {
			currModel.food6Available();
		}
		
		if (Math.random() < Params.kFood7) {
			currModel.food7Available();
		}
		
		if (Math.random() < Params.kFood8) {
			currModel.food8Available();
		}
		
		if (Math.random() < Params.kFood9) {
			currModel.food9Available();
		}
		
		
		
		
		
	}
	
	public void calcStressLevels() {
		if (stressCounter >= Params.iStressIn) {
			currModel.addStress();
			stressCounter = 0;
		}
	}

	/**
	 * @return the ises
	 */
	public ISES getIses() {
		return ises;
	}

	/**
	 * @param ises the ises to set
	 */
	public void setIses(ISES ises) {
		this.ises = ises;
	}

	/**
	 * @return the currModel
	 */
	public Model getCurrModel() {
		return currModel;
	}

	/**
	 * @param currModel the currModel to set
	 */
	public void setCurrModel(Model currModel) {
		this.currModel = currModel;
		this.currModel.initialize();
	}

	/**
	 * @return the currTime
	 */
	public int getCurrTime() {
		return currTime;
	}

	/**
	 * @param currTime the currTime to set
	 */
	public void setCurrTime(int currTime) {
		this.currTime = currTime;
	}

	/**
	 * @return the stressCounter
	 */
	public int getStressCounter() {
		return stressCounter;
	}

	/**
	 * @param stressCounter the stressCounter to set
	 */
	public void setStressCounter(int stressCounter) {
		this.stressCounter = stressCounter;
	}

	

}
