package tw.lanyitin.zools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import tw.lanyitin.zools.elements.JsonElementFactory;


public class Main {
	public static void main(String[] args) throws IOException {
		ANTLRInputStream in = new ANTLRFileStream(args[0]);
        zoolsLexer lexer = new zoolsLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        zoolsParser parser = new zoolsParser(tokens);
        Visitor visitor = new Visitor();
        visitor.visitFile(parser.file());
        JsonElementFactory jsonElementFactory = new JsonElementFactory();
		String origin_str = readFromFile(args[1]);
		System.out.println("Origin String");
		System.out.println(origin_str);
		System.out.println("Result");
		System.out.println(visitor.convert(jsonElementFactory.parse(origin_str), jsonElementFactory));
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
