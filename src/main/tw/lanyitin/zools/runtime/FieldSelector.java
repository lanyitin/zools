package tw.lanyitin.zools.runtime;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.StructElement;

public class FieldSelector extends PropertySelector {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldSelector other = (FieldSelector) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	private final String name;

	public FieldSelector(String name, Boolean optional) {
		super(optional);
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
	public Element getResolveProerpty(StructElement s, Engine env) {
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
