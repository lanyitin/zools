package tw.lanyitin.zools.ast;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import tw.lanyitin.zools.runtime.Location;

public class StructDeclStmt extends ASTTree {
	private final String name;
	private final Map<String, Pair<String, Boolean>> properties;

	StructDeclStmt(String name, Location l) {
		super(l);
		this.name = name;
		this.properties = new HashMap<String, Pair<String, Boolean>>();
	}

	public void addProperty(String name, String type, Boolean isOptional) {
		this.properties.put(name, Pair.of(type, isOptional));
	}

	public String getName() {
		return name;
	}

	public Map<String, Pair<String, Boolean>> getProperties() {
		return properties;
	}

	public Pair<String, Boolean> resolveProperty(String name) {
		if (this.properties.containsKey(name)) {
			return this.properties.get(name);
		} else {
			throw new RuntimeException(String.format("unable to find property \"%s\"", name));
		}
	}
}
