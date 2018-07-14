package tw.lanyitin.zools.runtime;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.StructElement;

public class CastSelector extends PropertySelector {

	private final PropertySelector source;
	private String target_rule;
	private String target_field;

	public CastSelector(PropertySelector source) {
		this.source = source;
	}
	public PropertySelector getSource() {
		return source;
	}
	public void setTargetRule(String text) {
		this.target_rule = text;
	}
	public void setTargetField(String text) {
		this.target_field = text;
	}
	public String getTargetRule() {
		return this.target_rule;
	}
	public String getTargetField() {
		return this.target_field;
	}
	@Override
	public String getResolvedName() {
		if (target_field != null) {
			return target_field;
		} else {
			return source.getResolvedName();
		}
	}
	@Override
	public Element getResolveProerpty(StructElement s, Environment env) {
		Element elem = source.getResolveProerpty(s, env);
		if (this.target_field != null) {
			StructElement sc = (StructElement) elem;
			for (Property e : sc.getProperties()) {
				if (e.getName().equals(this.target_field)) {
					return e.getValue();
				}
			}
			return null;
		}
		return elem;
	}

}
