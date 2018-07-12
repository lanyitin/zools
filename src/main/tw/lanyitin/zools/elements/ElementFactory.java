package tw.lanyitin.zools.elements;


abstract public class ElementFactory<T> {

	public abstract Element parse(String str);

	public abstract T convert(Element elem);

}