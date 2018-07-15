package tw.lanyitin.zools.ast;

public class RuleTypeStmt extends ASTTree {

	private final String baseType;
	private boolean isListType;

	public RuleTypeStmt(String text) {
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
