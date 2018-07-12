package tw.lanyitin.zools.context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.ElementFactory;
import tw.lanyitin.zools.elements.StringElement;

public class RegexContext extends MappingContext {
	private final Pattern pattern;

	public RegexContext(Pattern pattern) {
		this.pattern = pattern;
	}
	
	public boolean match(Element element) {
		if (element instanceof StringElement) {
			StringElement target = (StringElement)element;
			Matcher matcher = this.pattern.matcher(target.getContent());
			return matcher.matches();
		} else {
			// TODO: might need to throw an exception
			return false;
		}
	}

	@Override
	public <T> T convert(Element element, ElementFactory<T> factory) {
		return factory.convert(element);
	}
}
