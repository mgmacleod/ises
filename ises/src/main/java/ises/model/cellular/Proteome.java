package ises.model.cellular;

import java.util.ArrayList;

import ises.model.molecular.Gene;
import ises.model.molecular.ModelComponent;
import ises.model.molecular.ProteinSpecies;

public class Proteome extends ModelComponent {
	protected ArrayList<ProteinSpecies> species;

	public Proteome() {

	}

	public Proteome(Genome genome) {
		species = new ArrayList<ProteinSpecies>();

		for (Gene g : genome.getAllGenes())
			species.add(g.getProteinSpecies());

	}

	public int getNumProteins() {
		int count = 0;

		for (ProteinSpecies ps : species)
			count += ps.getCopies();

		return count;
	}

	public int getNumBoundProteins() {
		int count = 0;

		for (ProteinSpecies ps : species)
			count += ps.getBoundCopies();

		return count;
	}

	public void addSpecies(ProteinSpecies ps) {
		species.add(ps);
	}

	public int getNumSpecies() {
		return species.size();
	}

	public void unbindAndDeactivate() {
		for (ProteinSpecies ps : species)
			ps.unBindAndDeactivate();
	}

	public boolean hasNextSpecies() {
		return !species.isEmpty();
	}

	public ProteinSpecies getProteinSpecies(int i) {
		if (i < 0 || i >= species.size())
			return null;

		return species.get(i);
	}

	/**
	 * @return the species
	 */
	public ArrayList<ProteinSpecies> getSpecies() {
		return species;
	}

	/**
	 * @param species the species to set
	 */
	public void setSpecies(ArrayList<ProteinSpecies> species) {
		this.species = species;
	}

}
