package tw.lanyitin.zools.elements;

import java.util.List;

import tw.lanyitin.zools.runtime.type.ListType;

public class ListElement extends Element {
	private final List<Element> childs;
	private final ListType type;

	public ListElement(ListType type, List<Element> childs) {
		super(type);
		this.type = type;
		this.childs = childs;
	}

	public List<Element> getChilds() {
		return childs;
	}

	public ListType getListType() {
		return this.type;
	}

	@Override
	public String toString() {
		return "[" + this.childs.stream().
				map(x -> x.toString()).reduce((a, b) -> {return  a + ", " + b;}).orElse("") + "]";
	}
}
