package ises.model.network;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class GeneRegulatoryNetwork extends DefaultDirectedWeightedGraph<GrnVertex, DefaultWeightedEdge> {

	private static final long serialVersionUID = 5432328473075170472L;

	public GeneRegulatoryNetwork(Class<? extends DefaultWeightedEdge> edgeClass) {
		super(edgeClass);
	}

}
