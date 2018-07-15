package tw.lanyitin.zools.ast;

import java.util.ArrayList;
import java.util.List;

import tw.lanyitin.zools.runtime.Binding;

public class RuleStmt extends ASTTree {
	private final String name;
	private final List<Binding> bindings;
	private RuleTypeStmt targetType;

	public RuleStmt(String name) {
		this.name = name;
		this.bindings = new ArrayList<Binding>();
	}

	public void addBinding(Binding b) {
		this.bindings.add(b);
	}

	public List<Binding> getBindings() {
		return bindings;
	}

	public String getName() {
		return name;
	}

	public RuleTypeStmt getType() {
		return targetType;
	}

	public void setType(RuleTypeStmt type) {
		this.targetType = type;
	}
}
