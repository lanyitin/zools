package tw.lanyitin.zools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tw.lanyitin.zools.ast.ASTGenerator;
import tw.lanyitin.zools.ast.ZoolsFile;
import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.ElementFactory;
import tw.lanyitin.zools.elements.JsonElementFactory;
import tw.lanyitin.zools.elements.XMLElementFactory;
import tw.lanyitin.zools.runtime.Environment;

public class Main {
	private static ZoolsFile construct_ast(CommandLine cmd) throws IOException {
		CharStream in = CharStreams.fromFileName(cmd.getOptionValue("rule"));
		zoolsLexer lexer = new zoolsLexer(in);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		zoolsParser parser = new zoolsParser(tokens);
		ASTGenerator generator = new ASTGenerator();
		ZoolsFile file = (ZoolsFile) generator.visitFile(parser.file());
		return file;
	}

	private static ElementFactory<?> generateElementFactory(String optionValue) {
		if (optionValue.equals("json")) {
			return new JsonElementFactory();
		} else {
			return new XMLElementFactory();
		}
	}

	public static void main(String[] args) throws IOException, ParseException {
		CommandLine cmd = setup_cli_args_parser(args);

		ZoolsFile file = construct_ast(cmd);
		Environment env = new Environment(file);

		ElementFactory<?> inputElementFactory = generateElementFactory(cmd.getOptionValue("if"));
		ElementFactory<?> outputElementFactory = generateElementFactory(cmd.getOptionValue("of"));
		String origin_str = readFromFile(cmd.getOptionValue("source"));
		Element element = env.process(inputElementFactory.parse(origin_str));
		System.out.println("# original format");
		System.out.println(origin_str);
		System.out.println("# new format");
		System.out.println(outputElementFactory.convert(element));
	}

	private static String readFromFile(String filePath) {
		StringBuilder contentBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				contentBuilder.append(sCurrentLine).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result = contentBuilder.toString();
		return result.substring(0, result.length() - 1);
	}

	private static CommandLine setup_cli_args_parser(String[] args) throws ParseException {
		Options options = new Options();
		options.addOption("rule", true, "path of rule file");
		options.addOption("source", true, "path of data source file");
		options.addOption("if", true, "format of input file");
		options.addOption("of", true, "format of output");
		CommandLineParser cmdParser = new DefaultParser();
		CommandLine cmd = cmdParser.parse(options, args);
		return cmd;
	}
}
