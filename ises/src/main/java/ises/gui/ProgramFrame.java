package ises.gui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import javax.swing.UIManager;

import ises.sys.ISES;
import ises.sys.Params;

public class ProgramFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	protected ISES ises;
	protected UI ui;
	protected boolean repeating, collectingData;
	protected JMenu menu, constFood, varFood;
	protected JMenuBar menuBar;
	protected JMenuItem cfEasy, cfMedium, vfEasy, vfMedium;

	protected Timer timer;

	public ProgramFrame(ISES i, UI u) {
		ises = i;
		ui = u;
		repeating = false;
		collectingData = true;
	}

	public ProgramFrame() {
		super("GRN simulation and evolution");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		setLayout(new BorderLayout(2, 2));

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menu = new JMenu("Presets");
		constFood = new JMenu("Constant food probabilities");
		cfEasy = new JMenuItem("Easy");
		cfMedium = new JMenuItem("Medium");
		constFood.add(cfEasy);
		constFood.add(cfMedium);
		menu.add(constFood);

		varFood = new JMenu("Variable food probabilities");
		vfEasy = new JMenuItem("Easy");
		vfMedium = new JMenuItem("Medium");
		varFood.add(vfEasy);
		varFood.add(vfMedium);
		menu.add(varFood);

		menuBar.add(menu);

		cfEasy.addActionListener(this);
		cfMedium.addActionListener(this);
		vfEasy.addActionListener(this);
		vfMedium.addActionListener(this);

		ises = new ISES(this, false);
		ui = new UI(this);
		timer = new Timer(20, this);
		collectingData = false;

		add(BorderLayout.CENTER, ui);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Rectangle bounds = getGraphicsConfiguration().getBounds();
		setLocation(bounds.x, bounds.y + 25);
		setSize(bounds.width, bounds.height - 25);

		setVisible(true);

	}

	public void go() {
		repeating = true;
		timer.start();
	}

	public void goOnce() {
		repeating = false;
		timer.start();
	}

	public void stop() {
		timer.stop();
		repeating = false;

	}

	public void reset() {
		timer.stop();
		repeating = false;
		ises = new ISES(this, ui.collectDataSelected());
		ui.reset();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			timer.stop();
			run();
		}

		else if (e.getSource() == cfEasy) {
			Params.initEasy();
			reset();
		}

		else if (e.getSource() == cfMedium) {
			Params.initMedium();
			reset();
		}

		else if (e.getSource() == vfEasy) {
			Params.initEasyFlip();
			reset();
		}

		else if (e.getSource() == vfMedium) {
			Params.initMediumFlip();
			reset();
		}
	}

	public boolean isCollectingData() {
		return collectingData;
	}

	public void setCollectingData(boolean cd) {

		this.collectingData = cd;
		ises.setCollectingData(cd);
	}

	public void run() {
		ui.updateGaSimStatus("GA running generation " + ises.getGa().getGen() + "...");
		ises.simulatePop();

		ises.nextGen();
		String modelStatus = ises.getModelStatus();

		ui.draw(ises.getCurrGRN());
		ui.updateModelStatus(modelStatus);

		if (repeating) {
			if (!ises.isDone())
				timer.start();
		}

		if (ises.isDone()) {
			ui.updateGaSimStatus("GA running generation " + (ises.getGa().getGen() - 1) + "...\nGA done.");

			if (collectingData) {
				ises.prepareData();

				new GRNViewer(ises.getSampleGRNs());

			}
		}

	}

	public UI getUI() {
		return ui;
	}

}
