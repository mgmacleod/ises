package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;

public class Fod3Behaviour extends FodBehaviour {

	public Fod3Behaviour() {
	}

	public Fod3Behaviour(Model m, Gene g) {
		super(m, g);
	}

	public void translate() {
		super.translate();
		model.doFod3();
	}

}
