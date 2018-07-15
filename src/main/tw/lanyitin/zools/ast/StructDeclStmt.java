package tw.lanyitin.zools.ast;

import java.util.HashMap;
import java.util.Map;

import tw.lanyitin.zools.runtime.Location;

public class StructDeclStmt extends ASTTree {
	private final String name;
	private final Map<String, String> properties;

	StructDeclStmt(String name, Location l) {
		super(l);
		this.name = name;
		this.properties = new HashMap<String, String>();
	}

	public void addProperty(String name, String type) {
		this.properties.put(name, type);
	}

	public String getName() {
		return name;
	}

	public Map<String, String> getProperties() {
		return properties;
	}
}
