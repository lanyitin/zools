package tw.lanyitin.zools.runtime.type;

import java.util.regex.Pattern;

public class Primitive extends Type {
	private final Pattern pattern;
	private final String name;

	public Primitive(String name, Pattern pattern) {
		this.pattern = pattern;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Pattern getPattern() {
		return pattern;
	}
}
