package tw.lanyitin.zools;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

import tw.lanyitin.zools.context.MappingContext;
import tw.lanyitin.zools.context.RegexContext;
import tw.lanyitin.zools.context.StructContext;
import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.JsonElementFactory;
import tw.lanyitin.zools.elements.StringElement;
import tw.lanyitin.zools.elements.StructElement;

public class FormatValidationTest {

	@Test
	public void matchPattern() {
		MappingContext target = new RegexContext(Pattern.compile("\\d+"));
		assertTrue(target.match(new StringElement("123")));
	}
	
	@Test
	public void matchStruct() {
		List<Binding> bindings = Arrays.asList(new Binding("id", "_id", false, new RegexContext(Pattern.compile("\\d+"))));
		MappingContext target = new StructContext(bindings);
		assertTrue(target.match(new StructElement(Arrays.asList(new Property("_id", new StringElement("123"))))));
	}	

	
	@Test
	public void matchJsonString() {
		List<Binding> bindings = Arrays.asList(
				new Binding("id", "_id", false, new RegexContext(Pattern.compile("\\d+"))),
				new Binding("slot_id", "park_id", false, new RegexContext(Pattern.compile("\\d+")))
		);
		MappingContext target = new StructContext(bindings);
		Element element = (new JsonElementFactory()).parse("{\"_id\":1234, \"park_id\":321}");
		assertTrue(target.match(element));
		System.out.println(target.convert(element, new JsonElementFactory()));
	}
}
