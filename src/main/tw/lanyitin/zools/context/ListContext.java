package tw.lanyitin.zools.context;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;

import tw.lanyitin.zools.Binding;
import tw.lanyitin.zools.Property;
import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.ElementFactory;
import tw.lanyitin.zools.elements.ListElement;
import tw.lanyitin.zools.elements.StructElement;

public class ListContext extends MappingContext {
	private MappingContext child_context;

	public ListContext(MappingContext child_context) {
		this.child_context = child_context;
	}

	@Override
	public boolean match(Element element) {
		if (element instanceof ListElement) {
			ListElement target = (ListElement) element;
			return target.getChilds().stream().map(new Function<Element, Boolean>() {
				public Boolean apply(Element t) {
					return child_context.match(t);
				}
			}).reduce(true, new BinaryOperator<Boolean>() {
				public Boolean apply(Boolean t, Boolean u) {
					return t && u;
				}
			});
		} else {
			return false;
		}
	}

	@Override
	public <T> T convert(Element element, final ElementFactory<T> factory) {
		if (element instanceof ListElement) {
			final ListElement target = (ListElement) element;
			return factory.constructList(target.getChilds().stream().map(new Function<Element, T>() {
				@Override
				public T apply(Element t) {
					return child_context.convert(t, factory);
				}
			}).collect(Collectors.toCollection(new Supplier<List<T>>() {
				@Override
				public List<T> get() {
					return new ArrayList<T>();
				}
			})));
		} else {
			return null;
		}
	}

}
