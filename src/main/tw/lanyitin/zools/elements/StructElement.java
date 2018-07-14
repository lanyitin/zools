package tw.lanyitin.zools.elements;

import java.util.List;

import tw.lanyitin.zools.runtime.Property;


public class StructElement extends Element {
	private final List<Property> properties;
	public StructElement(List<Property> properties) {
		this.properties = properties;
	}
	
	public List<Property> getProperties() {
		return this.properties;
	}

	public void putProperty(Property p) {
		this.properties.add(p);
	}
}
