package tw.lanyitin.zools.ast;

import tw.lanyitin.zools.runtime.Location;

public class ListTypeStmt extends RuleTypeStmt {

	private final RuleTypeStmt baseType;

	public ListTypeStmt(RuleTypeStmt baseType, Location l) {
		super("[" + baseType.getName() + "]", l);
		this.baseType = baseType;
	}

	@Override
	public RuleTypeStmt getBaseType() {
		return this.baseType;
	}

	@Override
	public boolean isListType() {
		return true;
	}

	@Override
	public boolean isStructType() {
		return false;
	}

	@Override
	public boolean isPrimitiveType() {
		return false;
	}

}
