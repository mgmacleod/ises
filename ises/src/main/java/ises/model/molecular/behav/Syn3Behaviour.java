package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;
import ises.rest.entities.SimulationConfiguration;

public class Syn3Behaviour extends OutputBehaviour {

	public Syn3Behaviour() {
		super();
	}

	public Syn3Behaviour(Model m, Gene g, SimulationConfiguration config) {
		super(m, g, config);
	}

	public void translate() {
		super.translate();
		model.doSyn3();
	}

}
