package ises.stats;

public class SimData extends ComponentData {

	protected int mEnergy, mStress, mBiomass;
	protected int sTime, sFood1, sFood2, sFood3, sStress, sEnergy;
	protected int rand, numProteins, numBoundProteins;

	public SimData() {
		mEnergy = 0;
		mStress = 0;
		mBiomass = 0;
		sTime = 0;

		sFood1 = 0;
		sFood2 = 0;
		sFood3 = 0;
		sStress = 0;
		sEnergy = 0;

	}

	public int getNumProteins() {
		return numProteins;
	}

	public void setNumProteins(int numProteins) {
		this.numProteins = numProteins;
	}

	public int getNumBoundProteins() {
		return numBoundProteins;
	}

	public void setNumBoundProteins(int numBoundProteins) {
		this.numBoundProteins = numBoundProteins;
	}

	public void setRand(int rand) {
		this.rand = rand;
	}

	public int getRand() {
		return rand;
	}

	public int getmEnergy() {
		return mEnergy;
	}

	public void setmEnergy(int mEnergy) {
		this.mEnergy = mEnergy;
	}

	public int getmStress() {
		return mStress;
	}

	public void setmStress(int mStress) {
		this.mStress = mStress;
	}

	public int getmBiomass() {
		return mBiomass;
	}

	public void setmBiomass(int mBiomass) {
		this.mBiomass = mBiomass;
	}

	public int getsTime() {
		return sTime;
	}

	public void setsTime(int sTime) {
		this.sTime = sTime;
	}

	public int getsFood1() {
		return sFood1;
	}

	public void setsFood1(int sFood1) {
		this.sFood1 = sFood1;
	}

	public int getsFood2() {
		return sFood2;
	}

	public void setsFood2(int sFood2) {
		this.sFood2 = sFood2;
	}

	public int getsFood3() {
		return sFood3;
	}

	public void setsFood3(int sFood3) {
		this.sFood3 = sFood3;
	}

	public int getsStress() {
		return sStress;
	}

	public void setsStress(int sStress) {
		this.sStress = sStress;
	}

	public int getsEnergy() {
		return sEnergy;
	}

	public void setsEnergy(int sEnergy) {
		this.sEnergy = sEnergy;
	}

}
