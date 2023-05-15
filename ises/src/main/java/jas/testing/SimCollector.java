package jas.testing;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jas.stats.ComponentData;
import jas.stats.DataCollector;
import jas.stats.SimData;

public class SimCollector extends DataCollector {
	public int numGenes, numSitesGenome, numSitesGenes, fitness;

	public SimCollector() {
	}

	public void writeToFile() {
		ArrayList<Integer> mEnergies = new ArrayList<Integer>(data.size());
		ArrayList<Integer> mStresses = new ArrayList<Integer>(data.size());
		ArrayList<Integer> mBiomasses = new ArrayList<Integer>(data.size());
		ArrayList<Integer> sFood1s = new ArrayList<Integer>(data.size());
		ArrayList<Integer> sFood2s = new ArrayList<Integer>(data.size());
		ArrayList<Integer> sFood3s = new ArrayList<Integer>(data.size());
		ArrayList<Integer> sEnergies = new ArrayList<Integer>(data.size());
		ArrayList<Integer> sStresses = new ArrayList<Integer>(data.size());
		ArrayList<Integer> mNumProteins = new ArrayList<Integer>(data.size());
		ArrayList<Integer> mNumBoundProteins = new ArrayList<Integer>(data.size());

		for (ComponentData cd : data) {
			SimData sd = (SimData) cd;

			mEnergies.add(sd.getmEnergy());
			mStresses.add(sd.getmStress());
			mBiomasses.add(sd.getmBiomass());
			sFood1s.add(sd.getsFood1());
			sFood2s.add(sd.getsFood2());
			sFood3s.add(sd.getsFood3());
			sEnergies.add(sd.getsEnergy());
			sStresses.add(sd.getsStress());
			mNumProteins.add(sd.getNumProteins());
			mNumBoundProteins.add(sd.getNumBoundProteins());
		}

		try {
			PrintWriter outputFile = new PrintWriter(new FileWriter("mEnergies.txt"));
			for (Integer i : mEnergies)
				outputFile.println(i);

			outputFile.close();

			outputFile = new PrintWriter(new FileWriter("mStresses.txt"));
			for (Integer i : mStresses)
				outputFile.println(i);

			outputFile.close();

			outputFile = new PrintWriter(new FileWriter("mBiomasses.txt"));
			for (Integer i : mBiomasses)
				outputFile.println(i);

			outputFile.close();

			outputFile = new PrintWriter(new FileWriter("sFood1s.txt"));
			for (Integer i : sFood1s)
				outputFile.println(i);

			outputFile.close();

			outputFile = new PrintWriter(new FileWriter("sFood2s.txt"));
			for (Integer i : sFood2s)
				outputFile.println(i);

			outputFile.close();

			outputFile = new PrintWriter(new FileWriter("sFood3s.txt"));
			for (Integer i : sFood3s)
				outputFile.println(i);

			outputFile.close();

			outputFile = new PrintWriter(new FileWriter("sStresses.txt"));
			for (Integer i : sStresses)
				outputFile.println(i);

			outputFile.close();

			outputFile = new PrintWriter(new FileWriter("sEnergies.txt"));
			for (Integer i : sEnergies)
				outputFile.println(i);

			outputFile.close();

			outputFile = new PrintWriter(new FileWriter("mNumProteins.txt"));
			for (Integer i : mNumProteins)
				outputFile.println(i);

			outputFile.close();

			outputFile = new PrintWriter(new FileWriter("mNumBoundProteins.txt"));
			for (Integer i : mNumBoundProteins)
				outputFile.println(i);

			outputFile.close();

			outputFile = new PrintWriter(new FileWriter("sGeneral.txt"));

			outputFile.println("numGenes: " + numGenes + "; nsGenome: " + numSitesGenome + "; nsGenes: " + numSitesGenes
					+ "; fitness: " + fitness);

			outputFile.close();

		} catch (IOException e) {
			e.printStackTrace();
			print("fuck!");
		}
	}

}
