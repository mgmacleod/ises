package ises.model.molecular;

import ises.sys.Params;

public class ProteinSpecies extends ModelComponent {

	protected int prodRate, degRate, shape;
	protected int copies, boundCopies;
	protected Gene gene;

	public ProteinSpecies(Gene g) {
		gene = g;

		shape = randInt(Params.sMax);
		degRate = randInt(2) + 1;
		prodRate = randInt(8);

		name = g.getName() + "PP";
		copies = 0;
		boundCopies = 0;
	}

	public ProteinSpecies(ProteinSpecies parent, Gene g) {
		gene = g;
		this.name = g.getName() + "PP";

		this.prodRate = parent.prodRate;
		this.degRate = parent.degRate;
		this.shape = parent.shape;
		this.copies = this.boundCopies = 0;
	}

	public void addCopies(int inc) {
		copies += inc;
	}

	public boolean bindsTo(BindingSite bs) {
		if (bs == null) {
			return false;
		}
		if (isSpent())
			return false;

		double affinity = calcAffinityFor(bs);
		boolean binds = !bs.isOccupied() && Math.random() < affinity;

		if (binds) {
			bs.occupy();
			boundCopies++;
		}

		return binds;
	}

	public double calcAffinityFor(BindingSite bs) {
		if (bs == null) {
			return 0.0;
		}
		int d = Math.abs(shape - bs.getShape());

		if (d > Params.dMax)
			return 0.0;

		double b = (double) (d + 1);
		double a = 1 / b;
		return a;

	}

	public void degrade() {
		int count = 0;
		double degProb = 1 / (double) degRate;

		for (int i = 0; i < copies; i++) {
			if (Math.random() < degProb)
				count++;
		}

		copies -= count;

		if (copies < 0)
			copies = 0;
	}

	public boolean equals(Object o) {
		return this == o;
	}

	/**
	 * @return the boundCopies
	 */
	public int getBoundCopies() {
		return boundCopies;
	}

	/**
	 * @return the copies
	 */
	public int getCopies() {
		return copies;
	}

	public int getDegRate() {
		return degRate;
	}

	/**
	 * @return the parentGene
	 */
	public Gene getGene() {
		return gene;
	}

	public int getProdRate() {
		return prodRate;
	}

	public int getShape() {
		return shape;
	}

	public boolean isSpent() {
		return copies == 0 || boundCopies == copies;
	}

	public void label(String s) {
		name = s + "PP";
	}

	public void mutate() {
		mutateShape();
		mutateDegRate();
		mutateProdRate();
	}

	public void mutateDegRate() {
		if (Math.random() > Params.mStabP)
			return;

		degRate = addNoise(degRate, 2.0);

		if (degRate < 1)
			degRate = 1;

		if (degRate > Params.maxDegRate)
			degRate = Params.maxDegRate;

	}

	public void mutateProdRate() {
		if (Math.random() > Params.mProdP)
			return;
		prodRate = addNoise(prodRate, 2.0);

		if (prodRate < 1)
			prodRate = 1;

		else if (prodRate > Params.maxProdRate)
			prodRate = Params.maxProdRate;
	}

	public void mutateShape() {
		if (Math.random() > Params.mShapeP)
			return;

		int s = addNoise(shape, Math.log10((double) Params.sMax));

		if (s >= Params.sMax)
			shape = s - Params.sMax;

		else if (s < 0)
			shape = s + Params.sMax;

		else
			shape = s;

	}

	/**
	 * @param boundCopies the boundCopies to set
	 */
	public void setBoundCopies(int boundCopies) {
		this.boundCopies = boundCopies;
	}

	/**
	 * @param copies the copies to set
	 */
	public void setCopies(int copies) {
		this.copies = copies;
	}

	public void setDegRate(int degRate) {
		this.degRate = degRate;
	}

	/**
	 * @param parentGene the parentGene to set
	 */
	public void setParentGene(Gene parentGene) {
		this.gene = parentGene;
	}

	public void setProdRate(int prodRate) {
		this.prodRate = prodRate;
	}

	public void setShape(int shape) {
		this.shape = shape;
	}

	/**
	 * Sets the boundCopies of this protein species to 0 and stochastically degrades
	 * the number of available copies.
	 */
	public void unBindAndDeactivate() {
		boundCopies = 0;
		degrade();
	}

}
