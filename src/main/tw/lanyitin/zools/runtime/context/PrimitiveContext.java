package tw.lanyitin.zools.runtime.context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.PrimitiveElement;
import tw.lanyitin.zools.runtime.Environment;

public class PrimitiveContext extends RuleContext {
	private final Pattern pattern;

	public PrimitiveContext(Pattern pattern) {
		this.pattern = pattern;
	}
	
	public boolean match(Element element, Environment env) {
		if (element instanceof PrimitiveElement) {
			PrimitiveElement<?> target = (PrimitiveElement<?>)element;
			Matcher matcher = this.pattern.matcher(String.valueOf(target.getContent()));
			return matcher.matches();
		} else {
			throw new RuntimeException(String.format("expected as PrimitiveElement, but actually got: '%s'", element.getClass().getName()));
		}
	}

	@Override
	public Element convert(Element element, Environment env) {
		return element;
	}
}
