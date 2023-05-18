package ises.stats;

import ises.model.cellular.Genome;
import ises.model.cellular.Model;
import ises.model.cellular.Proteome;
import ises.model.molecular.BindingSite;
import ises.model.molecular.ProteinSpecies;
import ises.rest.entities.SimulationConfiguration;

public class ShapeDistribution {
	private int[] freqs;
	private int numEntries;
	private double[] pFreqs;
	private int mcShape, lcShape, highestFreq, lowestFreq;
	private int numPopulated, numUnpopulated;
	private double popUnpopRatio;
	private double meanShape, meanFreq, sdShape, sdFreq;
	private SimulationConfiguration config;

	public ShapeDistribution(Model model, SimulationConfiguration config) {
		this.config = config;
		initFreqs();
		addFromGenome(model.getGenome());

		if (model.getProteome() == null) {
			model.initProteome();
		}

		addFromProteome(model.getProteome());
		numEntries = model.getNumShapes();
		initialize();
	}

	private void addFromGenome(Genome genome) {
		for (BindingSite bs : genome.getSites()) {
			freqs[bs.getShape()]++;
		}
	}

	private void addFromProteome(Proteome proteome) {
		for (ProteinSpecies ps : proteome.getSpecies()) {
			freqs[ps.getShape()]++;
		}
	}

	private void calcAll() {
		calcMeanShape();
		calcMeanFreq();
		calcSDShape();
		calcSDFreq();
		calcPopulatedStats();
	}

	private void calcMeanFreq() {
		meanFreq = 0.0;

		int sum = 0;
		for (int i = 0; i < freqs.length; i++) {
			sum += freqs[i];
		}

		meanFreq = sum / (double) config.getsMax();
	}

	private void calcMeanShape() {
		meanShape = 0.0;
		int sum = 0;
		for (int i = 0; i < freqs.length; i++) {
			sum += (i * freqs[i]);
		}

		meanShape = sum / (double) numEntries;
	}

	private void calcPopulatedStats() {
		numPopulated = 0;
		numUnpopulated = 0;
		for (int i = 0; i < freqs.length; i++) {
			if (freqs[i] != 0) {
				numPopulated++;
			} else {
				numUnpopulated++;
			}
		}

		popUnpopRatio = (double) numPopulated / (double) numUnpopulated;
	}

	private void calcSDFreq() {
		sdFreq = 0.0;
		double sos = 0.0;

		for (int i = 0; i < freqs.length; i++) {

			double diff = meanFreq - freqs[i];
			sos += (diff * diff);
		}

		double var = sos / config.getsMax();
		sdFreq = Math.sqrt(var);
	}

	private void calcSDShape() {
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

		double var = sos / count;
		sdShape = Math.sqrt(var);

	}

	private void findAll() {
		findHighs();
		findLows();
	}

	private void findHighs() {
		mcShape = 0;
		highestFreq = 0;
		for (int i = 0; i < freqs.length; i++) {
			if (freqs[i] > freqs[mcShape]) {
				mcShape = i;
				highestFreq = freqs[i];
			}
		}
	}

	private void findLows() {
		lcShape = 0;
		lowestFreq = 10000;
		for (int i = 0; i < freqs.length; i++) {
			if (freqs[i] != 0 && freqs[i] < freqs[lcShape]) {
				lcShape = i;
				lowestFreq = freqs[i];
			}
		}

		if (lowestFreq == 10000) {
			lowestFreq = 0;
		}
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
		String s = "numEntries=" + numEntries + ";mcShape=" + mcShape + ";lcShape=" + lcShape + ";highestFreq=" + highestFreq + ";lowestFreq="
				+ lowestFreq + ";numPopulated=" + numPopulated + ";numUnpopulated=" + numUnpopulated + ";popUnpopRatio="
				+ String.format("%.3f", popUnpopRatio) + ";meanShape=" + String.format("%.3f", meanShape) + ";meanFreq="
				+ String.format("%.3f", meanFreq) + ";sdShape=" + String.format("%.3f", sdShape) + ";sdFreq=" + String.format("%.3f", sdFreq);
		return s;
	}

	private void initFreqs() {
		freqs = new int[config.getsMax()];
		for (int shape = 0; shape < config.getsMax(); shape++) {
			freqs[shape] = 0;
		}
	}

	private void initialize() {
		initPFreqs();
		findAll();
		calcAll();
	}

	private void initPFreqs() {
		pFreqs = new double[config.getsMax()];
		for (int i = 0; i < freqs.length; i++) {
			pFreqs[i] = (freqs[i] / (double) numEntries);
		}
	}

}
