package ises.model.network;

import java.awt.Color;
import java.awt.Graphics2D;

public class OutputNode extends Node {

	public OutputNode(String n) {
		super(n);
	}

	public void drawWith(Graphics2D g2d) {
		Color oldColor = g2d.getColor();

		g2d.setColor(Color.red.darker());

		g2d.fillRect(x - 5, y - 5, 10, 10);

		g2d.setColor(Color.black);
		g2d.drawRect(x - 5, y - 5, 10, 10);

		g2d.drawString(name, x - 10, y + 25);

		g2d.setColor(oldColor);
	}

}
