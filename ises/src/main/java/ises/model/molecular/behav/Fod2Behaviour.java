package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;

public class Fod2Behaviour extends FodBehaviour {

	public Fod2Behaviour() {

	}

	public Fod2Behaviour(Model m, Gene g) {
		super(m, g);
	}

	public void translate() {
		super.translate();
		model.doFod2();
	}

}
