package tw.lanyitin.zools.ast;

import tw.lanyitin.zools.runtime.Location;

public abstract class RuleTypeStmt extends ASTTree {

	private String name;

	public RuleTypeStmt(String text, Location l) {
		super(l);
		this.name = text;
	}
	
	public String getName() {
		return this.name;
	}

	public abstract RuleTypeStmt getBaseType();
	public abstract boolean isListType();
	public abstract boolean isStructType();
	public abstract boolean isPrimitiveType();
}
