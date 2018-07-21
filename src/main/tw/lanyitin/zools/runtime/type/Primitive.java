package tw.lanyitin.zools.runtime.type;

import java.util.regex.Pattern;

import tw.lanyitin.zools.runtime.context.PrimitiveContext;
import tw.lanyitin.zools.runtime.context.RuleContext;

public class Primitive extends Type {
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
		Primitive other = (Primitive) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

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

	@Override
	public RuleContext generateContext() {
		return new PrimitiveContext(this);
	}

	@Override
	public String toString() {
		return String.format("Primitive(%s)", this.getName());
	}
}
