package tw.lanyitin.zools.ast;

import tw.lanyitin.zools.runtime.Location;

public class RuleTypeStmt extends ASTTree {

	private final String baseType;
	private boolean isListType;

	public RuleTypeStmt(String text, Location l) {
		super(l);
		this.baseType = text;
		this.isListType = false;
	}

	public String getBaseType() {
		return baseType;
	}

	public boolean isListType() {
		return isListType;
	}

	public void setListType(boolean isListType) {
		this.isListType = isListType;
	}

}
