package jas.model.molecular.behav;

import jas.model.cellular.Model;
import jas.model.molecular.Gene;

public class Syn2Behaviour extends OutputBehaviour {

	public Syn2Behaviour() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Syn2Behaviour(Model m, Gene g) {
		super(m, g);
		// TODO Auto-generated constructor stub
	}

	public void translate() {
		super.translate();
		model.doSyn2();
	}
}
