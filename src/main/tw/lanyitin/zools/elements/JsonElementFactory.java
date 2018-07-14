package tw.lanyitin.zools.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

import com.google.gson.JsonArray;
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
		if (elem instanceof PrimitiveElement) {
			Object content = ((PrimitiveElement<?>)elem).getContent();
			if (content instanceof String) {
				return new JsonPrimitive((String) content);
			} else if (content instanceof Boolean) {
				return new JsonPrimitive((Boolean) content);
			} else {
				return new JsonPrimitive((Number) content);
			}
			
		} else if (elem instanceof StructElement) {
			StructElement target = (StructElement) elem;
			final JsonObject result = new JsonObject();
			target.getProperties().forEach(new Consumer<Property>() {
				public void accept(Property t) {
					result.add(t.getName(), convert(t.getValue()));
				}
			});
			return result;
		} else if (elem instanceof ListElement) {
			final JsonArray ary = new JsonArray();
			ListElement target = (ListElement) elem;
			for (Element elem2 : target.getChilds()) {
				ary.add(convert(elem2));
			}
			return ary;
		} else {
			return null;
		}
	}
	
	private Element visitJsonElement(JsonElement element) {
		if (element.isJsonPrimitive()) {
			JsonPrimitive primitive = element.getAsJsonPrimitive();
			if (primitive.isBoolean()) {
				return new PrimitiveElement<Boolean>(primitive.getAsBoolean());
			} else if (primitive.isNumber()) {
				return new PrimitiveElement<Number>(primitive.getAsNumber());
			} else {
				return new PrimitiveElement<String>(primitive.getAsString());
			}
			
		} else if (element.isJsonObject()) {
			final List<Property> properties = new ArrayList<Property>();
			JsonObject obj = element.getAsJsonObject();
			obj.entrySet().stream().forEach(new Consumer<Entry<String, JsonElement>>() {

				public void accept(Entry<String, JsonElement> t) {
					properties.add(new Property(t.getKey(), visitJsonElement(t.getValue())));
				}});
			return new StructElement(properties);
		} else if (element.isJsonArray()) {
			List<Element> lst = new ArrayList<Element>();
			for (JsonElement elem : element.getAsJsonArray()) {
				lst.add(visitJsonElement(elem));
			}
			return new ListElement(lst);
		} else {
			return null;
		}
	}

	@Override
	public JsonElement constructList(List<JsonElement> elements) {
		JsonArray ary = new JsonArray();
		for (JsonElement elem : elements) {
			ary.add(elem);
		}
		return ary;
	}
}
