package ises.stats;

import ises.model.cellular.Genome;
import ises.model.cellular.Model;
import ises.model.cellular.Proteome;
import ises.model.molecular.BindingSite;
import ises.model.molecular.ProteinSpecies;
import ises.rest.entities.SimulationConfiguration;

public class ShapeDistribution {
	protected int[] freqs;
	protected int numEntries;
	protected double[] pFreqs;
	protected int mcShape, lcShape, highestFreq, lowestFreq;
	protected int numPopulated, numUnpopulated;
	protected double popUnpopRatio;
	protected double meanShape, meanFreq, sdShape, sdFreq;
	private SimulationConfiguration config; // TODO initialize this sucker!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	public ShapeDistribution(Genome genome) {
		initFreqs();
		addFromGenome(genome);
		numEntries = genome.getNumSites();
		initialize();
	}

	public ShapeDistribution(Model model) {
		initFreqs();
		addFromGenome(model.getGenome());

		if (model.getProteome() == null)
			model.initProteome();

		addFromProteome(model.getProteome());
		numEntries = model.getNumShapes();
		initialize();
	}

	public ShapeDistribution(Proteome proteome) {
		initFreqs();
		addFromProteome(proteome);
		numEntries = proteome.getNumSpecies();
		initialize();
	}

	public void addFromGenome(Genome genome) {
		for (BindingSite bs : genome.getSites()) {
			freqs[bs.getShape()]++;
		}
	}

	public void addFromProteome(Proteome proteome) {
		for (ProteinSpecies ps : proteome.getSpecies()) {
			freqs[ps.getShape()]++;
		}
	}

	public void calcAll() {
		calcMeanShape();
		calcMeanFreq();
		calcSDShape();
		calcSDFreq();
		calcPopulatedStats();
	}

	public void calcMeanFreq() {
		meanFreq = 0.0;

		int sum = 0;
		for (int i = 0; i < freqs.length; i++) {
			sum += freqs[i];
		}

		meanFreq = sum / (double) config.getsMax();
	}

	public void calcMeanShape() {
		meanShape = 0.0;
		int sum = 0;
		for (int i = 0; i < freqs.length; i++) {
			sum += (i * freqs[i]);
		}

		meanShape = sum / (double) numEntries;
	}

	public void calcPopulatedStats() {
		numPopulated = 0;
		numUnpopulated = 0;
		for (int i = 0; i < freqs.length; i++) {
			if (freqs[i] != 0)
				numPopulated++;
			else
				numUnpopulated++;
		}

		popUnpopRatio = (double) numPopulated / (double) numUnpopulated;
	}

	public void calcSDFreq() {
		sdFreq = 0.0;
		double sos = 0.0;

		for (int i = 0; i < freqs.length; i++) {

			double diff = meanFreq - freqs[i];
			sos += (diff * diff);
		}

		double var = sos / (double) config.getsMax();
		sdFreq = Math.sqrt(var);
	}

	public void calcSDShape() {
		sdShape = 0.0;
		double sos = 0.0;
		int count = 0;

		for (int i = 0; i < freqs.length; i++) {
			if (freqs[i] > 0) {
				double diff = meanShape - i;
				sos += (diff * diff);
				count++;
			}
		}

		double var = sos / (double) count;
		sdShape = Math.sqrt(var);

	}

	public void decrement(int shape) {
		freqs[shape - 1]--;
	}

	public int difference(ShapeDistribution sd) {
		if (sd == null)
			return Integer.MIN_VALUE;

		if (this.freqs.length != sd.freqs.length)
			return Integer.MAX_VALUE;

		int ans = 0;
		for (int i = 0; i < freqs.length; i++) {
			int a = this.freqs[i] - sd.freqs[i];
			ans += Math.abs(a);
		}

		return ans;
	}

	public void findAll() {
		findHighs();
		findLows();
	}

	public void findHighs() {
		mcShape = 0;
		highestFreq = 0;
		for (int i = 0; i < freqs.length; i++) {
			if (freqs[i] > freqs[mcShape]) {
				mcShape = i;
				highestFreq = freqs[i];
			}
		}
	}

	public void findLows() {
		lcShape = 0;
		lowestFreq = 10000;
		for (int i = 0; i < freqs.length; i++) {
			if (freqs[i] != 0 && freqs[i] < freqs[lcShape]) {
				lcShape = i;
				lowestFreq = freqs[i];
			}
		}

		if (lowestFreq == 10000)
			lowestFreq = 0;
	}

	public int get(int shape) {
		return freqs[shape - 1];
	}

	public String getDistroString() {
		String s = "\nShape distribution:\n";

		for (int i = 0; i < freqs.length; i++) {
			s += "" + freqs[i] + " ";
		}

		return s + "\n";
	}

	public int[] getFreqs() {
		return freqs;
	}

	public int getHighestFreq() {
		return highestFreq;
	}

	public int getLcShape() {
		return lcShape;
	}

	public int getLowestFreq() {
		return lowestFreq;
	}

	public int getMcShape() {
		return mcShape;
	}

	public double getMeanFreq() {
		return meanFreq;
	}

	public double getMeanShape() {
		return meanShape;
	}

	public int getNumEntries() {
		return numEntries;
	}

	public int getNumPopulated() {
		return numPopulated;
	}

	public int getNumUnpopulated() {
		return numUnpopulated;
	}

	public double[] getpFreqs() {
		return pFreqs;
	}

	public double getPopUnpopRatio() {
		return popUnpopRatio;
	}

	public double getSdFreq() {
		return sdFreq;
	}

	public double getSdShape() {
		return sdShape;
	}

	public String getStatString() {
		String s = "numEntries=" + numEntries + ";mcShape=" + mcShape + ";lcShape=" + lcShape + ";highestFreq="
				+ highestFreq + ";lowestFreq=" + lowestFreq + ";numPopulated=" + numPopulated + ";numUnpopulated="
				+ numUnpopulated + ";popUnpopRatio=" + String.format("%.3f", popUnpopRatio) + ";meanShape="
				+ String.format("%.3f", meanShape) + ";meanFreq=" + String.format("%.3f", meanFreq) + ";sdShape="
				+ String.format("%.3f", sdShape) + ";sdFreq=" + String.format("%.3f", sdFreq);
		return s;
	}

	public void increment(int shape) {
		freqs[shape - 1]++;
	}

	public void initFreqs() {
		freqs = new int[config.getsMax()];
		for (int shape = 0; shape < config.getsMax(); shape++) {
			freqs[shape] = 0;
		}
	}

	public void initialize() {
		initPFreqs();
		findAll();
		calcAll();
	}

	public void initPFreqs() {
		pFreqs = new double[config.getsMax()];
		for (int i = 0; i < freqs.length; i++) {
			pFreqs[i] = (freqs[i] / (double) numEntries);
		}
	}

}
