package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;
import ises.rest.entities.SimulationConfiguration;

public class Syn1Behaviour extends OutputBehaviour {

	public Syn1Behaviour() {
		super();
	}

	public Syn1Behaviour(Model m, Gene g, SimulationConfiguration config) {
		super(m, g, config);
	}

	public void translate() {
		super.translate();
		model.doSyn1();

	}

}
