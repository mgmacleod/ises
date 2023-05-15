package jas.model.molecular.behav;

import jas.model.cellular.Model;
import jas.model.molecular.Gene;

public class Syn4Behaviour extends OutputBehaviour {

	public Syn4Behaviour() {
		super();
	}

	public Syn4Behaviour(Model m, Gene g) {
		super(m, g);
	}

	public void translate() {
		super.translate();
		model.doSyn4();
	}

}
