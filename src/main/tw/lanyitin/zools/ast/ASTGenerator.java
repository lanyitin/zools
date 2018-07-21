package tw.lanyitin.zools.ast;

import java.util.regex.Pattern;

import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.TerminalNode;

import tw.lanyitin.zools.ZoolsParser;
import tw.lanyitin.zools.ZoolsParser.MappingContext;
import tw.lanyitin.zools.ZoolsParser.Mapping_ruleContext;
import tw.lanyitin.zools.ZoolsParser.PrimitiveContext;
import tw.lanyitin.zools.ZoolsParser.PropertyContext;
import tw.lanyitin.zools.ZoolsParser.Struct_defContext;
import tw.lanyitin.zools.runtime.Binding;
import tw.lanyitin.zools.runtime.CastSelector;
import tw.lanyitin.zools.runtime.FieldSelector;
import tw.lanyitin.zools.runtime.Location;
import tw.lanyitin.zools.runtime.PropertySelector;
import tw.lanyitin.zools.runtime.type.Primitive;

public class ASTGenerator {
	private final String filePath;
	private final ZoolsParser parser;
	private Visitor visitor;

	public ASTGenerator(ZoolsParser parser) {
		this.parser = parser;
		this.filePath = parser.getFileName();
		this.visitor = new Visitor();
	}

	public ZoolsFile process() {
		return (ZoolsFile) visitor.visitFile(parser.file());
	}

	private class Visitor {

		public ASTTree visitFile(ZoolsParser.FileContext ctx) {
			ZoolsFile file = new ZoolsFile(filePath);

			for (PrimitiveContext primitiveContext : ctx.primitive()) {
				this.visitPrimitive(file, primitiveContext);
			}

			for (Struct_defContext structContext : ctx.struct_def()) {
				this.visitStruct_def(file, structContext);
			}

			for (Mapping_ruleContext context : ctx.mapping_rule()) {
				this.visitMapping_rule(file, context);
			}

			return file;
		}

		public void visitPrimitive(ZoolsFile file, ZoolsParser.PrimitiveContext ctx) {
			String regex = ctx.TOKEN_REGEX().getText();
			regex = regex.substring(1, regex.length() - 1);
			String type_name = ctx.TOKEN_IDENTIFIER().getText();
			Primitive result = new Primitive(type_name, Pattern.compile(regex));
			file.addPrimitive(result);
		}

		public void visitStruct_def(ZoolsFile file, ZoolsParser.Struct_defContext ctx) {
			TerminalNode token = ctx.TOKEN_IDENTIFIER();
			Interval si = token.getSourceInterval();
			StructDeclStmt struct = new StructDeclStmt(token.getText(), new Location(si.a, si.b, filePath));
			for (PropertyContext propertyContext : ctx.properties().property()) {
				this.visitProperty(struct, propertyContext);
			}
			file.addStruct(struct);
		}

		public void visitProperty(StructDeclStmt struct, ZoolsParser.PropertyContext ctx) {
			String type_name = ctx.TOKEN_IDENTIFIER(1).getText();
			boolean isOptional = false;
			if (ctx.TOKEN_QUESTIONMARK() != null) {
				isOptional = true;
			}
			String p_name = ctx.TOKEN_IDENTIFIER(0).getText();
			struct.addProperty(p_name, type_name, isOptional);
		}

		public void visitMapping_rule(ZoolsFile file, ZoolsParser.Mapping_ruleContext ctx) {
			TerminalNode token = ctx.TOKEN_IDENTIFIER();
			RuleStmt rule = new RuleStmt(token.getText(),
					new Location(token.getSourceInterval().a, token.getSourceInterval().b, filePath));
			rule.setType(this.visitTarget_type(file, rule, ctx.target_type()));
			file.addRuleStmt(rule);
		}

		public RuleTypeStmt visitTarget_type(ZoolsFile file, RuleStmt rule, ZoolsParser.Target_typeContext ctx) {
			if (ctx.list_type() != null && !ctx.list_type().isEmpty()) {
				return this.visitList_type(file, rule, ctx.list_type());
			} else {
				return this.visitName_and_mappings(file, rule, ctx.name_and_mappings());
			}
		}

		public RuleTypeStmt visitList_type(ZoolsFile file, RuleStmt rule, ZoolsParser.List_typeContext ctx) {
			Interval sourceInterval = ctx.TOKEN_LBRACKET().getSourceInterval();
			return new ListTypeStmt(this.visitTarget_type(file, rule, ctx.target_type()),
					new Location(sourceInterval.a, sourceInterval.b, filePath));
		}

		public RuleTypeStmt visitName_and_mappings(ZoolsFile file, RuleStmt stmt,
				ZoolsParser.Name_and_mappingsContext ctx) {
			TerminalNode token = ctx.TOKEN_IDENTIFIER();
			Interval si = token.getSourceInterval();
			RuleTypeStmt result;
			if (ctx.mappings() == null || ctx.mappings().isEmpty()) {
				result = new PrimitiveTypeStmt(token.getText(), new Location(si.a, si.b, filePath));
			} else {
				result = new StructTypeStmt(token.getText(), new Location(si.a, si.b, filePath));

				for (MappingContext context : ctx.mappings().mapping()) {
					this.visitMapping(file, stmt, (StructTypeStmt) result, context);
				}
			}
			return result;
		}

		public void visitMapping(ZoolsFile file, RuleStmt rule, StructTypeStmt struct, ZoolsParser.MappingContext ctx) {
			String originalPropertyName = ctx.TOKEN_IDENTIFIER().getText();
			FieldSelector oriignalSelector = new FieldSelector(originalPropertyName,
					file.resolveStruct(struct.getName()).resolveProperty(originalPropertyName).getRight());
			rule.addBinding(new Binding(oriignalSelector,
					this.visitProperty_selector(oriignalSelector, ctx.property_selector())));
		}

		public PropertySelector visitProperty_selector(PropertySelector oriignalSelector,
				ZoolsParser.Property_selectorContext ctx) {
			PropertySelector propertyQuery = new FieldSelector(ctx.TOKEN_IDENTIFIER().getText(),
					oriignalSelector.isOptional());
			if (ctx.cast_selector() != null && !ctx.cast_selector().isEmpty()) {
				propertyQuery = this.visitCast_selector(oriignalSelector, propertyQuery, ctx.cast_selector());
			}
			return propertyQuery;
		}

		public CastSelector visitCast_selector(PropertySelector originalSelector, PropertySelector sourceSelector,
				ZoolsParser.Cast_selectorContext ctx) {
			CastSelector result = new CastSelector(sourceSelector, originalSelector.isOptional());
			result.setTargetRule(ctx.TOKEN_IDENTIFIER(0).getText());
			if (ctx.TOKEN_IDENTIFIER().size() > 1) {
				result.setTargetField(ctx.TOKEN_IDENTIFIER(1).getText());
			}
			if (ctx.cast_selector() != null && !ctx.cast_selector().isEmpty()) {
				result = this.visitCast_selector(originalSelector, result, ctx.cast_selector());
			}
			return result;
		}
	}
}
