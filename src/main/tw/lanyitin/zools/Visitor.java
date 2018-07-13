package tw.lanyitin.zools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import tw.lanyitin.zools.context.ListContext;
import tw.lanyitin.zools.context.MappingContext;
import tw.lanyitin.zools.context.StructContext;
import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.ElementFactory;
import tw.lanyitin.zools.type.Primitive;
import tw.lanyitin.zools.type.Struct;

public class Visitor extends zoolsBaseVisitor<String> {
	private final Map<String, Primitive> primitives;
	private final Map<String, Struct> structs;
	private final Map<String, MappingContext> rules;
	
	
	private List<Binding> extra_bindings;
	private MappingContext current_context;
	
	private String current_type;

	public Visitor() {
		this.primitives = new HashMap<String, Primitive>();
		this.structs = new HashMap<String, Struct>();
		this.rules = new HashMap<String, MappingContext>();
	}
	
	public boolean validate(Element element) {
		return this.rules.get("target").match(element);
	}
	
	public <T> T convert(Element element, ElementFactory<T> factory) {
		return this.rules.get("target").convert(element, factory);
	}
	@Override public String visitFile(zoolsParser.FileContext ctx) { return visitChildren(ctx); }

	@Override public String visitPrimitive(zoolsParser.PrimitiveContext ctx) { 
		String regex = ctx.TOKEN_REGEX().getText();
		this.primitives.put(ctx.TOKEN_IDENTIFIER().getText(), new Primitive(Pattern.compile(regex.substring(1, regex.length() - 1))));
		return visitChildren(ctx);
	}

	@Override public String visitStruct_def(zoolsParser.Struct_defContext ctx) {
		current_type = ctx.TOKEN_IDENTIFIER().getText();
		structs.put(current_type, new Struct());
		return visitChildren(ctx);
	}

	@Override public String visitProperty(zoolsParser.PropertyContext ctx) {
		visitChildren(ctx);
		if (primitives.containsKey(ctx.TOKEN_IDENTIFIER(1).getText())) {
			this.structs.get(current_type).getProperties().put(ctx.TOKEN_IDENTIFIER(0).getText(), primitives.get(ctx.TOKEN_IDENTIFIER(1).getText()));
		} else {
			// TODO: support structure
		}
		return "";
	}

	@Override public String visitMapping_rule(zoolsParser.Mapping_ruleContext ctx) {
		String result = visitChildren(ctx);
		assert(current_context != null);
		this.rules.put(ctx.TOKEN_IDENTIFIER().getText(), current_context);
		current_context = null;
		return result;
	}

	@Override public String visitTarget_type(zoolsParser.Target_typeContext ctx) {
		if (ctx.list_type() == null || ctx.list_type().isEmpty()) {
			extra_bindings = new ArrayList<Binding>();
			String result = visitChildren(ctx);
			assert(current_context == null);
			if (primitives.containsKey(current_type)) {
				current_context = primitives.get(current_type).getContext();
			} else {
				StructContext struct_context = (StructContext) structs.get(current_type).getContext();
				for (Binding binding : extra_bindings) {
					struct_context.replaceBinding(binding.getLValue(), binding);
				}
				current_context = struct_context;
			}
			extra_bindings = null;
			return result;
		} else {
			return visitChildren(ctx);
		}
	}
	
	@Override public String visitName_and_mappings(zoolsParser.Name_and_mappingsContext ctx) {
		current_type = ctx.TOKEN_IDENTIFIER().getText();
		return visitChildren(ctx);
	}
	
	@Override public String visitList_type(zoolsParser.List_typeContext ctx) {
		extra_bindings = new ArrayList<Binding>();
		String result = visitChildren(ctx);
		assert(current_context == null);
		current_context = new ListContext(new StructContext(structs.get(current_type), extra_bindings));
		extra_bindings = null;
		return result;
	}

	@Override public String visitMapping(zoolsParser.MappingContext ctx) {
		extra_bindings.add(new Binding(ctx.TOKEN_IDENTIFIER().getText(), ctx.target_selector().getText(), structs.get(current_type).getProperties().get(ctx.TOKEN_IDENTIFIER().getText()).getContext()));
		return visitChildren(ctx);
	}
}
