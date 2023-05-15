package ises.gui;

import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import ises.model.network.GRN;

public class GRNListPanel extends JPanel implements ListSelectionListener, ActionListener {

	private static final long serialVersionUID = 1L;

	protected JList<GRN> grnList;
	protected GRNViewer viewer;
	protected JButton saveButton;

	public GRNListPanel(Vector<GRN> grns, GRNViewer gv) {
		super();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(layout);

		viewer = gv;

		JLabel label = new JLabel("Sample GRNs:");
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(12, 12, 3, 3);
		constraints.weightx = 10;
		constraints.weighty = 0;
		layout.setConstraints(label, constraints);
		add(label);

		grnList = new JList<>(grns);
		JScrollPane grnScrollPane = new JScrollPane(grnList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.gridheight = 4;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(12, 12, 3, 3);
		constraints.weightx = 50;
		constraints.weighty = 50;
		layout.setConstraints(grnScrollPane, constraints);
		add(grnScrollPane);
		grnList.addListSelectionListener(this);

		saveButton = new JButton("Save image");
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(12, 12, 3, 3);
		constraints.weightx = 10;
		constraints.weighty = 0;
		layout.setConstraints(saveButton, constraints);
		add(saveButton);

		saveButton.setEnabled(false);
		saveButton.addActionListener(this);
	}

	public void valueChanged(ListSelectionEvent e) {
		GRN currGRN = (GRN) grnList.getSelectedValue();

		if (currGRN == null) {
			saveButton.setEnabled(false);
			return;
		}

		viewer.setCurrGRN(currGRN);
		if (!saveButton.isEnabled())
			saveButton.setEnabled(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == saveButton) {
			GRN grn = (GRN) grnList.getSelectedValue();

			if (grn == null) {
				return;
			}

			BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = image.createGraphics();
			grn.drawWith(g2d);

			String currentDirectoryProperty = System.getProperty("user.dir");
			File currentDirectory = new File(currentDirectoryProperty);

			File saveFile = new File(grn.getName() + ".png");
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG images", "png");
			chooser.setFileFilter(filter);
			chooser.setCurrentDirectory(currentDirectory);
			chooser.setSelectedFile(saveFile);
			int rval = chooser.showSaveDialog(this);
			if (rval == JFileChooser.APPROVE_OPTION) {
				saveFile = chooser.getSelectedFile();
				/*
				 * Write the filtered image in the selected format, to the file chosen by the
				 * user.
				 */
				try {
					ImageIO.write(image, "png", saveFile);
				} catch (IOException ex) {
				}
			}

		}
	}

}
