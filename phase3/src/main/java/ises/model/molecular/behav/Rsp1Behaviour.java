package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;
import ises.rest.entities.SimulationConfiguration;

public class Rsp1Behaviour extends OutputBehaviour {

	public Rsp1Behaviour() {
		super();
	}

	public Rsp1Behaviour(Model m, Gene g, SimulationConfiguration config) {
		super(m, g, config);
	}

	public void translate() {
		super.translate();
		model.doRsp1();
	}

}
