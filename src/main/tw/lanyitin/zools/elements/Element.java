package tw.lanyitin.zools.elements;

import tw.lanyitin.zools.runtime.type.Type;

public abstract class Element {
	private final Type type;

	public Element(Type type) {
		this.type = type;
	}

	public Type getType() {
		return this.type;
	}
	
	@Override
	public abstract String toString();
}
