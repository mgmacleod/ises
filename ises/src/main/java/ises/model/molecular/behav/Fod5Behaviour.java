package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;

public class Fod5Behaviour extends FodBehaviour {

	public Fod5Behaviour() {
		// TODO Auto-generated constructor stub
	}

	public Fod5Behaviour(Model m, Gene g) {
		super(m, g);
		// TODO Auto-generated constructor stub
	}

	public void translate() {
		super.translate();
		model.doFod5();
	}

}
