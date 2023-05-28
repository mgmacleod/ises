package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;
import ises.rest.entities.SimulationConfiguration;

public class OutputBehaviour extends GeneBehaviour {

	public OutputBehaviour() {
		super();
	}

	public OutputBehaviour(Model m, Gene g, SimulationConfiguration config) {
		super(m, g, config);
	}

	public void translate() {
		super.translate();
	}

}
