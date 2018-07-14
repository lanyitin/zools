package tw.lanyitin.zools.elements;

import java.util.List;

abstract public class ElementFactory<T> {

	public abstract Element parse(String str);

	public abstract T convert(Element elem, String tagName);
}