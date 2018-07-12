package tw.lanyitin.zools.elements;

import java.util.List;

import tw.lanyitin.zools.Property;

public class StructElement extends Element {
	private final List<Property> properties;
	public StructElement(List<Property> properties) {
		this.properties = properties;
	}
	public Element getPropertyByName(String rValue) {
		for (Property property : this.properties) {
			if (property.getName().equals(rValue)) {
				return property.getValue();
			}
		}
		return null;
	}
	public List<Property> getProperties() {
		return this.properties;
	}

}
