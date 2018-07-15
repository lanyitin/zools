package tw.lanyitin.zools.ast;

import java.util.ArrayList;
import java.util.List;

import tw.lanyitin.zools.runtime.type.Primitive;

public class ZoolsFile extends ASTTree {
	private final List<Primitive> primitives;
	private final List<StructDeclStmt> structs;
	private final List<RuleStmt> rules;

	public ZoolsFile() {
		this.primitives = new ArrayList<Primitive>();
		this.structs = new ArrayList<StructDeclStmt>();
		this.rules = new ArrayList<RuleStmt>();
	}

	public void addPrimitive(Primitive stmt) {
		this.primitives.add(stmt);
	}

	public void addRuleStmt(RuleStmt stmt) {
		this.rules.add(stmt);
	}

	public void addStruct(StructDeclStmt stmt) {
		this.structs.add(stmt);
	}

	public List<Primitive> getPrimitives() {
		return primitives;
	}

	public List<RuleStmt> getRules() {
		return rules;
	}

	public List<StructDeclStmt> getStructs() {
		return structs;
	}

	public RuleStmt resolveRule(final String name) {
		return this.rules.stream().filter((RuleStmt t) -> t.getName().equals(name)).findFirst().get();
	}
}
