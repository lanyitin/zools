package tw.lanyitin.zools.runtime.type;

import tw.lanyitin.zools.runtime.context.ListContext;
import tw.lanyitin.zools.runtime.context.RuleContext;

public class ListType extends Type {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((containedType == null) ? 0 : containedType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListType other = (ListType) obj;
		if (containedType == null) {
			if (other.containedType != null)
				return false;
		} else if (!containedType.equals(other.containedType))
			return false;
		return true;
	}

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

	@Override
	public String toString() {
		return this.getName();
	}
}
