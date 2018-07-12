package tw.lanyitin.zools.elements;

import java.util.List;

public class ListElement extends Element {
	private final List<Element> childs;

	public ListElement(List<Element> childs) {
		this.childs = childs;
	}
	
	public List<Element> getChilds() {
		return childs;
	}
}
