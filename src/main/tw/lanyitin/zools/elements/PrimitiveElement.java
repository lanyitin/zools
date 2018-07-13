package tw.lanyitin.zools.elements;

public class PrimitiveElement<T extends Object> extends Element {
	final private T content;

	public PrimitiveElement(T content) {
		this.content = content;
	}
	
	public T getContent() {
		return this.content;
	}
	
	public String getRepr() {
		return this.content.toString();
	}

}
