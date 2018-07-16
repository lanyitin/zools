package tw.lanyitin.zools.runtime.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.StructElement;
import tw.lanyitin.zools.runtime.Binding;
import tw.lanyitin.zools.runtime.CastSelector;
import tw.lanyitin.zools.runtime.Engine;
import tw.lanyitin.zools.runtime.Property;
import tw.lanyitin.zools.runtime.PropertySelector;
import tw.lanyitin.zools.runtime.ZoolsException;
import tw.lanyitin.zools.runtime.type.Struct;
import tw.lanyitin.zools.runtime.type.Type;

public class StructContext extends RuleContext {
	private final Struct struct;
	private final HashMap<PropertySelector, PropertySelector> fieldMapping;

	public StructContext(Struct struct) {
		super(struct);
		if (struct == null) {
			throw new RuntimeException();
		}
		this.struct = struct;
		this.fieldMapping = new HashMap<PropertySelector, PropertySelector>();
	}	
	
	public StructContext(Struct struct, List<Binding> bindings) {
		this(struct);
		this.setBindings(bindings);
	}

	public void setBindings(List<Binding> bindings) {
		for (Binding b : bindings) {
			fieldMapping.put(b.getName(), b.getQuery());
		}
	}
	
	public Map<PropertySelector, PropertySelector> getReversedBinding() {
		Map<PropertySelector, PropertySelector> result = new HashMap<PropertySelector, PropertySelector>();
		for(Entry<PropertySelector, PropertySelector> entry : this.fieldMapping.entrySet()) {
			result.put(entry.getValue(), entry.getKey());
		}
		return result;
	}
	
	public List<BindingContext> getBindingContexts(Engine env) throws ZoolsException {
		List<BindingContext> result = new ArrayList<BindingContext>();
		for (Entry<PropertySelector, Type> entry : this.struct.getProperties().entrySet()) {
			PropertySelector property = this.fieldMapping.getOrDefault(entry.getKey(), entry.getKey());
			RuleContext context = entry.getValue().generateContext();
			if (property instanceof CastSelector) {
				CastSelector cs = (CastSelector) property;
				if (cs.getTargetField() == null) {
					context = (StructContext) env.resolveRule(cs.getTargetRule());
				}
			}
			result.add(new BindingContext(property, context));
		}
		return result;
	}

	@Override
	public Element process(Element element, Engine env) throws ZoolsException {
		List<Property> ps = new ArrayList<Property>();
		List<ZoolsException> errors = new ArrayList<ZoolsException>();
		List<BindingContext> bindingContexts = this.getBindingContexts(env);
		for (BindingContext ctx : bindingContexts) {
			PropertySelector query = ctx.getQuery();
			RuleContext property_context = ctx.getContext();
			try {
				Element property = resolveQuery(element, query, env);
				Property p = new Property(
						this.getReversedBinding().getOrDefault(ctx.getQuery(), ctx.getQuery()).getResolvedName(),
						property_context.process(property, env));
				ps.add(p);
			} catch (ZoolsException e) {
				errors.add(new ZoolsException(
						String.format("'%s:%s'", this.struct.getName(), ctx.getQuery().toString()), Arrays.asList(e)));
			}
		}

		if (errors.size() > 0) {
			throw new ZoolsException("unable to match format of following properties", errors);
		}
		return new StructElement(this.struct, ps);
	}

	private Element resolveQuery(Element element, PropertySelector query, Engine env) throws ZoolsException {
		StructElement s = (StructElement) element;
		Element result = query.getResolveProerpty(s, env);
		if (result == null) {
			throw new ZoolsException(String.format("missing property: %s in %s", query.getResolvedName(), element.toString()));
		}
		return result;
	}
	
	@Override
	public String toString() {
		return String.format("%s{%s}", struct != null ? struct.getName() : "", this.getReversedBinding().entrySet().stream().map(x -> x.toString()).reduce( (a, b) -> a +", " + b).orElse(""));
	}

}
