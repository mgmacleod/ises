package ises;

public class Thing {

	public Thing() {
	}

	public int randInt(int max) {
		return (int) (Math.random() * max);
	}

	public int addNoise(int val, double factor) {
		double d = Math.random() * factor;
		if (Math.random() > 0.5) {
			d = -d;
		}

		d += val;

		return (int) Math.floor(d + 0.5);
	}

	public double random() {
		return Math.random();
	}

	public double random(double max) {
		return Math.random() * max;
	}

}
