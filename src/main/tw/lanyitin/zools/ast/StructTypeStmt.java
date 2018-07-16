package tw.lanyitin.zools.ast;

import tw.lanyitin.zools.runtime.Location;

public class StructTypeStmt extends RuleTypeStmt {

	public StructTypeStmt(String text, Location l) {
		super(text, l);
	}

	@Override
	public RuleTypeStmt getBaseType() {
		return null;
	}

	@Override
	public boolean isListType() {
		return false;
	}

	@Override
	public boolean isStructType() {
		return true;
	}

	@Override
	public boolean isPrimitiveType() {
		return false;
	}

}
