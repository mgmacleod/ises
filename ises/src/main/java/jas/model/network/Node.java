package jas.model.network;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import jas.Thing;

public class Node extends Thing implements Comparable<Node> {

	public static Rectangle bounds = null;

	protected String name;
	protected int x, y;
	protected Point hillock;
	protected int indegree, outdegree;

	public Node(String n) {
		name = n;
		indegree = 0;
		outdegree = 0;
		initLocation();
	}

	public void setLocation(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	public void incrementIndegree() {
		indegree++;
	}

	public void incrementOutdegree() {
		outdegree++;
	}

	public int getIndegree() {
		return indegree;
	}

	public void setIndegree(int indegree) {
		this.indegree = indegree;
	}

	public int getOutdegree() {
		return outdegree;
	}

	public void setOutdegree(int outdegree) {
		this.outdegree = outdegree;
	}

	public void initLocation() {

		// hideous!!!!!

		// input genes
		if (name.equals("fod1")) {
			x = 50;
			y = 45;
			hillock = new Point(x, y + 10);
		} else if (name.equals("fod2")) {
			x = 100;
			y = 55;
			hillock = new Point(x, y + 10);
		} else if (name.equals("fod3")) {
			x = 150;
			y = 45;
			hillock = new Point(x, y + 10);
		} else if (name.equals("fod4")) {
			x = 200;
			y = 55;
			hillock = new Point(x, y + 10);
		} else if (name.equals("fod5")) {
			x = 250;
			y = 45;
			hillock = new Point(x, y + 10);
		} else if (name.equals("fod6")) {
			x = 300;
			y = 55;
			hillock = new Point(x, y + 10);
		} else if (name.equals("fod7")) {
			x = 350;
			y = 45;
			hillock = new Point(x, y + 10);
		} else if (name.equals("fod8")) {
			x = 400;
			y = 55;
			hillock = new Point(x, y + 10);
		} else if (name.equals("fod9")) {
			x = 450;
			y = 45;
			hillock = new Point(x, y + 10);
		}

		else if (name.equals("nrg1")) {
			x = 500;
			y = 55;
			hillock = new Point(x, y + 10);

		} else if (name.equals("nrg2")) {
			x = 550;
			y = 45;
			hillock = new Point(x, y + 10);

		}

		else if (name.equals("rcp1")) {
			x = 600;
			y = 55;
			hillock = new Point(x, y + 10);
		} else if (name.equals("rcp2")) {
			x = 650;
			y = 45;
			hillock = new Point(x, y + 10);
		}

		// output genes
		else if (name.equals("syn1")) {
			x = 100;
			y = 450;
			hillock = new Point(x, y - 15);
		} else if (name.equals("syn2")) {
			x = 200;
			y = 470;
			hillock = new Point(x, y - 15);
		} else if (name.equals("syn3")) {
			x = 300;
			y = 430;
			hillock = new Point(x, y - 15);
		} else if (name.equals("syn4")) {
			x = 400;
			y = 460;
			hillock = new Point(x, y - 15);
		} else if (name.equals("rsp1")) {
			x = 500;
			y = 440;
			hillock = new Point(x, y - 15);
		} else if (name.equals("rsp2")) {
			x = 600;
			y = 470;
			hillock = new Point(x, y - 15);
		}

		// reg genes
		else {
			x = randInt(400) + 150;
			y = randInt(150) + 150;
			hillock = new Point(x, y + 10);
		}
	}

	public void drawWith(Graphics2D g2d) {
		Color oldColour = g2d.getColor();

		g2d.setColor(Color.yellow);
		g2d.fillOval(hillock.x, hillock.y, 8, 8);

		g2d.setColor(oldColour);
	}

	public boolean equals(Object o) {
		return this == o;
	}

	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Point getHillock() {
		return hillock;
	}

	public void setLocation(int anX, int aY) {
		x = anX;
		y = aY;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Integer getDegree() {
		return indegree + outdegree;
	}

	@Override
	public int compareTo(Node n) {

		return this.getDegree().compareTo(n.getDegree());
	}

}
