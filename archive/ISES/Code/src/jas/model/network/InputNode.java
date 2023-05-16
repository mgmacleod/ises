package jas.model.network;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class InputNode extends Node {

	
	
	public InputNode(String n) {
		super(n);
	}

	public void drawWith(Graphics2D g2d) {
		//super.drawWith(g);
		Color oldColor = g2d.getColor();
		
		g2d.setColor(Color.green.darker());
		
		g2d.fillOval(x-5, y-5, 10, 10);
		g2d.setColor(Color.black);
		g2d.drawOval(x-5, y-5, 10, 10);
		g2d.drawString(name, x-10, y-15);
		
		g2d.setColor(oldColor);
	}

}
