package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;

public class Fod7Behaviour extends FodBehaviour {

	public Fod7Behaviour() {
		// TODO Auto-generated constructor stub
	}

	public Fod7Behaviour(Model m, Gene g) {
		super(m, g);
		// TODO Auto-generated constructor stub
	}

	public void translate() {
		super.translate();
		model.doFod7();
	}
}
