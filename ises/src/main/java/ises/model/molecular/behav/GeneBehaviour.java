package ises.model.molecular.behav;

import ises.model.cellular.Model;
import ises.model.molecular.Gene;
import ises.model.molecular.ModelComponent;
import ises.rest.entities.SimulationConfiguration;

public class GeneBehaviour extends ModelComponent {

	protected Gene gene;
	protected Model model;
	private SimulationConfiguration config;

	public GeneBehaviour() {
	}

	public GeneBehaviour(Model m, Gene g, SimulationConfiguration config) {
		this.config = config;
		model = m;
		gene = g;
	}

	/**
	 * @return the gene
	 */
	public Gene getGene() {
		return gene;
	}

	public void pay(int cost) {
		model.removeEnergy(cost);
	}

	/**
	 * @param gene the gene to set
	 */
	public void setGene(Gene gene) {
		this.gene = gene;
	}

	public void translate() {
		// determine number of proteins to produce

		int nProteins = addNoise(gene.getProteinSpecies().getProdRate(), 1.5);

		// pay for translation
		model.removeEnergy(config.getcRNA() + (nProteins * config.getcProtein()));

		// add the proteins to the model; changes reflected in the proteome
		gene.getProteinSpecies().addCopies(nProteins);
	}

}
