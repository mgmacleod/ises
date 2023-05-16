package ises.model.cellular;

import java.util.ArrayList;
import java.util.Collections;

import ises.Thing;
import ises.model.molecular.BindingSite;
import ises.model.molecular.ProteinSpecies;
import ises.model.network.Edge;
import ises.model.network.GRN;
import ises.model.network.Node;
import ises.rest.entities.SimulationConfiguration;

public class Model extends Thing implements Comparable<Model> {

	protected Genome genome;
	protected Proteome proteome;
	protected int energy, biomass, stress;
	protected int index, ancestralIndex;
	protected Integer fitness;
	protected GRN grn;
	protected ArrayList<Integer> speciesOrder;
	protected ArrayList<Integer> sitesOrder;
	protected int highestEnergy, lowestEnergy;
	protected int totalEnergy, totalBiomass;
	protected double meanEnergy, meanBiomass;
	private SimulationConfiguration config;

	public Model(int i, SimulationConfiguration config) {
		genome = new Genome(this, config);
		index = ancestralIndex = i;
		this.config = config;

		initialize();
	}

	public Model(Model parent) {
		this.ancestralIndex = parent.ancestralIndex;
		genome = new Genome(parent.genome, this, parent.config);

		this.energy = parent.energy;
		this.stress = parent.stress;
		this.biomass = parent.biomass;
		this.fitness = Integer.valueOf(parent.fitness.intValue());
		this.highestEnergy = parent.highestEnergy;
		this.lowestEnergy = parent.lowestEnergy;
		this.meanBiomass = parent.meanBiomass;
		this.meanEnergy = parent.meanEnergy;
		this.config = parent.config;
	}

	public void addBiomass(int bm) {
		biomass += bm;
	}

	public void addEdgeToGRN(Edge e) {
		grn.addEdge(e);
	}

	public void addEdgeToGRN(ProteinSpecies ps, BindingSite bs) {
		Node regulator = ps.getGene().getNode();
		Node target = bs.getGene().getNode();
		boolean pos = bs.getBias() > 0;
		addEdgeToGRN(new Edge(regulator, target, ps.calcAffinityFor(bs), pos));
	}

	public void addEnergy(int e) {
		energy += e;

	}

	public void addNodeToGRN(Node n) {
		grn.addNode(n);
	}

	public int calcNumSitesFromGenes() {
		return genome.calcNumSitesFromGenes();
	}

	public int getNumProteins() {
		return proteome.getNumProteins();
	}

	public int getNumBoundProteins() {
		return proteome.getNumBoundProteins();
	}

	public void addStress() {
		stress += config.getpStressIn();
	}

	public int getNumShapes() {
		return genome.getNumGenes() + genome.getNumSites();
	}

	public int compareTo(Model m) {

		return this.fitness.compareTo(m.fitness);
	}

	public void doFod1() {
		addEnergy(config.getpEnergy1());
	}

	public void doFod2() {
		addEnergy(config.getpEnergy2());
	}

	public void doFod3() {
		addEnergy(config.getpEnergy3());
	}

	public void doFod4() {
		addEnergy(config.getpEnergy4());
	}

	public void doFod5() {
		addEnergy(config.getpEnergy5());
	}

	public void doFod6() {
		addEnergy(config.getpEnergy6());
	}

	public void doFod7() {
		addEnergy(config.getpEnergy7());
	}

	public void doFod8() {
		addEnergy(config.getpEnergy8());
	}

	public void doFod9() {
		addEnergy(config.getpEnergy9());
	}

	public void doRsp1() {
		if (stress <= 0) {
			return;
		}
		stress -= config.getpStress1();
		removeEnergy(config.getcStress1());
	}

	public void doRsp2() {
		if (stress <= 0) {
			return;
		}

		stress -= config.getpStress2();
		removeEnergy(config.getcStress2());
	}

	public void doSyn1() {
		addBiomass(config.getpBio1());
		removeEnergy(config.getcBio1());
	}

	public void doSyn2() {
		addBiomass(config.getpBio2());
		removeEnergy(config.getcBio2());
	}

	public void doSyn3() {
		addBiomass(config.getpBio3());
		removeEnergy(config.getcBio3());
	}

	public void doSyn4() {
		addBiomass(config.getpBio4());
		removeEnergy(config.getcBio4());
	}

	public boolean equals(Object o) {
		return this == o;
	}

	public void flush() {
		proteome = null;
		grn = null;
		genome.flush();
	}

	public void foodAvailable(int kind) {
		genome.activateFodGene(kind);
	}

	public void food1Available() {
		genome.activateFod1();
	}

	public void food2Available() {
		genome.activateFod2();
	}

	public void food3Available() {
		genome.activateFod3();
	}

	public void food4Available() {
		genome.activateFod4();
	}

	public void food5Available() {
		genome.activateFod5();
	}

	public void food6Available() {
		genome.activateFod6();
	}

	public void food7Available() {
		genome.activateFod7();
	}

	public void food8Available() {
		genome.activateFod8();
	}

	public void food9Available() {
		genome.activateFod9();
	}

	/**
	 * @return the biomass
	 */
	public int getBiomass() {
		return biomass;
	}

	/**
	 * @return the energy
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * @return the fitness
	 */
	public Integer getFitness() {
		return fitness;
	}

