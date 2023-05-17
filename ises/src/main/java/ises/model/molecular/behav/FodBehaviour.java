package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;
import ises.rest.entities.SimulationConfiguration;

public class FodBehaviour extends InputBehaviour {

	public FodBehaviour() {
	}

	public FodBehaviour(Model m, Gene g, SimulationConfiguration config) {
		super(m, g, config);
	}

	@Override
	public void translate() {
		super.translate();
	}

}
