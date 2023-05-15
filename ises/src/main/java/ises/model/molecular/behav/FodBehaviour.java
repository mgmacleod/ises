package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;

public class FodBehaviour extends InputBehaviour {

	public FodBehaviour() {
	}

	public FodBehaviour(Model m, Gene g) {
		super(m, g);
	}

	public void translate() {
		super.translate();
	}

	public void addEnergy(int e) {
		model.addEnergy(e);
	}

}
