package tw.lanyitin.zools.elements;

abstract public class ElementFactory<T> {

	public abstract T convert(Element elem);

	public abstract Element parse(String str);
}