package ises.model.network;

import java.util.Objects;

public class GrnVertex {

	private final String name;
	private final GrnVertexType type;

	public GrnVertex(String name, GrnVertexType type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public GrnVertexType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GrnVertex other = (GrnVertex) obj;
		return Objects.equals(name, other.name) && type == other.type;
	}

	@Override
	public String toString() {
		return "GrnVertex [name=" + name + ", type=" + type + "]";
	}

}
