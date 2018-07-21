package tw.lanyitin.zools.runtime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;

import tw.lanyitin.zools.ast.ListTypeStmt;
import tw.lanyitin.zools.ast.PrimitiveTypeStmt;
import tw.lanyitin.zools.ast.RuleStmt;
import tw.lanyitin.zools.ast.RuleTypeStmt;
import tw.lanyitin.zools.ast.StructDeclStmt;
import tw.lanyitin.zools.ast.StructTypeStmt;
import tw.lanyitin.zools.ast.ZoolsFile;
import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.runtime.context.BindingContext;
import tw.lanyitin.zools.runtime.context.ListContext;
import tw.lanyitin.zools.runtime.context.PrimitiveContext;
import tw.lanyitin.zools.runtime.context.RuleContext;
import tw.lanyitin.zools.runtime.context.StructContext;
import tw.lanyitin.zools.runtime.type.ListType;
import tw.lanyitin.zools.runtime.type.Primitive;
import tw.lanyitin.zools.runtime.type.Struct;
import tw.lanyitin.zools.runtime.type.Type;

public class Engine {
	private final HashMap<String, RuleContext> rules;
	private final HashMap<String, Type> types;
	private final HashMap<String, List<Binding>> bindingMap;

	public Engine(ZoolsFile file) throws ZoolsException {
		this.rules = new HashMap<String, RuleContext>();
		this.types = new HashMap<String, Type>();
		this.bindingMap = new HashMap<String, List<Binding>>();
		construct(file);
	}

	public void addBinding(String name, List<Binding> bindings) {
		this.bindingMap.put(name, bindings);
	}

	public List<Binding> getBindings(String name) {
		return this.bindingMap.get(name);
	}

	private void construct(ZoolsFile file2) throws ZoolsException {
		// find all primitives and put it into global scope
		for (Primitive p : file2.getPrimitives()) {
			if (types.containsKey(p.getName())) {
				throw new ZoolsException(String.format("duplicate Type '%s'", p.getName()));
			}
			this.types.put(p.getName(), p);
		}
		// find all structures and put it into global scope
		for (StructDeclStmt s : file2.getStructs()) {
			if (types.containsKey(s.getName())) {
				throw new ZoolsException(String.format("duplicate Type '%s'", s.getName()));
			}
			this.types.put(s.getName(), new Struct(s.getName()));
		}
		// iterate through StructDeclStmt for fill up missing information in Structs
		for (StructDeclStmt s : file2.getStructs()) {
			Struct struct = (Struct) types.get(s.getName());
			for (Entry<String, Pair<String, Boolean>> entry : s.getProperties().entrySet()) {
				struct.addProperty(new FieldSelector(entry.getKey(), entry.getValue().getRight()),
						types.get(entry.getValue().getLeft()));
			}
		}

		// find all rules and put it into global scope
		for (RuleStmt r : file2.getRules()) {
			if (this.rules.containsKey(r.getName())) {
				throw new ZoolsException(String.format("duplicate rule '%s'", r.getName()));
			}
			RuleTypeStmt typeStmt = r.getType();
			RuleContext result = null;

			if (typeStmt instanceof PrimitiveTypeStmt) {
				Primitive type = (Primitive) types.get(typeStmt.getName());
				result = new PrimitiveContext(type);
			} else if (typeStmt instanceof StructTypeStmt) {
				Struct struct = (Struct) types.get(typeStmt.getName());
				StructContext ctx = new StructContext(struct);
				result = ctx;
			} else {
				ListTypeStmt stmt = (ListTypeStmt) typeStmt;
				ListType type = solveNestedListType(stmt);
				ListContext ctx = new ListContext(type);
				result = ctx;
			}
			this.rules.put(r.getName(), result);
		}

		// doing rule semantic analysis
		for (RuleStmt r : file2.getRules()) {
			RuleTypeStmt typeStmt = r.getType();
			if (typeStmt.isPrimitiveType()) {
				return;
			} else if (typeStmt.isListType()) {
				ListContext lc = (ListContext) this.resolveRule(r.getName());
				addBindingsToListContext(lc, r.getBindings(), file2);
			} else {
				StructContext sc = (StructContext) this.rules.get(r.getName());
				addBindingToStructContext(sc, r.getBindings(), file2);
			}
		}
	}

	private void addBindingsToListContext(ListContext lc, List<Binding> bindings, ZoolsFile file2)
			throws ZoolsException {
		RuleContext rc = lc;
		while (rc instanceof ListContext) {
			rc = ((ListContext) rc).getBaseContext();
		}
		if (rc instanceof StructContext) {
			StructContext sc = (StructContext) rc;
			addBindingToStructContext(sc, bindings, file2);
		}
	}

	private ListType solveNestedListType(ListTypeStmt stmt) {
		RuleTypeStmt childStmt = stmt.getBaseType();
		Type t;
		if (childStmt.isListType()) {
			t = solveNestedListType((ListTypeStmt) childStmt);
		} else {
			t = this.types.get(childStmt.getName());
		}
		return new ListType(t);
	}

	private void addBindingToStructContext(StructContext sc, List<Binding> bindings, ZoolsFile file2)
			throws ZoolsException {
		sc.setBindings(bindings);
		for (BindingContext bs : sc.getBindingContexts(this)) {
			if (bs.getQuery() instanceof CastSelector) {
				CastSelector cs = (CastSelector) bs.getQuery();
				StructContext sc2 = (StructContext) bs.getContext();
				RuleStmt resolveRuleContext = file2.resolveRule(cs.getTargetRule());
				List<Binding> subbindings = null;
				if (cs.getTargetField() != null) {
					subbindings = Arrays.asList(resolveRuleContext.getBindings().stream()
							.filter(t -> t.getQuery().getResolvedName().equals(cs.getTargetField())));
				} else {
					subbindings = resolveRuleContext.getBindings();
				}
				addBindingToStructContext(sc2, subbindings, file2);
			}
		}
	}

	public Element process(Element element) throws ZoolsException {
		return resolveTargetRule().process(element, this);
	}

	public RuleContext resolveTargetRule() throws ZoolsException {
		return this.resolveRule("target");
	}

	public RuleContext resolveRule(String name) throws ZoolsException {
		RuleContext context = this.rules.get(name);
		if (context == null) {
			throw new ZoolsException(String.format("unable to find rule: %s", name));
		}
		return context;
	}

}
