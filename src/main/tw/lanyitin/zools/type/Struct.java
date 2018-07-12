package tw.lanyitin.zools.type;

import java.util.HashMap;
import java.util.Map;

public class Struct extends Type {
	private final Map<String, Primitive> properties;

	public Struct() {
		this(new HashMap<String, Primitive>());
	}
	
	public Struct(Map<String, Primitive> properties) {
		this.properties = properties;
	}
	
	public Map<String, Primitive> getProperties() {
		return this.properties;
	}
}
