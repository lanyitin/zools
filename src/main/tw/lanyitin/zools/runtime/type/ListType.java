package tw.lanyitin.zools.runtime.type;

import tw.lanyitin.zools.runtime.context.ListContext;
import tw.lanyitin.zools.runtime.context.RuleContext;

public class ListType extends Type {
	private final Type containedType;

	public ListType(Type containedType) {
		this.containedType = containedType;
	}

	public Type getContainedType() {
		return this.containedType;
	}

	@Override
	public String getName() {
		return String.format("[%s]", containedType.getName());
	}

	@Override
	public RuleContext generateContext() {
		return new ListContext(this);
	}
}
