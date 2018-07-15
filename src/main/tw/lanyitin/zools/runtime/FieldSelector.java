package tw.lanyitin.zools.runtime;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.StructElement;

public class FieldSelector extends PropertySelector {
	private final String name;

	public FieldSelector(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String getResolvedName() {
		return name;
	}

	@Override
	public Element getResolveProerpty(StructElement s, Environment env) {
		for (Property entry : s.getProperties()) {
			if (entry.getName().equals(this.name)) {
				return entry.getValue();
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format("%s", name);
	}
}
