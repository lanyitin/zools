package tw.lanyitin.zools.context;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import tw.lanyitin.zools.Binding;
import tw.lanyitin.zools.Property;
import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.ElementFactory;
import tw.lanyitin.zools.elements.StructElement;
import tw.lanyitin.zools.type.Struct;

public class StructContext extends MappingContext {

	private final List<Binding> bindings;
	private final Struct type;

	public StructContext(Struct type, List<Binding> bindings) {
		this.type = type;
		this.bindings = bindings;
	}
	
	public void replaceBinding(String name, Binding binding) {
		List<String> keys = bindings.stream().map(new Function<Binding, String>(){
			@Override
			public String apply(Binding arg0) {
				return arg0.getLValue();
			}
		}).collect(Collectors.toCollection(new Supplier<List<String>>(){
			@Override
			public List<String> get() {
				return new ArrayList<String>();
			}
		}));
		int idx = keys.indexOf(name);
		bindings.set(idx,  binding);
	}

	@Override
	public boolean match(Element element) {
		if (element instanceof StructElement) {
		
			final StructElement target = (StructElement) element;		
			return bindings.stream().map(new Function<Binding, Boolean>() {
				public Boolean apply(Binding binding) {
					MappingContext propertyContext = binding.getContext();
					Element child = target.getPropertyByName(binding.getRValue());
					return propertyContext.match(child);
				}
			}).reduce(true, new BinaryOperator<Boolean> () {
				public Boolean apply(Boolean arg0, Boolean arg1) {
					return arg0 && arg1;
				}
			});
		} else {
			return false;
		}
	}

	@Override
	public <T> T convert(Element element, ElementFactory<T> factory) {
		if (element instanceof StructElement) {
			final StructElement target = (StructElement) element;		
			final List<Property> new_bindings = new ArrayList<Property>();
			bindings.stream().forEach(new Consumer<Binding>() {
				public void accept(Binding binding) {
					Element child = target.getPropertyByName(binding.getRValue());
					new_bindings.add(new Property(binding.getLValue(), child));
				}
			});
			return factory.convert(new StructElement(new_bindings));
		} else {
			return null;
		}
	}

}
