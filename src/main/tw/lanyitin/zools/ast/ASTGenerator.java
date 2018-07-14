package tw.lanyitin.zools.ast;

import java.util.regex.Pattern;

import tw.lanyitin.zools.zoolsBaseVisitor;
import tw.lanyitin.zools.zoolsParser;
import tw.lanyitin.zools.runtime.Binding;
import tw.lanyitin.zools.runtime.CastSelector;
import tw.lanyitin.zools.runtime.FieldSelector;
import tw.lanyitin.zools.runtime.PropertySelector;
import tw.lanyitin.zools.runtime.type.Primitive;

public class ASTGenerator extends zoolsBaseVisitor<ASTTree> {
	private ZoolsFile file;
	private StructDeclStmt struct;
	private RuleStmt rule;
	private RuleTypeStmt ruleTargetType;
	private PropertySelector propertyQuery;

	@Override public ASTTree visitFile(zoolsParser.FileContext ctx) {
		this.file = new ZoolsFile();
		visitChildren(ctx);
		return file;
	}

	@Override public ASTTree visitPrimitive(zoolsParser.PrimitiveContext ctx) {
		String regex = ctx.TOKEN_REGEX().getText();
		regex = regex.substring(1, regex.length() - 1);
		String type_name = ctx.TOKEN_IDENTIFIER().getText();
		Primitive result = new Primitive(type_name, Pattern.compile(regex));
		file.addPrimitive(result);
		return null;
	}

	@Override public ASTTree visitStruct_def(zoolsParser.Struct_defContext ctx) {
		struct = new StructDeclStmt(ctx.TOKEN_IDENTIFIER().getText());
		visitChildren(ctx);
		file.addStruct(struct);
		return null;
	}

	@Override public ASTTree visitProperty(zoolsParser.PropertyContext ctx) {
		visitChildren(ctx);
		String type_name = ctx.TOKEN_IDENTIFIER(1).getText();
		String p_name = ctx.TOKEN_IDENTIFIER(0).getText();
		struct.addProperty(p_name, type_name);
		return null;
	}

	@Override public ASTTree visitMapping_rule(zoolsParser.Mapping_ruleContext ctx) {
		this.rule = new RuleStmt(ctx.TOKEN_IDENTIFIER().getText());
		visitChildren(ctx);
		this.file.addRuleStmt(rule);
		return rule;
	}

	@Override public ASTTree visitTarget_type(zoolsParser.Target_typeContext ctx) {
		this.rule.setType((RuleTypeStmt)visitChildren(ctx));
		return null;
	}

	@Override public ASTTree visitName_and_mappings(zoolsParser.Name_and_mappingsContext ctx) {
		visitChildren(ctx);
		this.ruleTargetType = new RuleTypeStmt(ctx.TOKEN_IDENTIFIER().getText());
		return ruleTargetType;
	}

	@Override public ASTTree visitList_type(zoolsParser.List_typeContext ctx) {
		visitChildren(ctx);
		ruleTargetType.setListType(true);
		return ruleTargetType;
	}

	@Override public ASTTree visitMapping(zoolsParser.MappingContext ctx) {
		visitChildren(ctx);
		this.rule.addBinding(new Binding(ctx.TOKEN_IDENTIFIER().getText(), this.propertyQuery));
		return null;
	}

	@Override public ASTTree visitTarget_selector(zoolsParser.Target_selectorContext ctx) {
		this.propertyQuery = new FieldSelector(ctx.TOKEN_IDENTIFIER().getText());
		if (ctx.property_selector() != null && !ctx.property_selector().isEmpty()) {
			this.propertyQuery = new CastSelector(propertyQuery);
			visitChildren(ctx);
		}
		return null;
	}

	@Override public ASTTree visitProperty_selector(zoolsParser.Property_selectorContext ctx) {
		CastSelector result = (CastSelector) this.propertyQuery;
		result.setTargetRule(ctx.TOKEN_IDENTIFIER(0).getText());
		if (ctx.TOKEN_IDENTIFIER().size() > 1) {
			result.setTargetField(ctx.TOKEN_IDENTIFIER(1).getText());
		}
		if (ctx.property_selector() != null && !ctx.property_selector().isEmpty()) {
			this.propertyQuery = new CastSelector(propertyQuery);
			visitChildren(ctx);
		}
		return null;
	}
}
