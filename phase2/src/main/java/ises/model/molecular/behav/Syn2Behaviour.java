package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;
import ises.rest.entities.SimulationConfiguration;

public class Syn2Behaviour extends OutputBehaviour {

	public Syn2Behaviour() {
		super();
	}

	public Syn2Behaviour(Model m, Gene g, SimulationConfiguration config) {
		super(m, g, config);
	}

	public void translate() {
		super.translate();
		model.doSyn2();
	}
}
