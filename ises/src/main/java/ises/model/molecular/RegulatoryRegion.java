package ises.model.molecular;

import java.util.ArrayList;

import ises.sys.Params;

public class RegulatoryRegion extends ModelComponent {

	protected ArrayList<BindingSite> sites;
	protected Gene gene;
	protected int regState;

	public RegulatoryRegion() {
	}

	public RegulatoryRegion(Gene g) {
		sites = new ArrayList<BindingSite>();
		gene = g;
		regState = 0;

		name = gene.getName() + "RR";
		int r = randInt(4);

		for (int i = 0; i < r; i++)
			sites.add(new BindingSite(gene, i));

	}

	public RegulatoryRegion(RegulatoryRegion rr, Gene g) {
		gene = g;
		sites = new ArrayList<BindingSite>();

		for (BindingSite bs : rr.sites)
			this.sites.add(new BindingSite(bs, g));

	}

	public void addSite(BindingSite bs) {
		sites.add(bs);
	}

	public void removeSite(BindingSite bs) {
		if (sites.size() == 1)
			return;

		sites.remove(bs);
	}

	public int calcRegState() {
		regState = 0;

		for (BindingSite bs : sites)
			regState += bs.regState();

		return regState;
	}

	public void deleteSite(BindingSite bs) {
		if (sites.isEmpty())
			return;

		if (Math.random() < Params.mDelBS)
			sites.remove(bs);
	}

	public void duplicateSite(BindingSite bs) {
		if (sites.isEmpty()) {

		}

		if (Math.random() < Params.mDupBS) {
			BindingSite dup = new BindingSite(bs);
			sites.add(dup);
		}

	}

	public boolean equals(Object o) {
		if (!(o instanceof RegulatoryRegion))
			return false;

		return o == this;
	}

	/**
	 * @return the gene
	 */
	public Gene getGene() {
		return gene;
	}

	/**
	 * @return the regState
	 */
	public int getRegState() {
		return regState;
	}

	/**
	 * @return the sites
	 */
	public ArrayList<BindingSite> getSites() {
		return sites;
	}

	public void label(String s) {
		for (int i = 0; i < sites.size(); i++) {
			BindingSite bs = sites.get(i);
			bs.label(s, i);
		}

	}

	public void labelSites() {
		label(gene.name);
	}

	public void mutate() {

		if (sites.isEmpty() && random() < Params.mDupBS) {
			sites.add(new BindingSite(gene, 0));
			return;
		}

		ArrayList<BindingSite> sitesCopy = new ArrayList<BindingSite>(sites);

		for (BindingSite bs : sitesCopy) {
			duplicateSite(bs);
			bs.mutate();
			deleteSite(bs);
		}
	}

	/**
	 * @param gene the gene to set
	 */
	public void setGene(Gene gene) {
		this.gene = gene;
	}

	/**
	 * @param regState the regState to set
	 */
	public void setRegState(int regState) {
		this.regState = regState;
	}

	/**
	 * @param sites the sites to set
	 */
	public void setSites(ArrayList<BindingSite> sites) {
		this.sites = sites;
	}

	public void unbindAndDeactivate() {
		for (BindingSite bs : sites)
			bs.unbindAndDeactivate();

		regState = 0;
	}

}
