package jas.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import jas.model.network.GRN;

public class GRNPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GRN grn;

	public GRNPanel() {
		super();
		setBackground(Color.white);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (grn != null) {
			Graphics2D g2d = (Graphics2D) g;
			grn.drawWith(g2d);
		}
	}

	public void setGRN(GRN g) {
		grn = g;
		repaint();
	}

}
