package tw.lanyitin.zools.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import tw.lanyitin.zools.runtime.Property;

public class JsonElementFactory extends ElementFactory<JsonElement> {
	static JsonParser parser = new JsonParser();

	@Override
	public JsonElement convert(Element elem) {
		if (elem instanceof PrimitiveElement) {
			Object content = ((PrimitiveElement<?>) elem).getContent();
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
			target.getProperties().forEach((Property t) -> {
				result.add(t.getName(), convert(t.getValue()));
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

	@Override
	public Element parse(String str) {
		JsonElement element = parser.parse(str);
		return transformToElement(element);
	}

	public Element transformToElement(JsonElement element) {
		if (element.isJsonPrimitive()) {
			JsonPrimitive primitive = element.getAsJsonPrimitive();
			if (primitive.isBoolean()) {
				return new PrimitiveElement<Boolean>(null, primitive.getAsBoolean());
			} else if (primitive.isNumber()) {
				return new PrimitiveElement<Number>(null, primitive.getAsNumber());
			} else {
				return new PrimitiveElement<String>(null, primitive.getAsString());
			}

		} else if (element.isJsonObject()) {
			final List<Property> properties = new ArrayList<Property>();
			JsonObject obj = element.getAsJsonObject();
			obj.entrySet().stream().forEach((Entry<String, JsonElement> t) -> {
				properties.add(new Property(t.getKey(), transformToElement(t.getValue())));
			});
			return new StructElement(null, properties);
		} else if (element.isJsonArray()) {
			List<Element> lst = new ArrayList<Element>();
			for (JsonElement elem : element.getAsJsonArray()) {
				lst.add(transformToElement(elem));
			}
			return new ListElement(null, lst);
		} else {
			return null;
		}
	}
}