	/**
	 * @return the genome
	 */
	public Genome getGenome() {
		return genome;
	}

	public GRN getGRN() {
		return grn;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	public int getAncestralIndex() {
		return ancestralIndex;
	}

	public int getNumGenes() {
		return genome.getNumGenes();
	}

	public int getNumSites() {
		return genome.getNumSites();
	}

	public int getNumSitesFromGenes() {
		return genome.getNumSitesFromGenes();
	}

	/**
	 * @return the proteome
	 */
	public Proteome getProteome() {
		return proteome;
	}

	/**
	 * @return the stress
	 */
	public int getStress() {
		return stress;
	}

	public void initGRN() {
		grn = new GRN();

		// ugly
		genome.createNodesFor(grn);
	}

	public void initialize() {
		genome.labelGenes();
		initProteome();
		initGRN();
		initOrders();
		fitness = Integer.valueOf(0);
		biomass = stress = 0;
		highestEnergy = lowestEnergy = 0;
		totalEnergy = totalBiomass = 0;

		energy = config.getStartEnergy();
	}

	public void initOrders() {
		int nSpecies = proteome.getNumSpecies();
		int nSites = genome.getNumSites();
		speciesOrder = new ArrayList<Integer>(nSpecies);
		sitesOrder = new ArrayList<Integer>(nSites);

		for (int i = 0; i < nSpecies; i++)
			speciesOrder.add(Integer.valueOf(i));

		for (int i = 0; i < nSites; i++)
			sitesOrder.add(Integer.valueOf(i));

	}

	public void initProteome() {
		proteome = new Proteome(genome);
	}

	public boolean isAlive() {
		return (energy > 0 && stress < config.gettStress1());
	}

	public boolean isDead() {
		return energy <= 0 || stress > config.gettStress1();
	}

	public void mutate() {
		genome.mutate();
	}

	public void preEvolve() {
		mutate();
		genome.labelGenes();
	}

	public void randomizeOrders() {
		randomizeSpeciesOrder();
		randomizeSitesOrder();
	}

	public void randomizeSitesOrder() {
		Collections.shuffle(sitesOrder);
	}

	public void randomizeSpeciesOrder() {
		Collections.shuffle(speciesOrder);
	}

	public void regulateGenome() {
		for (Integer i : speciesOrder) {
			ProteinSpecies ps = proteome.getProteinSpecies(i.intValue());
			for (Integer j : sitesOrder) {
				if (ps.isSpent())
					break;

				BindingSite bs = genome.getSite(j.intValue());
				if (ps.bindsTo(bs)) {
					addEdgeToGRN(ps, bs);
				}
			}
		}

		genome.calcRegStates();
	}

	public void regulateSignallingGenes() {
		if (energy >= config.gettEnergy1())
			genome.activateNrg1();

		if (energy >= config.gettEnergy2())
			genome.activateNrg2();

		if (stress > 0) {
			genome.activateRcp1();
			genome.activateRcp2();
		}
	}

	public void removeEnergy(int e) {
		energy -= e;
	}

	public Model replicate() {
		flush();
		return new Model(this);
	}

	/**
	 * @param biomass the biomass to set
	 */
	public void setBiomass(int biomass) {
		this.biomass = biomass;
	}

	/**
	 * @param energy the energy to set
	 */
	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public void setFitness(int f) {
		fitness = Integer.valueOf(f);
	}

	/**
	 * @param genome the genome to set
	 */
	public void setGenome(Genome genome) {
		this.genome = genome;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @param proteome the proteome to set
	 */
	public void setProteome(Proteome proteome) {
		this.proteome = proteome;
	}

	/**
	 * @param stress the stress to set
	 */
	public void setStress(int stress) {
		this.stress = stress;
	}

	public void step() {
		randomizeOrders();
		regulateSignallingGenes();
		translateInputGenes();

		if (energy <= 0) {
			collectStats();
			return;
		}
		regulateGenome();
		translateRegulatedGenes();
		unbindAndDeactivate();
		collectStats();
	}

	private void collectStats() {
		if (energy < lowestEnergy)
			lowestEnergy = energy;

		if (energy > highestEnergy)
			highestEnergy = energy;

		totalEnergy += energy;
		totalBiomass += biomass;
	}

	public void calcMeans(int timesteps) {
		meanEnergy = (double) totalEnergy / (double) timesteps;
		meanBiomass = (double) totalBiomass / (double) timesteps;
	}

	public String getStatString() {
		String s = "energy=" + energy + ";biomass=" + biomass + ";fitness=" + fitness + ";lowestEnergy=" + lowestEnergy
				+ ";highestEnergy=" + highestEnergy + ";meanEnergy=" + String.format("%.3f", meanEnergy)
				+ ";meanBiomass=" + String.format("%.3f", meanBiomass) + ";nGenes=" + genome.getNumGenes() + ";nSites="
				+ genome.getNumSites();

		return s;
	}

	private void translateInputGenes() {
		genome.translateInputGenes();
	}

	private void translateRegulatedGenes() {
		genome.translateRegulatedGenes();
	}

	private void unbindAndDeactivate() {
		genome.unbindAndDeactivate();
		proteome.unbindAndDeactivate();
	}

}
