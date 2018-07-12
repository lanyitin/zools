package tw.lanyitin.zools.type;

import tw.lanyitin.zools.context.RegexContext;

public class Primitive extends Type {
	private final RegexContext context;

	public Primitive(RegexContext context) {
		this.context = context;
	}
	
	public RegexContext getContext() {
		return context;
	}
}
