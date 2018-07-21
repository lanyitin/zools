package tw.lanyitin.zools.runtime.context;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.runtime.Engine;
import tw.lanyitin.zools.runtime.PropertySelector;
import tw.lanyitin.zools.runtime.ZoolsException;

public class BindingContext extends RuleContext {
	private PropertySelector query;
	private RuleContext property_context;

	public BindingContext(PropertySelector query, RuleContext context) {
		super(context.getType());
		this.query = query;
		this.property_context = context;
	}

	public RuleContext getContext() {
		return property_context;
	}

	public PropertySelector getQuery() {
		return query;
	}

	@Override
	public Element process(Element element, Engine env) throws ZoolsException {
		return property_context.process(element, env);
	}

	public void setQuery(PropertySelector query2) {
		this.query = query2;
	}

	@Override
	public String toString() {
		return String.format("%s = %s", property_context.toString(), query.toString());
	}

}
