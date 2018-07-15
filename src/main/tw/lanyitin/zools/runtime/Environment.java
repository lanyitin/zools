package tw.lanyitin.zools.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import tw.lanyitin.zools.ast.RuleStmt;
import tw.lanyitin.zools.ast.RuleTypeStmt;
import tw.lanyitin.zools.ast.StructDeclStmt;
import tw.lanyitin.zools.ast.ZoolsFile;
import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.runtime.context.BindingContext;
import tw.lanyitin.zools.runtime.context.ListContext;
import tw.lanyitin.zools.runtime.context.PrimitiveContext;
import tw.lanyitin.zools.runtime.context.RuleContext;
import tw.lanyitin.zools.runtime.context.StructContext;
import tw.lanyitin.zools.runtime.type.Primitive;
import tw.lanyitin.zools.runtime.type.Struct;
import tw.lanyitin.zools.runtime.type.Type;

public class Environment {
	private final HashMap<String, RuleContext> rules;
	private final HashMap<String, Type> types;

	public Environment(ZoolsFile file) {
		this.rules = new HashMap<String, RuleContext>();
		this.types = new HashMap<String, Type>();
		construct(file);
	}

	private void construct(ZoolsFile file2) {
		for (Primitive p : file2.getPrimitives()) {
			if (types.containsKey(p.getName())) {
				throw new RuntimeException(String.format("duplicate Type '%s'", p.getName()));
			}
			this.types.put(p.getName(), p);
		}
		for (StructDeclStmt s : file2.getStructs()) {
			if (types.containsKey(s.getName())) {
				throw new RuntimeException(String.format("duplicate Type '%s'", s.getName()));
			}
			this.types.put(s.getName(), new Struct(s.getName()));
		}
		for (StructDeclStmt s : file2.getStructs()) {
			Struct struct = (Struct) types.get(s.getName());
			for (Entry<String, String> entry : s.getProperties().entrySet()) {
				struct.addProperty(entry.getKey(), types.get(entry.getValue()));
			}
		}
		for (RuleStmt r : file2.getRules()) {
			RuleTypeStmt typeStmt = r.getType();
			RuleContext result = null;
			Type type = types.get(typeStmt.getBaseType());
			if (type instanceof Primitive) {
				result = new PrimitiveContext(((Primitive) type));
			} else {
				Struct struct = (Struct) type;
				List<Binding> bindings = generateRawBindings(struct);
				StructContext ctx = new StructContext(struct, generateBindingContext(struct, bindings));
				result = ctx;
			}
			if (typeStmt.isListType()) {
				result = new ListContext(result);
			}
			this.rules.put(r.getName(), result);
		}
		for (RuleStmt r : file2.getRules()) {
			RuleTypeStmt typeStmt = r.getType();
			Type type = types.get(typeStmt.getBaseType());
			if (type instanceof Primitive) {
				continue;
			} else {
				RuleContext ctx = this.rules.get(r.getName());
				StructContext sc;
				while (ctx instanceof ListContext) {
					ctx = ((ListContext) ctx).getBaseContext();
				}
				sc = (StructContext) ctx;
				replaceBindingContextQuery(r.getBindings(), sc);
				for (BindingContext bs : sc.getBindingContexts()) {
					if (bs.getQuery() instanceof CastSelector) {
						CastSelector cs = (CastSelector) bs.getQuery();
						if (cs.getTargetField() == null) {
							StructContext sc2 = (StructContext) bs.getContext();
							List<Binding> bs2 = file2.resolveRule(cs.getTargetRule()).getBindings();
							replaceBindingContextQuery(bs2, sc2);
						}
					}
				}
			}
		}
	}

	private List<BindingContext> generateBindingContext(Struct struct, List<Binding> bindings) {
		List<BindingContext> contexts = new ArrayList<BindingContext>();
		for (Binding b : bindings) {
			Type propertyType = struct.getTypeOfProperty(b.getName());
			if (propertyType instanceof Primitive) {
				contexts.add(new BindingContext(b.getQuery(), new PrimitiveContext(((Primitive) propertyType))));
			} else {
				Struct s = (Struct) propertyType;
				contexts.add(new BindingContext(b.getQuery(),
						new StructContext(s, generateBindingContext(s, generateRawBindings(s)))));
			}
		}
		return contexts;
	}

	private List<Binding> generateRawBindings(Struct struct) {
		List<Binding> bindings = new ArrayList<Binding>();
		for (Entry<String, Type> entry : struct.getProperties().entrySet()) {
			bindings.add(new Binding(entry.getKey(), new FieldSelector(entry.getKey())));
		}
		return bindings;
	}

	public RuleContext getTargetRule() {
		return this.resolveRule("target");
	}

	public Element process(Element element) {
		return this.rules.get("target").process(element, this);
	}

	private void replaceBindingContextQuery(List<Binding> bindings, final StructContext ctx) {
		bindings.stream().forEach((Binding t) -> ctx.replaceFieldSelecor(t.getName(), t.getQuery()));
	}

	public RuleContext resolveRule(String name) {
		return this.rules.get(name);
	}

}
