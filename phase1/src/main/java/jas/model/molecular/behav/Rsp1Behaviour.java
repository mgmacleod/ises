package jas.model.molecular.behav;

import jas.model.cellular.Model;
import jas.model.molecular.Gene;

public class Rsp1Behaviour extends OutputBehaviour {

	public Rsp1Behaviour() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Rsp1Behaviour(Model m, Gene g) {
		super(m, g);
		// TODO Auto-generated constructor stub
	}
	
	public void translate() {
		super.translate();
		model.doRsp1();
	}

}
