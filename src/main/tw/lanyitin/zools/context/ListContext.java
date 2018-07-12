package tw.lanyitin.zools.context;

import java.util.function.BinaryOperator;
import java.util.function.Function;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.ElementFactory;
import tw.lanyitin.zools.elements.ListElement;

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
	public <T> T convert(Element element, ElementFactory<T> factory) {
		return factory.convert(element);
	}

}
