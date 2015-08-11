package jas.model.molecular.behav;

import jas.model.cellular.Model;
import jas.model.molecular.Gene;

public class FodBehaviour extends InputBehaviour {

	public FodBehaviour() {
	}

	public FodBehaviour(Model m, Gene g) {
		super(m, g);
	}
	
	public void translate() {
		super.translate();
	}
	
	public void addEnergy(int e) {
		model.addEnergy(e);
	}

}
