package jas.model.molecular.behav;

import jas.model.cellular.Model;
import jas.model.molecular.Gene;

public class Rsp2Behaviour extends OutputBehaviour {

	public Rsp2Behaviour() {
		// TODO Auto-generated constructor stub
	}

	public Rsp2Behaviour(Model m, Gene g) {
		super(m, g);
		// TODO Auto-generated constructor stub
	}
	
	public void translate() {
		super.translate();
		model.doRsp2();
	}

}
