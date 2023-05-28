package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;
import ises.rest.entities.SimulationConfiguration;

public class Fod5Behaviour extends FodBehaviour {

	public Fod5Behaviour() {
	}

	public Fod5Behaviour(Model m, Gene g, SimulationConfiguration config) {
		super(m, g, config);
	}

	public void translate() {
		super.translate();
		model.doFod5();
	}

}
