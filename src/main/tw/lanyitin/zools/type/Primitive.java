package tw.lanyitin.zools.type;

import java.util.regex.Pattern;

import tw.lanyitin.zools.context.RegexContext;

public class Primitive extends Type {
	private final RegexContext context;

	public Primitive(Pattern pattern) {
		this.context = new RegexContext(pattern);
	}
	
	public RegexContext getContext() {
		return context;
	}
}
