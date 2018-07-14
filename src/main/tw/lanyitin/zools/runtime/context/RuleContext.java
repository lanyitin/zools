package tw.lanyitin.zools.runtime.context;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.runtime.Environment;

abstract public class RuleContext {
	abstract public boolean match(Element element, Environment env);
	abstract public Element convert(Element element, Environment env);
	public Element process(Element element, Environment env) {
		if (this.match(element, env)) {
			return this.convert(element, env);
		} else {
			return null;
		}
	}
}
