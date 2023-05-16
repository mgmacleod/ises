package ises.model.molecular;

import ises.rest.entities.SimulationConfiguration;

public class BindingSite extends ModelComponent {
	protected int shape, bias, occupancy;
	protected Gene gene;
	private SimulationConfiguration config;

	private BindingSite(SimulationConfiguration config) {
		this.config = config;
	}

	public BindingSite(BindingSite bs, SimulationConfiguration config) {
		this(config);
		this.gene = bs.gene;
		this.bias = bs.bias;
		this.shape = bs.shape;
	}

	public BindingSite(BindingSite bs, Gene g, SimulationConfiguration config) {
		this(config);
		gene = g;
		this.shape = bs.shape;
		this.bias = bs.bias;
		this.occupancy = 0;

	}

	public BindingSite(Gene g, int index, SimulationConfiguration config) {
		this(config);
		gene = g;
		shape = randInt(config.getsMax());
		bias = (random() < 0.5) ? -1 : 1;
		occupancy = 0;

		name = gene.getName() + "BS#" + index;

	}

	public boolean equals(Object o) {
		return this == o;
	}

	/**
	 * @return the bias
	 */
	public int getBias() {
		return bias;
	}

	/**
	 * @return the gene
	 */
	public Gene getGene() {
		return gene;
	}

	/**
	 * @return the occupancy
	 */
	public int getOccupancy() {
		return occupancy;
	}

	/**
	 * @return the shape
	 */
	public int getShape() {
		return shape;
	}

	public boolean isOccupied() {
		return occupancy != 0;
	}

	public void label(String s, int i) {
		name = s + "BS#" + i;
	}

	public void mutate() {
		mutateShape();
		mutateBias();
	}

	public void mutateBias() {
		if (random() < config.getmFlipBS())
			bias = -bias;
	}

	public void mutateShape() {
		if (random() > config.getmShapeBS())
			return;

		int s = addNoise(shape, Math.log10((double) config.getsMax()));

		if (s >= config.getsMax())
			shape = s - config.getsMax();

		else if (s < 0)
			shape = s + config.getsMax();

		else
			shape = s;
	}

	public void occupy() {
		occupancy = 1;
	}

	public int regState() {
		return occupancy * bias;

	}

	/**
	 * @param bias the bias to set
	 */
	public void setBias(int bias) {
		this.bias = bias;
	}

	/**
	 * @param gene the gene to set
	 */
	public void setGene(Gene gene) {
		this.gene = gene;
	}

	/**
	 * @param occupancy the occupancy to set
	 */
	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}

	/**
	 * @param shape the shape to set
	 */
	public void setShape(int shape) {
		this.shape = shape;
	}

	public void unbindAndDeactivate() {
		occupancy = 0;
	}

	public String toString() {
		if (name == null)
			name = "bullshit";

		return name;
	}

}
