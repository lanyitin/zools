package tw.lanyitin.zools.runtime.context;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.ListElement;
import tw.lanyitin.zools.runtime.Environment;

public class ListContext extends RuleContext {
	private RuleContext child_context;

	public ListContext(RuleContext child_context) {
		this.child_context = child_context;
	}

	@Override
	public boolean match(Element element, final Environment env) {
		if (element instanceof ListElement) {
			ListElement target = (ListElement) element;
			return target.getChilds().stream().map(new Function<Element, Boolean>() {
				public Boolean apply(Element t) {
					return child_context.match(t, env);
				}
			}).reduce(true, new BinaryOperator<Boolean>() {
				public Boolean apply(Boolean t, Boolean u) {
					return t && u;
				}
			});
		} else {
			throw new RuntimeException(String.format("expected as ListElement, but actually got: '%s'", element.getClass().getName()));
		}
	}

	@Override
	public Element convert(Element element, final Environment env) {
		if (element instanceof ListElement) {
			final ListElement target = (ListElement) element;
			return new ListElement(target.getChilds().stream().map(new Function<Element, Element>() {
				public Element apply(Element t) {
					return child_context.convert(t, env);
				}
			}).collect(Collectors.toCollection(new Supplier<List<Element>>() {
				public List<Element> get() {
					return new ArrayList<Element>();
				}
			})));
		} else {
			throw new RuntimeException(String.format("expected as ListElement, but actually got: '%s'", element.getClass().getName()));
		}
	}

	public RuleContext getBaseContext() {
		return this.child_context;
	}

}
