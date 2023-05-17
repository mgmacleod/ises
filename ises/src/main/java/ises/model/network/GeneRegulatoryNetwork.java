package ises.model.network;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Provides a network representation of the interactions between genes in a model
 */
public class GeneRegulatoryNetwork extends DefaultDirectedWeightedGraph<GrnVertex, DefaultWeightedEdge> {

	private static final long serialVersionUID = 5432328473075170472L;

	private String name;

	public GeneRegulatoryNetwork() {
		super(DefaultWeightedEdge.class);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
