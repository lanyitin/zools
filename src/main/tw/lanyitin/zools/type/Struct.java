package tw.lanyitin.zools.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import tw.lanyitin.zools.Binding;
import tw.lanyitin.zools.context.MappingContext;
import tw.lanyitin.zools.context.StructContext;

public class Struct extends Type {
	private final Map<String, Type> properties;

	public Struct() {
		this(new HashMap<String, Type>());
	}
	
	public Struct(Map<String, Type> properties) {
		this.properties = properties;
	}
	
	public Map<String, Type> getProperties() {
		return this.properties;
	}
	
	public StructContext getContext() {
		final List<Binding> bindings = new ArrayList<Binding>();
		this.properties.entrySet().stream().forEach(new Consumer<Entry<String, Type>>() {
			@Override
			public void accept(Entry<String, Type> arg0) {
				bindings.add(new Binding(arg0.getKey(), arg0.getKey(), arg0.getValue().getContext()));
			}
		});
		return new StructContext(this, bindings);
	}
}
