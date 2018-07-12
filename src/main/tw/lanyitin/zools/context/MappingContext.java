package tw.lanyitin.zools.context;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.ElementFactory;

abstract public class MappingContext {
	abstract public boolean match(Element element);
	abstract public <T> T convert(Element element, ElementFactory<T> factory);
}
