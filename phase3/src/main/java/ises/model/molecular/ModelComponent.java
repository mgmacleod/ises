package ises.model.molecular;

import ises.Thing;

/**
 * Base class for all components of a {@link ises.model.cellular.Model}
 */
public abstract class ModelComponent extends Thing {
	public String name;

	public ModelComponent() {

	}

	public void mutate() {

	}

	public void label(String s) {

	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
