package tw.lanyitin.zools.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import tw.lanyitin.zools.Property;

public class JsonElementFactory extends ElementFactory<JsonElement> {
	static JsonParser parser = new JsonParser();
	
	public Element parse(String str) {
		JsonElement element = parser.parse(str);
		return visitJsonElement(element);
	}
	
	public JsonElement convert(Element elem) {
		if (elem instanceof StringElement) {
			return new JsonPrimitive(((StringElement)elem).getContent());
		} else if (elem instanceof StructElement) {
			StructElement target = (StructElement) elem;
			final JsonObject result = new JsonObject();
			target.getProperties().forEach(new Consumer<Property>() {
				public void accept(Property t) {
					result.add(t.getName(), convert(t.getValue()));
				}
			});
			return result;
		} else {
			return null;
		}
	}
	
	private Element visitJsonElement(JsonElement element) {
		if (element.isJsonPrimitive()) {
			return new StringElement(element.getAsString());
		} else if (element.isJsonObject()) {
			final List<Property> properties = new ArrayList<Property>();
			JsonObject obj = element.getAsJsonObject();
			obj.entrySet().stream().forEach(new Consumer<Entry<String, JsonElement>>() {

				public void accept(Entry<String, JsonElement> t) {
					properties.add(new Property(t.getKey(), visitJsonElement(t.getValue())));
				}});
			return new StructElement(properties);
		} else {
			return null;
		}
	}
}
