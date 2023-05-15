package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;

public class Syn1Behaviour extends OutputBehaviour {

	public Syn1Behaviour() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Syn1Behaviour(Model m, Gene g) {
		super(m, g);
		// TODO Auto-generated constructor stub
	}

	public void translate() {
		super.translate();
		model.doSyn1();

	}

}
