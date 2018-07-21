package tw.lanyitin.zools.runtime;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.StructElement;

abstract public class PropertySelector {

	private final Boolean optional;

	public Boolean isOptional() {
		return optional;
	}

	public PropertySelector(Boolean isOptional) {
		this.optional = isOptional;
	}

	public abstract String getResolvedName();

	public abstract Element getResolveProerpty(StructElement s, Engine env);

	@Override
	abstract public String toString();

	@Override
	abstract public boolean equals(Object selector);

	@Override
	public abstract int hashCode();
}
