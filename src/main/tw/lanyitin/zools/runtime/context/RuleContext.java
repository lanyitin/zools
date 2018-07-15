package tw.lanyitin.zools.runtime.context;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.runtime.Environment;
import tw.lanyitin.zools.runtime.ZoolsException;
import tw.lanyitin.zools.runtime.type.Type;

abstract public class RuleContext {
	private final Type type;

	public RuleContext(Type type) {
		this.type = type;
	}

	public Type getType() {
		return this.type;
	}

	abstract public Element process(Element element, Environment env) throws ZoolsException;
}
