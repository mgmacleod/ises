package jas.model.molecular.behav;

import jas.model.cellular.Model;
import jas.model.molecular.Gene;

public class Fod4Behaviour extends FodBehaviour {

	public Fod4Behaviour() {
		// TODO Auto-generated constructor stub
	}

	public Fod4Behaviour(Model m, Gene g) {
		super(m, g);
		// TODO Auto-generated constructor stub
	}
	
	public void translate() {
		super.translate();
		model.doFod4();
	}

}
