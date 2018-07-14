package tw.lanyitin.zools.runtime.context;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.runtime.Environment;
import tw.lanyitin.zools.runtime.PropertySelector;

public class BindingContext extends RuleContext {
	private PropertySelector query;
	private RuleContext property_context;

	public BindingContext(PropertySelector query, RuleContext context) {
		this.query = query;
		this.property_context = context;
	}
	@Override
	public boolean match(Element element, Environment env) {
		return property_context.match(element, env);
	}

	@Override
	public Element convert(Element element, Environment env) {
		return property_context.convert(element, env);
	}
	public PropertySelector getQuery() {
		return query;
	}
	public void setQuery(PropertySelector query2) {
		this.query = query2;
	}
	public void setContext(RuleContext context) {
		this.property_context = context;
	}
	public RuleContext getContext() {
		return property_context;
	}

}
