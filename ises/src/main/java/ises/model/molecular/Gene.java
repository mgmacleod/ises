package ises.model.molecular;

import ises.model.cellular.Genome;
import ises.model.cellular.Model;
import ises.model.molecular.behav.Fod1Behaviour;
import ises.model.molecular.behav.Fod2Behaviour;
import ises.model.molecular.behav.Fod3Behaviour;
import ises.model.molecular.behav.Fod4Behaviour;
import ises.model.molecular.behav.Fod5Behaviour;
import ises.model.molecular.behav.Fod6Behaviour;
import ises.model.molecular.behav.Fod7Behaviour;
import ises.model.molecular.behav.Fod8Behaviour;
import ises.model.molecular.behav.Fod9Behaviour;
import ises.model.molecular.behav.GeneBehaviour;
import ises.model.molecular.behav.Rsp1Behaviour;
import ises.model.molecular.behav.Rsp2Behaviour;
import ises.model.molecular.behav.Syn1Behaviour;
import ises.model.molecular.behav.Syn2Behaviour;
import ises.model.molecular.behav.Syn3Behaviour;
import ises.model.molecular.behav.Syn4Behaviour;
import ises.model.network.GrnVertex;
import ises.rest.entities.SimulationConfiguration;

public class Gene extends ModelComponent {

	private Genome genome;
	private RegulatoryRegion regRegion;
	private ProteinSpecies proteinSpecies;
	private GeneBehaviour behaviour;
	private int regState;
	private GrnVertex vertex;
	private String ancestralName;
	private Model model;
	private SimulationConfiguration config;

	private Gene(SimulationConfiguration config) {
		this.config = config;
	}

	public Gene(Gene parent, SimulationConfiguration config) {
		this(config);
		model = parent.model;
		genome = parent.genome;
		name = "randomName";
		ancestralName = parent.name;

		if (parent.regRegion != null) {
			regRegion = new RegulatoryRegion(parent.regRegion, this, config);
		} else {
			regRegion = new RegulatoryRegion(this, config);
		}

		proteinSpecies = new ProteinSpecies(parent.proteinSpecies, this, config);
		initBehaviour();
	}

	public Gene(Gene parent, Model m, SimulationConfiguration config) {
		this(config);
		model = m;
		genome = model.getGenome();
		regState = 0;
		name = new String(parent.getName());
		ancestralName = name;

		if (parent.regRegion != null) {
			regRegion = new RegulatoryRegion(parent.regRegion, this, config);
		} else {
			regRegion = null;
		}

		proteinSpecies = new ProteinSpecies(parent.proteinSpecies, this, config);
		initBehaviour();

	}

	public Gene(Model m, String tag, boolean regulated, SimulationConfiguration config) {
		this(config);
		model = m;
		genome = m.getGenome();
		name = tag;
		ancestralName = "" + tag;
		initBehaviour();

		proteinSpecies = new ProteinSpecies(this, config);

		if (regulated) {
			regRegion = new RegulatoryRegion(this, config);
		} else {
			regRegion = null;
		}

	}

	/**
	 * Sets the genes regState to 1
	 */
	public void activate() {
		regState = 1;
	}

	public void addSite(BindingSite bs) {
		if (regRegion != null) {
			regRegion.addSite(bs);

		}
	}

	public void removeSite(BindingSite bs) {
		if (regRegion != null) {
			regRegion.removeSite(bs);
		}
	}

	public String getAncestralName() {
		return ancestralName;
	}

	public Model getModel() {
		return model;
	}

	/**
	 * calculates the regulation state of the gene; calls calcRegState() on
	 * regRegion; smells like a donkey and looks like one too; eats shoots and
	 * leaves.
	 * 
	 */
	public void calcRegState() {
		regState = regRegion.calcRegState();
	}

	@Override
	public boolean equals(Object o) {
		return this == o;
	}

	/**
	 * @return the behaviour
	 */
	public GeneBehaviour getBehaviour() {
		return behaviour;
	}

