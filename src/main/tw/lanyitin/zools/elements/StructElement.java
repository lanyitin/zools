package tw.lanyitin.zools.elements;

import java.util.List;

import tw.lanyitin.zools.runtime.Property;
import tw.lanyitin.zools.runtime.type.Struct;

public class StructElement extends Element {
	private final List<Property> properties;
	private final Struct struct;

	public StructElement(Struct struct, List<Property> properties) {
		super(struct);
		this.properties = properties;
		this.struct = struct;
	}

	public List<Property> getProperties() {
		return this.properties;
	}

	public Struct getStruct() {
		return this.struct;
	}

	public void putProperty(Property p) {
		this.properties.add(p);
	}
}
