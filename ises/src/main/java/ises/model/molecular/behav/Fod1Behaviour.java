package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;

public class Fod1Behaviour extends FodBehaviour {

	public Fod1Behaviour(Model m, Gene g) {
		super(m, g);

	}

	public void translate() {
		super.translate();
		model.doFod1();

	}

}
