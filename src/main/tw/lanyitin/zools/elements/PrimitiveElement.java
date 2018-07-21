package tw.lanyitin.zools.elements;

import tw.lanyitin.zools.runtime.type.Primitive;

public class PrimitiveElement<T extends Object> extends Element {
	final private T content;

	public PrimitiveElement(Primitive type, T content) {
		super(type);
		this.content = content;
	}

	public T getContent() {
		return this.content;
	}

	public String getRepr() {
		return this.content.toString();
	}

	@Override
	public String toString() {
		return this.getRepr();
	}

}
