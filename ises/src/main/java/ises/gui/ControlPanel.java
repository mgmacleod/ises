package ises.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ControlPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTextArea gaStatus, modelStatus;
	private JButton goButton, stopButton, goOnceButton, resetButton;
	private ProgramFrame progFrame;
	private JCheckBox collDataBox;

	public ControlPanel(ProgramFrame c) {
		super();
		progFrame = c;

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(layout);

		JLabel label = new JLabel("Model Status");
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

		modelStatus = new JTextArea(4, 20);
		modelStatus.setEditable(false);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.gridheight = 3;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(12, 12, 3, 3);
		constraints.weightx = 10;
		constraints.weighty = 10;
		layout.setConstraints(modelStatus, constraints);
		add(modelStatus);

		label = new JLabel("GA Status");
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(12, 12, 3, 3);
		constraints.weightx = 10;
		constraints.weighty = 0;
		layout.setConstraints(label, constraints);
		add(label);

		gaStatus = new JTextArea(4, 20);
		gaStatus.setEditable(false);
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(12, 12, 3, 3);
		constraints.weightx = 10;
		constraints.weighty = 0;
		layout.setConstraints(gaStatus, constraints);
		add(gaStatus);

		goButton = new JButton("Go");
		constraints.gridx = 0;
		constraints.gridy = 8;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(12, 12, 3, 3);
		constraints.weightx = 10;
		constraints.weighty = 0;
		layout.setConstraints(goButton, constraints);
		add(goButton);

		stopButton = new JButton("Stop");
		constraints.gridx = 1;
		constraints.gridy = 8;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(12, 12, 3, 3);
		constraints.weightx = 10;
		constraints.weighty = 0;
		layout.setConstraints(stopButton, constraints);
		add(stopButton);

		goOnceButton = new JButton("Go once");
		constraints.gridx = 0;
		constraints.gridy = 9;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(12, 12, 3, 3);
		constraints.weightx = 10;
		constraints.weighty = 0;
		layout.setConstraints(goOnceButton, constraints);
		add(goOnceButton);

		resetButton = new JButton("Reset");
		constraints.gridx = 1;
		constraints.gridy = 9;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(12, 12, 3, 3);
		constraints.weightx = 10;
		constraints.weighty = 0;
		layout.setConstraints(resetButton, constraints);
		add(resetButton);

		collDataBox = new JCheckBox("Collect data?");
		collDataBox.setSelected(true);
		constraints.gridx = 1;
		constraints.gridy = 10;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(12, 12, 3, 3);
		constraints.weightx = 10;
		constraints.weighty = 0;
		layout.setConstraints(collDataBox, constraints);
		add(collDataBox);

		goButton.addActionListener(this);
		stopButton.addActionListener(this);
		goOnceButton.addActionListener(this);
		resetButton.addActionListener(this);
		collDataBox.addActionListener(this);
	}

	public void setGaAndSimStatus(String status) {
		gaStatus.setText(status);
		repaint();
	}

	public void setModelStatus(String status) {
		modelStatus.setText(status);
		repaint();
	}

	public boolean collectDataSelected() {
		return collDataBox.isSelected();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == goButton)
			progFrame.go();

		else if (e.getSource() == stopButton)
			progFrame.stop();

		else if (e.getSource() == goOnceButton)
			progFrame.goOnce();

		else if (e.getSource() == resetButton)
			progFrame.reset();

		else if (e.getSource() == collDataBox)
			progFrame.setCollectingData(collDataBox.isSelected());
	}

	public void reset() {
		modelStatus.setText("");
		gaStatus.setText("");
	}

}
