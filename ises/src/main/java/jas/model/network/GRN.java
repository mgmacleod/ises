package jas.model.network;

import java.awt.Graphics2D;
import java.util.Collections;
import java.util.LinkedList;

import jas.Thing;

public class GRN extends Thing {

	protected LinkedList<Node> nodes;
	protected LinkedList<Edge> edges;
	protected String name;
	protected double avgDegree;
	protected Node highestDegree, lowestDegree, highestIndegree, highestOutdegree;

	public GRN() {
		nodes = new LinkedList<Node>();
		edges = new LinkedList<Edge>();
	}

	public GRN(GRN parent) {
		this.nodes = new LinkedList<Node>(parent.nodes);
		this.edges = new LinkedList<Edge>();

		for (Edge e : parent.edges) {
			this.edges.add(new Edge(e));

		}
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public String toString() {
		return name;
	}

	public int getNumEdges() {
		return edges.size();
	}

	public void addNode(Node n) {
		if (nodes.contains(n))
			return;

		nodes.add(n);
	}

	public void addEdge(Edge e) {
		if (edges.contains(e)) {
			return;
		}
		edges.add(e);
	}

	public void drawWith(Graphics2D g2d) {
		for (Edge e : edges)
			e.drawWith(g2d);

		for (Node n : nodes)
			n.drawWith(g2d);

	}

	public void calcDegrees() {
		for (Edge e : edges) {
			e.getRegulator().incrementOutdegree();
			e.getTarget().incrementIndegree();
		}

		Collections.sort(nodes);

		highestDegree = nodes.getLast();
		lowestDegree = nodes.getFirst();
		highestIndegree = findHighestIndegree();
		highestOutdegree = findHighestOutdegree();

		int sum = 0;

		for (Node n : nodes)
			sum += n.getDegree();

		double nNodes = (double) nodes.size();
		avgDegree = sum / nNodes;
	}

	public Node findHighestIndegree() {
		Node hid = nodes.getFirst();

		for (Node n : nodes) {
			if (n.getIndegree() > hid.getIndegree())
				hid = n;
		}

		return hid;
	}

	public Node findHighestOutdegree() {
		Node hod = nodes.getFirst();

		for (Node n : nodes) {
			if (n.getOutdegree() > hod.getOutdegree())
				hod = n;
		}

		return hod;
	}

	public String getPropString() {
		if (highestDegree == null)
			calcDegrees();

		return "Average degree: " + avgDegree + "\n" + "Highest degree: " + highestDegree.getDegree() + " ("
				+ highestDegree.getName() + ")\n" + "Lowest degree: " + lowestDegree.getDegree() + " ("
				+ lowestDegree.getName() + ")\n" + "Highest indegree: " + highestIndegree.getIndegree() + " ("
				+ highestIndegree.getName() + ")\n" + "Highest outdegree: " + highestOutdegree.getIndegree() + " ("
				+ highestOutdegree.getName() + ")\n";
	}

}
