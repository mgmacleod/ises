package ises.model.molecular;

import ises.rest.entities.SimulationConfiguration;

/**
 * Represents an abstract binding site on the {@link RegulatoryRegion} of a
 * {@link Gene}, consisting of a simple integer
 * {@link #shape}, a positive or negative {@link #bias} (indicating whether its
 * effect on the gene's expression is
 * activating or inhibiting), and an {@link #occupancy} (whether or not it is
 * bound to a transcription factor).
 */
public class BindingSite extends ModelComponent {
	private int shape, bias, occupancy;
	private Gene gene;
	private SimulationConfiguration config;

	private BindingSite(SimulationConfiguration config) {
		this.config = config;
	}

	public BindingSite(BindingSite bs, SimulationConfiguration config) {
		this(config);
		gene = bs.gene;
		bias = bs.bias;
		shape = bs.shape;
	}

	public BindingSite(BindingSite bs, Gene g, SimulationConfiguration config) {
		this(config);
		gene = g;
		shape = bs.shape;
		bias = bs.bias;
		occupancy = 0;

	}

	public BindingSite(Gene g, int index, SimulationConfiguration config) {
		this(config);
		gene = g;
		shape = randInt(config.getShapeMax());
		bias = (random() < 0.5) ? -1 : 1;
		occupancy = 0;

		name = gene.getName() + "BS#" + index;

	}

	@Override
	public boolean equals(Object o) {
		return this == o;
	}

	public int getBias() {
		return bias;
	}

	public Gene getGene() {
		return gene;
	}

	public int getShape() {
		return shape;
	}

	public boolean isOccupied() {
		return occupancy != 0;
	}

	public void label(String s, int i) {
		name = s + "BS#" + i;
	}

	@Override
	public void mutate() {
		mutateShape();
		mutateBias();
	}

	private void mutateBias() {
		if (random() < config.getBindSiteFlipProbability()) {
			bias = -bias;
		}
	}

	private void mutateShape() {
		if (random() > config.getBindSiteShapeMutProbability()) {
			return;
		}

		int s = addNoise(shape, Math.log10(config.getShapeMax()));

		if (s >= config.getShapeMax()) {
			shape = s - config.getShapeMax();
		} else if (s < 0) {
			shape = s + config.getShapeMax();
		} else {
			shape = s;
		}
	}

	public void occupy() {
		occupancy = 1;
	}

	public int regState() {
		return occupancy * bias;

	}

	public void unbindAndDeactivate() {
		occupancy = 0;
	}

	@Override
	public String toString() {
		return name;
	}

}
