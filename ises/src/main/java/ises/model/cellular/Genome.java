package ises.model.cellular;

import java.util.ArrayList;

import ises.model.molecular.BindingSite;
import ises.model.molecular.Gene;
import ises.model.molecular.ModelComponent;
import ises.model.network.GeneRegulatoryNetwork;
import ises.model.network.GrnVertex;
import ises.model.network.GrnVertexType;
import ises.rest.entities.SimulationConfiguration;

public class Genome extends ModelComponent {

	private Gene fod1, fod2, fod3, fod4, fod5, fod6, fod7, fod8, fod9, nrg1, nrg2, rcp1, rcp2;
	private ArrayList<Gene> inputGenes, regGenes, outputGenes, allGenes;
	private ArrayList<BindingSite> sites;
	private Model model;
	private SimulationConfiguration config;

	/**
	 * A 'replication' constructor; copies the parent to produce a child
	 * 
	 * @param parent
	 * @param m
	 */
	public Genome(Genome parent, Model m, SimulationConfiguration config) {
		this.config = config;
		inputGenes = new ArrayList<>();
		regGenes = new ArrayList<>();
		outputGenes = new ArrayList<>();
		allGenes = new ArrayList<>();

		// fod genes
		fod1 = new Gene(parent.fod1, m, config);
		fod2 = new Gene(parent.fod2, m, config);
		fod3 = new Gene(parent.fod3, m, config);
		fod4 = new Gene(parent.fod4, m, config);
		fod5 = new Gene(parent.fod5, m, config);
		fod6 = new Gene(parent.fod6, m, config);
		fod7 = new Gene(parent.fod7, m, config);
		fod8 = new Gene(parent.fod8, m, config);
		fod9 = new Gene(parent.fod9, m, config);

		// signalling genes
		nrg1 = new Gene(parent.nrg1, m, config);
		nrg2 = new Gene(parent.nrg2, m, config);
		rcp1 = new Gene(parent.rcp1, m, config);
		rcp2 = new Gene(parent.rcp2, m, config);

		for (Gene g : parent.getOutputGenes()) {
			outputGenes.add(new Gene(g, m, config));
		}

		for (Gene g : parent.getRegGenes()) {
			regGenes.add(new Gene(g, m, config));
		}

		sites = new ArrayList<>(parent.getNumSites());
		addGenesToLists();

		addAllSites();
	}

	public Genome(Model m, SimulationConfiguration config) {
		this.config = config;
		model = m;

		inputGenes = new ArrayList<>();
		regGenes = new ArrayList<>();
		outputGenes = new ArrayList<>();
		allGenes = new ArrayList<>();
		sites = new ArrayList<>();

		// create fod genes
		fod1 = new Gene(m, "fod1", false, config);
		fod2 = new Gene(m, "fod2", false, config);
		fod3 = new Gene(m, "fod3", false, config);
		fod4 = new Gene(m, "fod4", false, config);
		fod5 = new Gene(m, "fod5", false, config);
		fod6 = new Gene(m, "fod6", false, config);
		fod7 = new Gene(m, "fod7", false, config);
		fod8 = new Gene(m, "fod8", false, config);
		fod9 = new Gene(m, "fod9", false, config);

		// create nrg genes
		nrg1 = new Gene(m, "nrg1", false, config);
		nrg2 = new Gene(m, "nrg2", false, config);

		// create rcp genes
		rcp1 = new Gene(m, "rcp1", false, config);
		rcp2 = new Gene(m, "rcp2", false, config);

		// create syn genes
		outputGenes.add(new Gene(m, "syn1", true, config));
		outputGenes.add(new Gene(m, "syn2", true, config));
		outputGenes.add(new Gene(m, "syn3", true, config));
		outputGenes.add(new Gene(m, "syn4", true, config));

		// create rsp genes
		outputGenes.add(new Gene(m, "rsp1", true, config));
		outputGenes.add(new Gene(m, "rsp2", true, config));

		// generate regulatory genes
		for (int i = 1; i <= config.getInitGenomeSize(); i++) {
			regGenes.add(new Gene(m, "reg" + i, true, config));
		}

		addGenesToLists();
		addAllSites();

	}

