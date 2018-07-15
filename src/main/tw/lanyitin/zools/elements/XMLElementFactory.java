package tw.lanyitin.zools.elements;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONTokener;
import org.json.XML;
import org.w3c.dom.Node;

public class XMLElementFactory extends ElementFactory<Node> {
	private final JsonElementFactory jsonFactory;

	public XMLElementFactory() {
		this.jsonFactory = new JsonElementFactory();
	}

	@Override
	public Node convert(Element elem) {
		String xmlStr = XML.toString((new JSONTokener(jsonFactory.convert(elem).toString())).nextValue());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new ByteArrayInputStream(xmlStr.getBytes()));
		} catch (Exception e) {
			System.out.println(xmlStr);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Element parse(String str) {
		return jsonFactory.parse(XML.toJSONObject(str).toString());
	}
}
