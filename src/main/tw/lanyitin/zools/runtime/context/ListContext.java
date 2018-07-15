package tw.lanyitin.zools.runtime.context;

import java.util.ArrayList;
import java.util.List;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.ListElement;
import tw.lanyitin.zools.runtime.Environment;
import tw.lanyitin.zools.runtime.type.ListType;

public class ListContext extends RuleContext {
	private RuleContext base_context;

	public ListContext(RuleContext child_context) {
		super(new ListType(child_context.getType()));
		this.base_context = child_context;
	}

	public RuleContext getBaseContext() {
		return this.base_context;
	}

	@Override
	public Element process(Element element, final Environment env) {
		if (!(element instanceof ListElement)) {
			throw new RuntimeException(
					String.format("expected as ListElement, but actually got: '%s'", element.getClass().getName()));
		}

		ListElement target = (ListElement) element;
		List<Element> new_childs = new ArrayList<Element>();
		for (Element child : target.getChilds()) {
			new_childs.add(base_context.process(child, env));
		}
		return new ListElement(target.getListType(), new_childs);
	}

}
