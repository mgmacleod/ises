package jas.stats;

import java.util.LinkedList;

import jas.Thing;

public class DataCollector extends Thing {

	public LinkedList<ComponentData> data;

	public DataCollector() {
		data = new LinkedList<ComponentData>();
	}

	public void writeToFile() {

	}

	public void addData(ComponentData cd) {
		data.add(cd);
	}

}
