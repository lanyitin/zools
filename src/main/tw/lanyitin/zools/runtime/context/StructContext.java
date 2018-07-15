package tw.lanyitin.zools.runtime.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.StructElement;
import tw.lanyitin.zools.runtime.Environment;
import tw.lanyitin.zools.runtime.Property;
import tw.lanyitin.zools.runtime.PropertySelector;
import tw.lanyitin.zools.runtime.ZoolsException;
import tw.lanyitin.zools.runtime.type.Struct;

public class StructContext extends RuleContext {

	private final List<BindingContext> bindings;
	private final Map<String, String> fieldMapping;
	private final Struct struct;

	public StructContext(Struct struct, List<BindingContext> bindings) {
		super(struct);
		this.struct = struct;
		this.bindings = bindings;
		this.fieldMapping = new HashMap<String, String>();
	}

	public List<BindingContext> getBindingContexts() {
		return bindings;
	}

	@Override
	public Element process(Element element, Environment env) throws ZoolsException {
		List<Property> ps = new ArrayList<Property>();
		List<ZoolsException> errors = new ArrayList<ZoolsException>();

		for (BindingContext ctx : this.bindings) {
			PropertySelector query = ctx.getQuery();
			RuleContext property_context = ctx.getContext();
			Element property = resolveQuery(element, query, env);
			try {
				Property p = new Property(
						fieldMapping.getOrDefault(ctx.getQuery().getResolvedName(), ctx.getQuery().getResolvedName()),
						property_context.process(property, env));
				ps.add(p);
			} catch (ZoolsException e) {
				errors.add(new ZoolsException(
						String.format("'%s:%s'", this.struct.getName(), ctx.getQuery().toString()), Arrays.asList(e)));
			}
		}

		if (errors.size() > 0) {
			throw new ZoolsException("unable to match formaot of following properties", errors);
		}
		return new StructElement(this.struct, ps);
	}

	public void replaceFieldSelecor(final String name, PropertySelector query) {
		Optional<BindingContext> result = this.bindings.stream()
				.filter((BindingContext t) -> t.getQuery().getResolvedName().equals(name)).findFirst();
		if (result.isPresent()) {
			BindingContext ctx = result.get();
			ctx.setQuery(query);
			this.fieldMapping.put(query.getResolvedName(), name);
		}
	}

	private Element resolveQuery(Element element, PropertySelector query, Environment env) {
		StructElement s = (StructElement) element;
		return query.getResolveProerpty(s, env);
	}

}
