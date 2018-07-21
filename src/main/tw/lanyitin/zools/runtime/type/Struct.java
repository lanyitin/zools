package tw.lanyitin.zools.runtime.type;

import java.util.HashMap;
import java.util.Map;

import tw.lanyitin.zools.runtime.PropertySelector;
import tw.lanyitin.zools.runtime.context.RuleContext;
import tw.lanyitin.zools.runtime.context.StructContext;

public class Struct extends Type {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
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
		Struct other = (Struct) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		return true;
	}

	private final Map<PropertySelector, Type> properties;
	private final String name;

	public Struct(String name) {
		this.properties = new HashMap<PropertySelector, Type>();
		this.name = name;
	}

	public void addProperty(PropertySelector name, Type type) {
		this.properties.put(name, type);
	}

	public String getName() {
		return this.name;
	}

	public Map<PropertySelector, Type> getProperties() {
		return this.properties;
	}

	public Type getTypeOfProperty(PropertySelector key) {
		return properties.get(key);
	}

	public boolean hasProperty(PropertySelector key) {
		return properties.containsKey(key);
	}

	@Override
	public RuleContext generateContext() {
		return new StructContext(this);
	}

	@Override
	public String toString() {
		return String.format("Struct(%s)", this.getName());
	}
}
