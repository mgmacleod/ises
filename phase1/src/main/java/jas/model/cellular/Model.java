package jas.model.cellular;

import jas.Thing;
import jas.model.molecular.BindingSite;
import jas.model.molecular.ProteinSpecies;
import jas.model.network.Edge;
import jas.model.network.GRN;
import jas.model.network.Node;
import jas.sys.Params;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

public class Model extends Thing implements Comparable<Model> {
	
	///////////////////////////  Instance variables  \\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	protected Genome							genome;
	protected Proteome							proteome;
	protected int								energy, biomass, stress;
	protected int 								index, ancestralIndex;
	protected Integer							fitness;
	protected GRN								grn;
	protected ArrayList<Integer>				speciesOrder;
	protected ArrayList<Integer>				sitesOrder;
	protected int								highestEnergy, lowestEnergy;
	protected int								totalEnergy, totalBiomass;
	protected double							meanEnergy, meanBiomass;
	
	
	
	
	
	////////////////////////////  Constructors  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public Model(Genome g) {
		genome = g;
		
	}

	public Model(int i) {
		genome = new Genome(this);
		index = ancestralIndex = i;
		
		initialize();
	}
	
	public Model(Model parent) {
		this.ancestralIndex = parent.ancestralIndex;
		genome = new Genome(parent.genome, this);
		
		this.energy = parent.energy;
		this.stress = parent.stress;
		this.biomass = parent.biomass;
		this.fitness = new Integer(parent.fitness.intValue());
		this.highestEnergy = parent.highestEnergy;
		this.lowestEnergy = parent.lowestEnergy;
		this.meanBiomass = parent.meanBiomass;
		this.meanEnergy = parent.meanEnergy;
		
	}
	
	public Model() {
		this(0);
	}
	
	
	
	
	/////////////////////////  Methods  \\\\\\\\\\\\\\\\\\\\\\
	
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
		stress += Params.pStressIn;
	}
	
	public int getNumShapes() {
		return genome.getNumGenes() + genome.getNumSites();
	}
	
	public int compareTo(Model m) {
		
		return this.fitness.compareTo(m.fitness);
	}
	
	public void doFod1() {
		addEnergy(Params.pEnergy1);
	}
	
	public void doFod2() {
		addEnergy(Params.pEnergy2);
	}
	
	public void doFod3() {
		addEnergy(Params.pEnergy3);
	}
	
	public void doFod4() {
		addEnergy(Params.pEnergy4);
	}
	
	public void doFod5() {
		addEnergy(Params.pEnergy5);
	}
	
	public void doFod6() {
		addEnergy(Params.pEnergy6);
	}
	
	public void doFod7() {
		addEnergy(Params.pEnergy7);
	}
	
	public void doFod8() {
		addEnergy(Params.pEnergy8);
	}
	
	public void doFod9() {
		addEnergy(Params.pEnergy9);
	}
	
	public void doRsp1() {
		if (stress <= 0) {
			return;
		}
		stress -= Params.pStress1;
		removeEnergy(Params.cStress1);
	}
	
	public void doRsp2() {
		if (stress <= 0) {
			return;
		}
		
		stress -= Params.pStress2;
		removeEnergy(Params.cStress2);
	}
	
	public void doSyn1() {
		addBiomass(Params.pBio1);
		removeEnergy(Params.cBio1);
	}
	
	public void doSyn2() {
		addBiomass(Params.pBio2);
		removeEnergy(Params.cBio2);
	}
	
	public void doSyn3() {
		addBiomass(Params.pBio3);
		removeEnergy(Params.cBio3);
	}
	
	public void doSyn4() {
		addBiomass(Params.pBio4);
		removeEnergy(Params.cBio4);
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
		fitness = new Integer(0);
		biomass = stress = 0;
		highestEnergy = lowestEnergy = 0;
		totalEnergy = totalBiomass = 0;
		
		energy = Params.startEnergy;
	}
	
	public void initOrders() {
		int nSpecies = proteome.getNumSpecies();
		int nSites = genome.getNumSites();
		speciesOrder = new ArrayList<Integer>(nSpecies);
		sitesOrder = new ArrayList<Integer>(nSites);
		
		for (int i=0; i<nSpecies; i++) 
			speciesOrder.add(new Integer(i));
		
		for (int i=0; i<nSites; i++)
			sitesOrder.add(new Integer(i));
		
	}
	
	public void initProteome() {
		proteome = new Proteome(genome);
	}
	
	public boolean isAlive() {
		return (energy > 0 && stress < Params.tStress1);
	}
	
	public boolean isDead() {
		return energy <= 0 || stress > Params.tStress1;
	}
	
	public void mutate() {
		genome.mutate();
	}
	

	public void preEvolve() {
		mutate();
		genome.labelGenes();
		
		/*
		print("from Model.preEvolve()");
		for (BindingSite bs: genome.getSites())
			print(bs);
		*/
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
		for (Integer i: speciesOrder) {
			ProteinSpecies ps = proteome.getProteinSpecies(i.intValue());
			for (Integer j: sitesOrder) {
				if (ps.isSpent())
					break;
				
				BindingSite bs = genome.getSite(j.intValue());
				if (ps.bindsTo(bs)) {
					addEdgeToGRN(ps, bs);
					//print("edge should be added");
				}
			}
		}
		
		genome.calcRegStates();
	}



	public void regulateSignallingGenes() {
		if (energy >= Params.tEnergy1)
			genome.activateNrg1();
		
		if (energy >= Params.tEnergy2)
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
		fitness = new Integer(f);
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

	///////////////////////////////////  Methods  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
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
	
	public void collectStats() {
		if (energy < lowestEnergy)
			lowestEnergy = energy;
		
		if (energy > highestEnergy)
			highestEnergy = energy;
		
		totalEnergy += energy;
		totalBiomass += biomass;
	}
	
	public void calcMeans(int timesteps) {
		meanEnergy = (double)totalEnergy / (double)timesteps;
		meanBiomass = (double)totalBiomass / (double)timesteps;
	}
	
	public String getStatString() {
		String s = "energy=" + energy + ";biomass=" + biomass + ";fitness=" + fitness +
					";lowestEnergy=" + lowestEnergy + ";highestEnergy=" + highestEnergy +
					";meanEnergy=" + String.format("%.3f", meanEnergy) + ";meanBiomass=" +
					String.format("%.3f", meanBiomass) + ";nGenes=" + genome.getNumGenes() +
					";nSites=" + genome.getNumSites();
		
		return s;
	}
	
	

	public void translateInputGenes() {
		genome.translateInputGenes();
	}


	public void translateRegulatedGenes() {
		genome.translateRegulatedGenes();
	}


	public void unbindAndDeactivate() {
		genome.unbindAndDeactivate();
		proteome.unbindAndDeactivate();
	}
	
	public void prepareGRN(Rectangle bounds) {
		initGRN();
		
	}

	
}
