package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;

public class Syn3Behaviour extends OutputBehaviour {

	public Syn3Behaviour() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Syn3Behaviour(Model m, Gene g) {
		super(m, g);
		// TODO Auto-generated constructor stub
	}

	public void translate() {
		super.translate();
		model.doSyn3();
	}

}
