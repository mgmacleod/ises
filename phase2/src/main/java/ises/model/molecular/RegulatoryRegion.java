package ises.model.molecular;

import java.util.ArrayList;

import ises.rest.entities.SimulationConfiguration;

/**
 * Represents the abstract regulatory region of a {@link Gene}, consisting of a collection of {@link BindingSites}
 */
public class RegulatoryRegion extends ModelComponent {

	private ArrayList<BindingSite> sites;
	private Gene gene;
	private int regState;
	private SimulationConfiguration config;

	private RegulatoryRegion(SimulationConfiguration config) {
		this.config = config;
	}

	public RegulatoryRegion(Gene g, SimulationConfiguration config) {
		this(config);
		sites = new ArrayList<>();
		gene = g;
		regState = 0;

		name = gene.getName() + "RR";
		int r = randInt(4);

		for (int i = 0; i < r; i++) {
			sites.add(new BindingSite(gene, i, config));
		}

	}

	public RegulatoryRegion(RegulatoryRegion rr, Gene g, SimulationConfiguration config) {
		this(config);
		gene = g;
		sites = new ArrayList<>();

		for (BindingSite bs : rr.sites) {
			sites.add(new BindingSite(bs, g, config));
		}

	}

	public void addSite(BindingSite bs) {
		sites.add(bs);
	}

	public void removeSite(BindingSite bs) {
		if (sites.size() == 1) {
			return;
		}

		sites.remove(bs);
	}

	public int calcRegState() {
		regState = 0;

		for (BindingSite bs : sites) {
			regState += bs.regState();
		}

		return regState;
	}

	private void deleteSite(BindingSite bs) {
		if (sites.isEmpty()) {
			return;
		}

		if (Math.random() < config.getBindSiteDelProbability()) {
			sites.remove(bs);
		}
	}

	private void duplicateSite(BindingSite bs) {
		if (sites.isEmpty()) {

		}

		if (Math.random() < config.getBindSiteDupProbability()) {
			BindingSite dup = new BindingSite(bs, config);
			sites.add(dup);
		}

	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof RegulatoryRegion)) {
			return false;
		}

		return o == this;
	}

	public ArrayList<BindingSite> getSites() {
		return sites;
	}

	@Override
	public void label(String s) {
		for (int i = 0; i < sites.size(); i++) {
			BindingSite bs = sites.get(i);
			bs.label(s, i);
		}

	}

	public void labelSites() {
		label(gene.name);
	}

	@Override
	public void mutate() {
		if (sites.isEmpty() && random() < config.getBindSiteDupProbability()) {
			sites.add(new BindingSite(gene, 0, config));
			return;
		}

		ArrayList<BindingSite> sitesCopy = new ArrayList<>(sites);

		for (BindingSite bs : sitesCopy) {
			duplicateSite(bs);
			bs.mutate();
			deleteSite(bs);
		}
	}

	public void unbindAndDeactivate() {
		for (BindingSite bs : sites) {
			bs.unbindAndDeactivate();
		}

		regState = 0;
	}

}
