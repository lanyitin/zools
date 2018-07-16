package tw.lanyitin.zools.runtime.context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.PrimitiveElement;
import tw.lanyitin.zools.runtime.Engine;
import tw.lanyitin.zools.runtime.ZoolsException;
import tw.lanyitin.zools.runtime.type.Primitive;

public class PrimitiveContext extends RuleContext {
	private final Pattern pattern;

	public PrimitiveContext(Primitive type) {
		super(type);
		this.pattern = type.getPattern();
	}

	@Override
	public Element process(Element element, Engine env) throws ZoolsException {
		if (!(element instanceof PrimitiveElement)) {
			throw new ZoolsException(String.format("expected as PrimitiveElement, but actually got: '%s'",
					element.getClass().getName()));
		}

		PrimitiveElement<?> target = (PrimitiveElement<?>) element;
		Matcher matcher = this.pattern.matcher(String.valueOf(target.getContent()));
		if (matcher.matches()) {
			return element;
		} else {
			throw new ZoolsException(String.format("mismatch regular expression: '%s', '%s'", this.pattern.pattern(),
					target.getContent()));
		}
	}
	
	@Override
	public String toString() {
		return String.format("%s(%s)", getType().getName(), pattern.pattern());
	}
}
