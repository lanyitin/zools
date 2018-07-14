package tw.lanyitin.zools.ast;

import java.util.HashMap;
import java.util.Map;

public class StructDeclStmt extends ASTTree {
	private final String name;
	private final Map<String, String> properties;
	
	public String getName() {
		return name;
	}

	StructDeclStmt(String name) {
		this.name = name;
		this.properties = new HashMap<String, String>();
	}
	
	public void addProperty(String name, String type) {
		this.properties.put(name, type);
	}

	public Map<String, String> getProperties() {
		return properties;
	}
}
