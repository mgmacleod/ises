package ises.model.network;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import ises.Thing;

public class Edge extends Thing {

	protected Node regulator, target;
	protected double strength;
	protected boolean positive;

	public Edge(Node reg, Node targ, double stren, boolean pos) {
		regulator = reg;
		target = targ;
		strength = stren;
		positive = pos;
	}

	public Edge(Edge e) {
		this.regulator = e.regulator;
		this.target = e.target;
		this.strength = e.strength;
		this.positive = e.positive;
	}

	public void drawWith(Graphics2D g2d) {
		Color oldColour = g2d.getColor();
		int x = (int) (255 * (1.0 - strength));
		if (positive) {

			g2d.setColor(new Color(255, x, x));

		} else {
			g2d.setColor(new Color(x, x, 255));

		}

		g2d.setStroke(new BasicStroke(1.15f * (float) strength));

		if (regulator.equals(target)) {
			g2d.drawOval(regulator.getX(), regulator.getY(), 12, 12);
		}

		else {
			g2d.drawLine(regulator.getHillock().x, regulator.getHillock().y, target.getX(), target.getY());
		}
		g2d.setColor(oldColour);
		g2d.setStroke(new BasicStroke());
	}

	public boolean equals(Object o) {
		if (!(o instanceof Edge))
			return false;

		Edge e = (Edge) o;

		return (this.regulator.equals(e.regulator) && this.target.equals(e.target) && this.strength == e.strength
				&& this.positive == e.positive);

	}

	public double getStrength() {
		return strength;
	}

	public boolean isPositive() {
		return positive;
	}

	public Node getRegulator() {
		return regulator;
	}

	public void setRegulator(Node regulator) {
		this.regulator = regulator;
	}

	public Node getTarget() {
		return target;
	}

	public void setTarget(Node target) {
		this.target = target;
	}

	public void setStrength(double strength) {
		this.strength = strength;
	}

	public void setPositive(boolean positive) {
		this.positive = positive;
	}

}