	/**
	 * @return the proteinSpecies
	 */
	public ProteinSpecies getProteinSpecies() {
		return proteinSpecies;
	}

	/**
	 * @return the regRegion
	 */
	public RegulatoryRegion getRegRegion() {
		return regRegion;
	}

	/**
	 * @return the regState
	 */
	public int getRegState() {
		return regState;
	}

	public GrnVertex getVertex() {
		return vertex;
	}

	public void setVertex(GrnVertex vertex) {
		this.vertex = vertex;
	}

	public void initBehaviour() {
		// sort out behaviour. ugly!
		if (name.equals("fod1")) {
			behaviour = new Fod1Behaviour(model, this, config);
		} else if (name.equals("fod2")) {
			behaviour = new Fod2Behaviour(model, this, config);
		} else if (name.equals("fod3")) {
			behaviour = new Fod3Behaviour(model, this, config);
		} else if (name.equals("fod4")) {
			behaviour = new Fod4Behaviour(model, this, config);
		} else if (name.equals("fod5")) {
			behaviour = new Fod5Behaviour(model, this, config);
		} else if (name.equals("fod6")) {
			behaviour = new Fod6Behaviour(model, this, config);
		} else if (name.equals("fod7")) {
			behaviour = new Fod7Behaviour(model, this, config);
		} else if (name.equals("fod8")) {
			behaviour = new Fod8Behaviour(model, this, config);
		} else if (name.equals("fod9")) {
			behaviour = new Fod9Behaviour(model, this, config);
		} else if (name.equals("syn1")) {
			behaviour = new Syn1Behaviour(model, this, config);
		} else if (name.equals("syn2")) {
			behaviour = new Syn2Behaviour(model, this, config);
		} else if (name.equals("syn3")) {
			behaviour = new Syn3Behaviour(model, this, config);
		} else if (name.equals("syn4")) {
			behaviour = new Syn4Behaviour(model, this, config);
		} else if (name.equals("rsp1")) {
			behaviour = new Rsp1Behaviour(model, this, config);
		} else if (name.equals("rsp2")) {
			behaviour = new Rsp2Behaviour(model, this, config);
		} else {
			behaviour = new GeneBehaviour(model, this, config);
		}
	}

	/**
	 * Checks to see if the gene is active. Returns true if regState > 0.
	 * 
	 * @return A boolean representing the activity of the gene
	 * 
	 */
	public boolean isActive() {
		return regState > 0;
	}

	@Override
	public void label(String s) {
		name = s;
		proteinSpecies.label(s);
	}

	public void labelRegRegion() {
		if (regRegion != null) {
			regRegion.labelSites();
		}
	}

	public void printSites() {
		if (regRegion == null) {
			return;
		}

		for (BindingSite bs : regRegion.getSites()) {
			print(bs);
		}
	}

	@Override
	public void mutate() {
		proteinSpecies.mutate();
	}

	public Genome getGenome() {
		return genome;
	}

	/**
	 * @param behaviour the behaviour to set
	 */
	public void setBehaviour(GeneBehaviour behaviour) {
		this.behaviour = behaviour;
	}

	/**
	 * @param proteinSpecies the proteinSpecies to set
	 */
	public void setProteinSpecies(ProteinSpecies proteinSpecies) {
		this.proteinSpecies = proteinSpecies;
	}

	/**
	 * @param regRegion the regRegion to set
	 */
	public void setRegRegion(RegulatoryRegion regRegion) {
		this.regRegion = regRegion;
	}

	/**
	 * @param regState the regState to set
	 */
	public void setRegState(int regState) {
		this.regState = regState;
	}

	/**
	 * The basic method for protein translation. calls the translate() method of
	 * this Gene's Behaviour
	 */
	public void translate() {
		behaviour.translate();
	}

	/**
	 * Resets the Gene. regState set to 0; calls reset() on the regulatory region
	 * 
	 */
	public void unbindAndDeactivate() {
		if (regRegion != null) {
			regRegion.unbindAndDeactivate();
		}
		regState = 0;
	}

}
