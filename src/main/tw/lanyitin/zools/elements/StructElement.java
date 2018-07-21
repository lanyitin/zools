package tw.lanyitin.zools.elements;

import java.util.List;

import tw.lanyitin.zools.runtime.Property;
import tw.lanyitin.zools.runtime.type.Struct;

public class StructElement extends Element {
	private final List<Property> properties;
	private final Struct struct;

	public StructElement(Struct struct, List<Property> properties) {
		super(struct);
		// if (struct == null) {
		// throw new IllegalArgumentException("struct shouldn't be null");
		// }
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

	@Override
	public String toString() {
		return String.format("{%s}", this.properties.stream().map(x -> x.getName() + ":" + x.getValue().toString())
				.reduce((a, b) -> a + ", " + b).orElse(""));
	}
}