	public void activateFodGene(int kind) {
		if (kind == 1) {
			fod1.activate();
		}

		else if (kind == 2) {
			fod2.activate();
		} else if (kind == 3) {
			fod3.activate();
		}
	}

	public void activateFod1() {
		fod1.activate();
	}

	public void activateFod2() {
		fod2.activate();
	}

	public void activateFod3() {
		fod3.activate();
	}

	public void activateFod4() {
		fod4.activate();
	}

	public void activateFod5() {
		fod5.activate();
	}

	public void activateFod6() {
		fod6.activate();
	}

	public void activateFod7() {
		fod7.activate();
	}

	public void activateFod8() {
		fod8.activate();
	}

	public void activateFod9() {
		fod9.activate();
	}

	public void activateNrg1() {
		nrg1.activate();
	}

	public void activateNrg2() {
		nrg2.activate();
	}

	public void activateRcp1() {
		rcp1.activate();
	}

	public void activateRcp2() {
		rcp2.activate();
	}

	public void addAllSites() {
		for (Gene g : regGenes) {
			for (BindingSite bs : g.getRegRegion().getSites()) {
				sites.add(bs);
			}
		}

		for (Gene g : outputGenes) {
			for (BindingSite bs : g.getRegRegion().getSites()) {
				sites.add(bs);
			}
		}
	}

	public void addGenesToLists() {
		inputGenes.add(fod1);
		inputGenes.add(fod2);
		inputGenes.add(fod3);
		inputGenes.add(fod4);
		inputGenes.add(fod5);
		inputGenes.add(fod6);
		inputGenes.add(fod7);
		inputGenes.add(fod8);
		inputGenes.add(fod9);
		inputGenes.add(nrg1);
		inputGenes.add(nrg2);
		inputGenes.add(rcp1);
		inputGenes.add(rcp2);

		allGenes.addAll(inputGenes);
		allGenes.addAll(regGenes);
		allGenes.addAll(outputGenes);
	}

	public void addSite(BindingSite bs) {
		sites.add(bs);
	}

	public int calcNumSitesFromGenes() {
		int count = 0;
		for (Gene g : regGenes) {
			if (g.getRegRegion() != null) {
				count += g.getRegRegion().getSites().size();
			}
		}

		for (Gene g : outputGenes) {
			if (g.getRegRegion() != null) {
				count += g.getRegRegion().getSites().size();
			}
		}

		return count;
	}

	public void calcRegStates() {
		for (Gene g : regGenes) {
			g.calcRegState();
		}

		for (Gene g : outputGenes) {
			g.calcRegState();
		}
	}

	public void createNodesFor(GeneRegulatoryNetwork grn) {
		// TODO clean this up / find a better way to do it
		for (Gene g : inputGenes) {
			g.setVertex(new GrnVertex(g.name, GrnVertexType.INPUT));
			grn.addVertex(g.getVertex());
		}

		for (Gene g : regGenes) {
			g.setVertex(new GrnVertex(g.name, GrnVertexType.REGULATORY));
			grn.addVertex(g.getVertex());
		}

		for (Gene g : outputGenes) {
			g.setVertex(new GrnVertex(g.name, GrnVertexType.OUTPUT));
			grn.addVertex(g.getVertex());
		}
	}

	public boolean deleteGene(Gene g) {
		if (inputGenes.contains(g) || outputGenes.contains(g)) {
			return false;
		}

		if (regGenes.isEmpty()) {
			return false;
		}

		if (Math.random() < config.getmDelGene()) {
			regGenes.remove(g);
			allGenes.remove(g);
			return true;
		}

		return false;
	}

