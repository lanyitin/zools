package tw.lanyitin.zools.runtime;

import tw.lanyitin.zools.elements.Element;

public class Property {
	private final String name;
	private final Element value;

	public Property(String name, Element element) {
		this.name = name;
		this.value = element;
	}

	public String getName() {
		return this.name;
	}

	public Element getValue() {
		return this.value;
	}
}
