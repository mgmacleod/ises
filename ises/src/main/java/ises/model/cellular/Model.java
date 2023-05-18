package ises.model.cellular;

import java.util.ArrayList;
import java.util.Collections;

import org.jgrapht.graph.DefaultWeightedEdge;

import ises.Thing;
import ises.model.molecular.BindingSite;
import ises.model.molecular.ProteinSpecies;
import ises.model.network.GeneRegulatoryNetwork;
import ises.model.network.GrnVertex;
import ises.rest.entities.SimulationConfiguration;

/**
 * Represents an abstract 'cell' that consists or a {@link Genome}, its representation as a
 * {@link GeneRegulatoryNetwork}, and the {@link Proteome} generated from it. It also contains the logic to take a
 * {@link #step()} in the simulation.
 */
public class Model extends Thing implements Comparable<Model> {

	private Genome genome;
	private Proteome proteome;
	private int energy, biomass, stress;
	private int ancestralIndex;
	private Integer fitness;
	private GeneRegulatoryNetwork grn;
	private ArrayList<Integer> speciesOrder;
	private ArrayList<Integer> sitesOrder;
	private int highestEnergy, lowestEnergy;
	private int totalEnergy, totalBiomass;
	private double meanEnergy, meanBiomass;
	private SimulationConfiguration config;

	public Model(int i, SimulationConfiguration config) {
		genome = new Genome(this, config);
		ancestralIndex = i;
		this.config = config;

		initialize();
	}

	public Model(Model parent) {
		ancestralIndex = parent.ancestralIndex;
		genome = new Genome(parent.genome, this);

		energy = parent.energy;
		stress = parent.stress;
		biomass = parent.biomass;
		fitness = Integer.valueOf(parent.fitness.intValue());
		highestEnergy = parent.highestEnergy;
		lowestEnergy = parent.lowestEnergy;
		meanBiomass = parent.meanBiomass;
		meanEnergy = parent.meanEnergy;
		config = parent.config;
	}

	private void addBiomass(int bm) {
		biomass += bm;
	}

	private void addEdgeToGRN(ProteinSpecies ps, BindingSite bs) {
		GrnVertex regulator = ps.getGene().getVertex();
		GrnVertex target = bs.getGene().getVertex();
		double weight = ps.calcAffinityFor(bs) * bs.getBias();
		DefaultWeightedEdge edge = grn.addEdge(regulator, target);
		if (edge != null) {
			grn.setEdgeWeight(edge, weight);
		}

	}

	private void addEnergy(int e) {
		energy += e;
	}

	public void addStress() {
		stress += config.getStressInProduction();
	}

	public int getNumShapes() {
		return genome.getNumGenes() + proteome.getNumSpecies();
	}

	@Override
	public int compareTo(Model m) {
		return fitness.compareTo(m.fitness);
	}

	public void doFod1() {
		addEnergy(config.getEnergy1Production());
	}

	public void doFod2() {
		addEnergy(config.getEnergy2Production());
	}

	public void doFod3() {
		addEnergy(config.getEnergy3Production());
	}

	public void doFod4() {
		addEnergy(config.getEnergy4Production());
	}

	public void doFod5() {
		addEnergy(config.getEnergy5Production());
	}

	public void doFod6() {
		addEnergy(config.getEnergy6Production());
	}

	public void doFod7() {
		addEnergy(config.getEnergy7Production());
	}

	public void doFod8() {
		addEnergy(config.getEnergy8Production());
	}

	public void doFod9() {
		addEnergy(config.getEnergy9Production());
	}

	public void doRsp1() {
		if (stress <= 0) {
			return;
		}
		stress -= config.getStress1Production();
		removeEnergy(config.getStress1Cost());
	}

	public void doRsp2() {
		if (stress <= 0) {
			return;
		}

		stress -= config.getStress2Production();
		removeEnergy(config.getStress2Cost());
	}

	public void doSyn1() {
		addBiomass(config.getBiomass1Production());
		removeEnergy(config.getBiomass1Cost());
	}

