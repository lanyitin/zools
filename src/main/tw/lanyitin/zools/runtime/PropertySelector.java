package tw.lanyitin.zools.runtime;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.StructElement;

abstract public class PropertySelector {

	public abstract String getResolvedName();

	public abstract Element getResolveProerpty(StructElement s, Environment env);

}
