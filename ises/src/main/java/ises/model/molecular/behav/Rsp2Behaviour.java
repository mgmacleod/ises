package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;
import ises.rest.entities.SimulationConfiguration;

public class Rsp2Behaviour extends OutputBehaviour {

	public Rsp2Behaviour() {
	}

	public Rsp2Behaviour(Model m, Gene g, SimulationConfiguration config) {
		super(m, g, config);
	}

	public void translate() {
		super.translate();
		model.doRsp2();
	}

}
