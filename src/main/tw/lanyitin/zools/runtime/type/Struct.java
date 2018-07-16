package tw.lanyitin.zools.runtime.type;

import java.util.HashMap;
import java.util.Map;

import tw.lanyitin.zools.runtime.PropertySelector;
import tw.lanyitin.zools.runtime.context.RuleContext;
import tw.lanyitin.zools.runtime.context.StructContext;

public class Struct extends Type {
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
}
