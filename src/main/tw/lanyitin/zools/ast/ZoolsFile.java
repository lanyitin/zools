package tw.lanyitin.zools.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import tw.lanyitin.zools.runtime.Location;
import tw.lanyitin.zools.runtime.type.Primitive;

public class ZoolsFile extends ASTTree {
	private final List<Primitive> primitives;
	private final List<StructDeclStmt> structs;
	private final List<RuleStmt> rules;

	public ZoolsFile(String file_path) {
		super(new Location(0, 0, file_path));
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
		Optional<RuleStmt> result = this.rules.stream().filter((RuleStmt t) -> t.getName().equals(name)).findFirst();
		if (result.isPresent()) {
			return result.get();
		} else {
			throw new RuntimeException(String.format("unable to find rule \"%s\"", name));
		}
	}

	public StructDeclStmt resolveStruct(String name) {
		for (StructDeclStmt struct : structs) {
			if (struct.getName().equals(name)) {
				return struct;
			}
		}
		throw new RuntimeException(String.format("unable to find struct \"%s\"", name));
	}
}
