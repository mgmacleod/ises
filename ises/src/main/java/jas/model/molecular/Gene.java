package jas.model.molecular;

import jas.model.cellular.Genome;
import jas.model.cellular.Model;
import jas.model.molecular.behav.Fod1Behaviour;
import jas.model.molecular.behav.Fod2Behaviour;
import jas.model.molecular.behav.Fod3Behaviour;
import jas.model.molecular.behav.Fod4Behaviour;
import jas.model.molecular.behav.Fod5Behaviour;
import jas.model.molecular.behav.Fod6Behaviour;
import jas.model.molecular.behav.Fod7Behaviour;
import jas.model.molecular.behav.Fod8Behaviour;
import jas.model.molecular.behav.Fod9Behaviour;
import jas.model.molecular.behav.GeneBehaviour;
import jas.model.molecular.behav.Rsp1Behaviour;
import jas.model.molecular.behav.Rsp2Behaviour;
import jas.model.molecular.behav.Syn1Behaviour;
import jas.model.molecular.behav.Syn2Behaviour;
import jas.model.molecular.behav.Syn3Behaviour;
import jas.model.molecular.behav.Syn4Behaviour;
import jas.model.network.Node;

public class Gene extends ModelComponent {

	protected Genome genome;
	protected RegulatoryRegion regRegion;
	protected ProteinSpecies proteinSpecies;
	protected GeneBehaviour behaviour;
	protected int regState;
	protected Node node;
	protected String ancestralName;
	protected Model model;

	public Gene() {
	}

	public Gene(Gene parent) {
		this.model = parent.model;
		this.genome = parent.genome;
		this.name = "randomName";
		this.ancestralName = parent.name;

		if (parent.regRegion != null)
			this.regRegion = new RegulatoryRegion(parent.regRegion, this);
		else
			this.regRegion = new RegulatoryRegion(this);

		this.proteinSpecies = new ProteinSpecies(parent.proteinSpecies, this);
		initBehaviour();
	}

	public Gene(Gene parent, Model m) {
		model = m;
		genome = model.getGenome();
		regState = 0;
		this.name = new String(parent.getName());
		this.ancestralName = this.name;

		if (parent.regRegion != null)
			this.regRegion = new RegulatoryRegion(parent.regRegion, this);
		else
			this.regRegion = null;

		this.proteinSpecies = new ProteinSpecies(parent.proteinSpecies, this);
		initBehaviour();

	}

	public Gene(Model m, String tag, boolean regulated) {
		model = m;
		genome = m.getGenome();
		name = tag;
		ancestralName = "" + tag;
		initBehaviour();

		proteinSpecies = new ProteinSpecies(this);

		if (regulated)
			regRegion = new RegulatoryRegion(this);

		else
			regRegion = null;

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
		if (regRegion != null)
			regRegion.removeSite(bs);
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

	public boolean equals(Object o) {
		return this == o;
	}

	/**
	 * @return the behaviour
	 */
	public GeneBehaviour getBehaviour() {
		return behaviour;
	}

	public Node getNode() {
		return node;
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

	public void initBehaviour() {
		// sort out behaviour. ugly!
		if (name.equals("fod1"))
			behaviour = new Fod1Behaviour(model, this);
		else if (name.equals("fod2"))
			behaviour = new Fod2Behaviour(model, this);
		else if (name.equals("fod3"))
			behaviour = new Fod3Behaviour(model, this);
		else if (name.equals("fod4"))
			behaviour = new Fod4Behaviour(model, this);
		else if (name.equals("fod5"))
			behaviour = new Fod5Behaviour(model, this);
		else if (name.equals("fod6"))
			behaviour = new Fod6Behaviour(model, this);
		else if (name.equals("fod7"))
			behaviour = new Fod7Behaviour(model, this);
		else if (name.equals("fod8"))
			behaviour = new Fod8Behaviour(model, this);
		else if (name.equals("fod9"))
			behaviour = new Fod9Behaviour(model, this);

		else if (name.equals("syn1"))
			behaviour = new Syn1Behaviour(model, this);
		else if (name.equals("syn2"))
			behaviour = new Syn2Behaviour(model, this);
		else if (name.equals("syn3"))
			behaviour = new Syn3Behaviour(model, this);
		else if (name.equals("syn4"))
			behaviour = new Syn4Behaviour(model, this);
		else if (name.equals("rsp1"))
			behaviour = new Rsp1Behaviour(model, this);
		else if (name.equals("rsp2"))
			behaviour = new Rsp2Behaviour(model, this);

		else
			behaviour = new GeneBehaviour(model, this);
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

	public void label(String s) {
		name = s;
		proteinSpecies.label(s);
	}

	public void labelRegRegion() {
		if (regRegion != null)
			regRegion.labelSites();
	}

	public void printSites() {
		if (regRegion == null)
			return;

		for (BindingSite bs : regRegion.getSites())
			print(bs);
	}

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

	public void setNode(Node n) {
		node = n;
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
		if (regRegion != null)
			regRegion.unbindAndDeactivate();
		regState = 0;
	}

}