	public void duplicateGene(Gene g) {
		if (Math.random() > config.getmDupGene()) {
			return;
		}

		Gene ng = new Gene(g, config);
		regGenes.add(ng);
		allGenes.add(ng);

	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Genome)) {
			return false;
		}

		Genome gn = (Genome) o;

		return (regGenes.equals(gn.regGenes) && outputGenes.equals(gn.outputGenes));
	}

	public void flush() {
		unbindAndDeactivate();

	}

	public ArrayList<Gene> getAllGenes() {
		return allGenes;
	}

	public ArrayList<Gene> getInputGenes() {
		return inputGenes;
	}

	public Model getModel() {
		return model;
	}

	public int getNumGenes() {
		int numGenes = 0;
		numGenes += inputGenes.size();
		numGenes += outputGenes.size();
		numGenes += regGenes.size();

		if (numGenes != allGenes.size()) {
			print("getNumGenes is inconsistent.  Abandon ship!!");
		}

		return numGenes;
	}

	public int getNumSites() {
		return sites.size();
	}

	public int getNumSitesFromGenes() {
		int count = 0;
		for (Gene g : regGenes) {
			count += g.getRegRegion().getSites().size();
		}

		for (Gene g : outputGenes) {
			count += g.getRegRegion().getSites().size();
		}

		return count;
	}

	public ArrayList<Gene> getOutputGenes() {
		return outputGenes;
	}

	public ArrayList<Gene> getRegGenes() {
		return regGenes;
	}

	public BindingSite getSite(int i) {
		if (i < 0 || i >= sites.size()) {
			return null;
		}

		return sites.get(i);
	}

	public ArrayList<BindingSite> getSites() {
		return sites;
	}

	public void labelGenes() {
		for (int i = 0; i < regGenes.size(); i++) {
			regGenes.get(i).label("reg" + i);
		}

		for (Gene g : allGenes) {
			g.labelRegRegion();
		}

	}

	@Override
	public void mutate() {
		ArrayList<Gene> agCopy = new ArrayList<>(allGenes);

		for (Gene g : agCopy) {
			duplicateGene(g);
			g.mutate();
			deleteGene(g);
		}

		for (BindingSite bs : sites) {
			duplicateSite(bs);
			bs.mutate();
			deleteSite(bs);
		}

		if (sites.isEmpty()) {
			Gene g = randomRegulatedGene();
			BindingSite bs = new BindingSite(g, 0, config);
			g.getRegRegion().addSite(bs);
		}

		updateSitesList();
	}

	public Gene randomRegulatedGene() {
		if (random() < 0.5 && !regGenes.isEmpty()) {
			int r = randInt(regGenes.size());
			return regGenes.get(r);
		}

		else {
			int r = randInt(outputGenes.size());
			return outputGenes.get(r);
		}
	}

	public void duplicateSite(BindingSite bs) {
		if (random() < config.getmDupBS()) {
			Gene g = randomRegulatedGene();
			BindingSite bs2 = new BindingSite(bs, g, config);
			g.addSite(bs2);
		}
	}

	public void deleteSite(BindingSite bs) {
		if (random() < config.getmDelBS()) {
			Gene g = bs.getGene();
			g.removeSite(bs);
		}
	}

	public void printSites() {
		for (BindingSite bs : sites) {
			print(bs);
		}
	}

	public void printSitesFromGenes() {
		for (Gene g : regGenes) {
			g.printSites();
		}
	}

	public void removeSite(BindingSite bs) {
		sites.remove(bs);
	}

	public void setInputGenes(ArrayList<Gene> inputGenes) {
		this.inputGenes = inputGenes;
	}

	public void setOutputGenes(ArrayList<Gene> outputGenes) {
		this.outputGenes = outputGenes;
	}

	public void setRegGenes(ArrayList<Gene> regGenes) {
		this.regGenes = regGenes;
	}

	public void setSites(ArrayList<BindingSite> sites) {
		this.sites = sites;
	}

	public void translateInputGenes() {
		for (Gene g : inputGenes) {
			if (g.isActive()) {
				g.translate();

			}
		}
	}

	public void translateRegulatedGenes() {
		for (Gene g : regGenes) {

			if (g.getRegState() == 0 && random() < config.getkBasal()) {
				g.activate();
			}

			if (g.isActive()) {
				g.translate();
			}
		}

		for (Gene g : outputGenes) {

			if (g.getRegState() == 0 && random() < config.getkBasal()) {
				g.activate();
			}

			if (g.isActive()) {
				g.translate();
			}
		}
	}

	public void unbindAndDeactivate() {
		for (Gene g : inputGenes) {
			g.unbindAndDeactivate();
		}

		for (Gene g : regGenes) {
			g.unbindAndDeactivate();
		}

		for (Gene g : outputGenes) {
			g.unbindAndDeactivate();
		}
	}

	public void updateSitesList() {
		sites = new ArrayList<>();
		addAllSites();
	}

}
