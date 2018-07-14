package tw.lanyitin.zools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import com.google.gson.JsonElement;

import tw.lanyitin.zools.ast.ASTGenerator;
import tw.lanyitin.zools.ast.RuleStmt;
import tw.lanyitin.zools.ast.ZoolsFile;
import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.JsonElementFactory;
import tw.lanyitin.zools.elements.XMLElementFactory;
import tw.lanyitin.zools.runtime.Environment;


public class Main {
	public static void main(String[] args) throws IOException {
		ANTLRInputStream in = new ANTLRFileStream(args[0]);
        zoolsLexer lexer = new zoolsLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        zoolsParser parser = new zoolsParser(tokens);
        JsonElementFactory jsonElementFactory = new JsonElementFactory();
        XMLElementFactory xmlElementFactory = new XMLElementFactory();
		String origin_str = readFromFile(args[1]);
        
        ASTGenerator generator = new ASTGenerator();
        ZoolsFile file = (ZoolsFile) generator.visitFile(parser.file());
        Environment e = new Environment(file);
        Element element = e.process(jsonElementFactory.parse(origin_str));
        System.out.println(origin_str);
        System.out.println(xmlElementFactory.convert(element, file.getRules().stream().filter(new Predicate<RuleStmt> () {
			@Override
			public boolean test(RuleStmt t) {
				return t.getName().equals("target");
			}
        }).findFirst().get().getName()));
	}
	
    private static String readFromFile(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String result = contentBuilder.toString();
        return result.substring(0, result.length() - 1);
    }
}
