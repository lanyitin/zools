package tw.lanyitin.zools.runtime.type;

import java.util.HashMap;
import java.util.Map;

public class Struct extends Type {
	private final Map<String, Type> properties;
	private final String name;

	public Struct(String name) {
		this(name, new HashMap<String, Type>());
	}

	private Struct(String name, Map<String, Type> properties) {
		this.properties = properties;
		this.name = name;
	}

	public void addProperty(String name, Type type) {
		this.properties.put(name, type);
	}

	public String getName() {
		return this.name;
	}

	public Map<String, Type> getProperties() {
		return this.properties;
	}

	public Type getTypeOfProperty(String key) {
		return properties.get(key);
	}

	public boolean hasProperty(String key) {
		return properties.containsKey(key);
	}
}
