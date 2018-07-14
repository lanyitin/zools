package tw.lanyitin.zools.runtime.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.StructElement;
import tw.lanyitin.zools.runtime.Environment;
import tw.lanyitin.zools.runtime.Property;
import tw.lanyitin.zools.runtime.PropertySelector;


public class StructContext extends RuleContext {

	private final List<BindingContext> bindings;
	private final Map<String, String> fieldMapping;

	public StructContext(List<BindingContext> bindings) {
		this.bindings = bindings;
		this.fieldMapping = new HashMap<String, String>();
	}

	@Override
	public boolean match(Element element, Environment env) {
		for (BindingContext ctx : this.bindings) {
			PropertySelector query = ctx.getQuery();
			Element property = resolveQuery(element, query, env);
			if (ctx.match(property, env) == false) {
				return false;
			}
		}
		return true;
	}

	private Element resolveQuery(Element element, PropertySelector query, Environment env) {
		StructElement s = (StructElement) element;
		return query.getResolveProerpty(s, env);
	}

	@Override
	public Element convert(Element element, Environment env) {
		List<Property> ps = new ArrayList<Property>();
		for (BindingContext ctx : this.bindings) {
			PropertySelector query = ctx.getQuery();
			RuleContext property_context = ctx.getContext();
			Element property = resolveQuery(element, query, env);
			if (ctx.match(property, env) == false) {
				return null;
			} else {
				Property p = new Property(fieldMapping.getOrDefault(ctx.getQuery().getResolvedName(), ctx.getQuery().getResolvedName()), property_context.convert(property, env));
				ps.add(p);
			}
		}
		return new StructElement(ps);
	}

	public List<BindingContext> getBindingContexts() {
		return bindings;
	}
	
	public void replaceFieldSelecor(final String name, PropertySelector query) {
		Optional<BindingContext> result = this.bindings.stream().filter(new Predicate<BindingContext>() {
			public boolean test(BindingContext t) {
				return t.getQuery().getResolvedName().equals(name);
			}
		}).findFirst();
		
		if (result.isPresent()) {
			BindingContext ctx = result.get();
			ctx.setQuery(query);
			this.fieldMapping.put(query.getResolvedName(), name);
		}
	}

}
