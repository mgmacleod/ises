package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;
import ises.rest.entities.SimulationConfiguration;

public class Fod6Behaviour extends FodBehaviour {

	public Fod6Behaviour() {
	}

	public Fod6Behaviour(Model m, Gene g, SimulationConfiguration config) {
		super(m, g, config);
	}

	public void translate() {
		super.translate();
		model.doFod6();
	}

}
