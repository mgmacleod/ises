package jas.model.molecular.behav;

import jas.model.cellular.Model;
import jas.model.molecular.Gene;

public class Fod9Behaviour extends FodBehaviour {

	public Fod9Behaviour() {
		// TODO Auto-generated constructor stub
	}

	public Fod9Behaviour(Model m, Gene g) {
		super(m, g);
		// TODO Auto-generated constructor stub
	}

	public void translate() {
		super.translate();
		model.doFod9();
	}

}
