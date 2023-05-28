package jas.gui;

import jas.model.network.GRN;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.UIManager;

public class GRNViewer extends JDialog {

	private static final long serialVersionUID = 1L;
	
	protected GRNListPanel			grnList;
	protected GRNPanel				grnPanel;
	//protected GRNPropPanel			grnProps;
	
	public GRNViewer(Vector<GRN> grns) {
		super();
		setModal(false);
		setTitle("GRN Viewer");
		
		setLayout(new BorderLayout(2,2));
		
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (Exception e) {}
        
        grnPanel = new GRNPanel();
        grnList = new GRNListPanel(grns, this);
        //grnProps = new GRNPropPanel();
        
        add(BorderLayout.WEST, grnList);
        add(BorderLayout.CENTER, grnPanel);
        //add(BorderLayout.EAST, grnProps);
        
        Rectangle bounds = getGraphicsConfiguration().getBounds();
		setLocation(bounds.x, bounds.y + 25);
		setSize(bounds.width, bounds.height - 25);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void setCurrGRN(GRN grn) {
		grnPanel.setGRN(grn);
	}
	
	
	public void setGRNPropText(String s) {
		//grnProps.setText(s);
	}
	

}
