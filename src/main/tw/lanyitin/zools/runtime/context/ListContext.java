package tw.lanyitin.zools.runtime.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.ListElement;
import tw.lanyitin.zools.runtime.Engine;
import tw.lanyitin.zools.runtime.ZoolsException;
import tw.lanyitin.zools.runtime.type.ListType;

public class ListContext extends RuleContext {
	private RuleContext base_context;

	public ListContext(ListType t) {
		super(t);
		this.base_context = t.getContainedType().generateContext();
	}
	
	public ListContext(RuleContext t) {
		super(new ListType(t.getType()));
		this.base_context = t;
	}

	public RuleContext getBaseContext() {
		return this.base_context;
	}

	@Override
	public Element process(Element element, final Engine env) throws ZoolsException {
		if (!(element instanceof ListElement)) {
			throw new ZoolsException(
					String.format("expected as ListElement, but actually got: '%s'", element.getClass().getName()));
		}

		ListElement target = (ListElement) element;
		List<Element> new_childs = new ArrayList<Element>();
		List<ZoolsException> errors = new ArrayList<ZoolsException>();
		for (Element child : target.getChilds()) {
			try {
				new_childs.add(base_context.process(child, env));
			} catch (ZoolsException e) {
				errors.add(new ZoolsException(String.format("%dth child:", target.getChilds().indexOf(child) + 1),  Arrays.asList(e)));
			}
		}
		if (errors.size() > 0) {
			throw new ZoolsException("unable to match following childs:", errors);
		}
		return new ListElement(target.getListType(), new_childs);
	}

	@Override
	public String toString() {
		return String.format("[%s]", this.base_context.toString());
	}

}
