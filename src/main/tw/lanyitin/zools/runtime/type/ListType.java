package tw.lanyitin.zools.runtime.type;

public class ListType extends Type {
	private final Type containedType;

	public ListType(Type containedType) {
		this.containedType = containedType;
	}
	
	public Type getContainedType() {
		return this.containedType;
	}
}
