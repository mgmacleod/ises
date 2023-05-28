package jas.gui;

import jas.model.network.GRN;

import java.awt.BorderLayout;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class UI extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private ControlPanel		controlPanel;
	private GRNPanel			grnPanel;
	//private ISES				ises;
	
	public UI() {
		this(null);
	}
	
	public UI(ProgramFrame pf) {
		super();
		controlPanel = new ControlPanel(pf);
		grnPanel = new GRNPanel();
		
		setLayout(new BorderLayout(2,2));
		
		add(BorderLayout.CENTER, grnPanel);
		add(BorderLayout.WEST, controlPanel);
		
		//ises = new ISES(this);
		
		
	}
	
	public void go() {
		//ises.goOnce();
	}
	
	public void stop() {
		//ises.stop();
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