	public void doSyn2() {
		addBiomass(config.getBiomass2Production());
		removeEnergy(config.getBiomass2Cost());
	}

	public void doSyn3() {
		addBiomass(config.getBiomass3Production());
		removeEnergy(config.getBiomass3Cost());
	}

	public void doSyn4() {
		addBiomass(config.getBiomass4Production());
		removeEnergy(config.getBiomass4Cost());
	}

	@Override
	public boolean equals(Object o) {
		return this == o;
	}

	private void flush() {
		proteome = null;
		grn = null;
		genome.flush();
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

	public GeneRegulatoryNetwork getGRN() {
		return grn;
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

	public Proteome getProteome() {
		return proteome;
	}

	public int getStress() {
		return stress;
	}

	public void initGRN() {
		grn = new GeneRegulatoryNetwork();
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

	private void initOrders() {
		int nSpecies = proteome.getNumSpecies();
		int nSites = genome.getNumSites();
		speciesOrder = new ArrayList<>(nSpecies);
		sitesOrder = new ArrayList<>(nSites);

		for (int i = 0; i < nSpecies; i++) {
			speciesOrder.add(Integer.valueOf(i));
		}

		for (int i = 0; i < nSites; i++) {
			sitesOrder.add(Integer.valueOf(i));
		}

	}

	public void initProteome() {
		proteome = new Proteome(genome);
	}

	public boolean isAlive() {
		return (energy > 0 && stress < config.getStress1Threshold());
	}

	public void mutate() {
		genome.mutate();
	}

	public void preEvolve() {
		mutate();
		genome.labelGenes();
	}

	public void removeEnergy(int e) {
		energy -= e;
	}

	public Model replicate() {
		flush();
		return new Model(this);
	}

	public void setFitness(int f) {
		fitness = Integer.valueOf(f);
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

	private void randomizeOrders() {
		randomizeSpeciesOrder();
		randomizeSitesOrder();
	}

	private void randomizeSpeciesOrder() {
		Collections.shuffle(speciesOrder);
	}

	private void randomizeSitesOrder() {
		Collections.shuffle(sitesOrder);
	}

	private void regulateSignallingGenes() {
		if (energy >= config.getEnergy1Threshold()) {
			genome.activateNrg1();
		}

		if (energy >= config.getEnergy2Threshold()) {
			genome.activateNrg2();
		}

		if (stress > 0) {
			genome.activateRcp1();
			genome.activateRcp2();
		}
	}

	private void translateInputGenes() {
		genome.translateInputGenes();
	}

	private void regulateGenome() {
		for (Integer i : speciesOrder) {
			ProteinSpecies ps = proteome.getProteinSpecies(i.intValue());
			for (Integer j : sitesOrder) {
				if (ps.isSpent()) {
					break;
				}

				BindingSite bs = genome.getSite(j.intValue());
				if (ps.bindsTo(bs)) {
					addEdgeToGRN(ps, bs);
				}
			}
		}

		genome.calcRegStates();
	}

	private void translateRegulatedGenes() {
		genome.translateRegulatedGenes();
	}

	private void unbindAndDeactivate() {
		genome.unbindAndDeactivate();
		proteome.unbindAndDeactivate();
	}

	private void collectStats() {
		if (energy < lowestEnergy) {
			lowestEnergy = energy;
		}

		if (energy > highestEnergy) {
			highestEnergy = energy;
		}

		totalEnergy += energy;
		totalBiomass += biomass;
	}

	public void calcMeans(int timesteps) {
		meanEnergy = (double) totalEnergy / (double) timesteps;
		meanBiomass = (double) totalBiomass / (double) timesteps;
	}

	public String getStatString() {
		String s = "energy=" + energy + ";biomass=" + biomass + ";fitness=" + fitness + ";lowestEnergy=" + lowestEnergy + ";highestEnergy="
				+ highestEnergy + ";meanEnergy=" + String.format("%.3f", meanEnergy) + ";meanBiomass=" + String.format("%.3f", meanBiomass)
				+ ";nGenes=" + genome.getNumGenes() + ";nSites=" + genome.getNumSites();

		return s;
	}

}
