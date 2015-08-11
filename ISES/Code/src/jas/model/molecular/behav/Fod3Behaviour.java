package jas.model.molecular.behav;

import jas.model.cellular.Model;
import jas.model.molecular.Gene;


public class Fod3Behaviour extends FodBehaviour {

	public Fod3Behaviour() {}

	public Fod3Behaviour(Model m, Gene g) {
		super(m, g);
	}
	
	public void translate() {
		super.translate();
		model.doFod3();
	}

}
