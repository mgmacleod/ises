package ises.gui;

import java.awt.BorderLayout;
import java.awt.Rectangle;

import javax.swing.JPanel;

import ises.model.network.GRN;

public class UI extends JPanel {
	private static final long serialVersionUID = 1L;

	private ControlPanel controlPanel;
	private GRNPanel grnPanel;

	public UI() {
		this(null);
	}

	public UI(ProgramFrame pf) {
		super();
		controlPanel = new ControlPanel(pf);
		grnPanel = new GRNPanel();

		setLayout(new BorderLayout(2, 2));

		add(BorderLayout.CENTER, grnPanel);
		add(BorderLayout.WEST, controlPanel);
	}
	
	public void updateGaSimStatus(String status) {
		controlPanel.setGaAndSimStatus(status);
		repaint();
	}

	public boolean collectDataSelected() {
		return controlPanel.collectDataSelected();
	}

	public void updateModelStatus(String status) {
		controlPanel.setModelStatus(status);
		repaint();
	}

	public Rectangle getCPBounds() {
		return controlPanel.getBounds();
	}

	public void draw(GRN grn) {
		grnPanel.setGRN(grn);
	}

	public void reset() {
		controlPanel.reset();
		draw(null);
	}

}
