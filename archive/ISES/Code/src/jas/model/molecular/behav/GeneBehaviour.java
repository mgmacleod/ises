package jas.model.molecular.behav;

import jas.model.cellular.Model;
import jas.model.molecular.Gene;
import jas.model.molecular.ModelComponent;
import jas.sys.Params;

public class GeneBehaviour extends ModelComponent {
	
	protected Gene				gene;
	protected Model				model;
	
	public GeneBehaviour() {}
	public GeneBehaviour(Model m, Gene g) {
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
		model.removeEnergy(Params.cRNA + (nProteins * Params.cProtein));
		
		// add the proteins to the model; changes reflected in the proteome
		gene.getProteinSpecies().addCopies(nProteins);
	}
	
	
	
	
	
	
	
	

}
