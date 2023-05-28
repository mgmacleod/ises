package ises.model.cellular;

import java.util.ArrayList;

import ises.model.molecular.Gene;
import ises.model.molecular.ModelComponent;
import ises.model.molecular.ProteinSpecies;

/**
 * Represents the abstract proteome of a {@link Model}, which consists of a collection of {@link ProteinSpecies} and
 * some basic logic for manipulating them.
 */
public class Proteome extends ModelComponent {
	private ArrayList<ProteinSpecies> species;

	public Proteome(Genome genome) {
		species = new ArrayList<>();

		for (Gene g : genome.getAllGenes()) {
			species.add(g.getProteinSpecies());
		}

	}

	public int getNumSpecies() {
		return species.size();
	}

	public void unbindAndDeactivate() {
		for (ProteinSpecies ps : species) {
			ps.unBindAndDeactivate();
		}
	}

	public ProteinSpecies getProteinSpecies(int i) {
		if (i < 0 || i >= species.size()) {
			return null;
		}

		return species.get(i);
	}

	public ArrayList<ProteinSpecies> getSpecies() {
		return species;
	}

}
