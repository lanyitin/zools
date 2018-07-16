package tw.lanyitin.zools.runtime;

import tw.lanyitin.zools.elements.Element;
import tw.lanyitin.zools.elements.StructElement;

public class CastSelector extends PropertySelector {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target_field == null) ? 0 : target_field.hashCode());
		result = prime * result + ((target_rule == null) ? 0 : target_rule.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CastSelector other = (CastSelector) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target_field == null) {
			if (other.target_field != null)
				return false;
		} else if (!target_field.equals(other.target_field))
			return false;
		if (target_rule == null) {
			if (other.target_rule != null)
				return false;
		} else if (!target_rule.equals(other.target_rule))
			return false;
		return true;
	}

	private final PropertySelector source;
	private String target_rule;
	private String target_field;

	public CastSelector(PropertySelector source) {
		this.source = source;
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
	public Element getResolveProerpty(StructElement s, Engine env) {
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

	public PropertySelector getSource() {
		return source;
	}

	public String getTargetField() {
		return this.target_field;
	}

	public String getTargetRule() {
		return this.target_rule;
	}

	public void setTargetField(String text) {
		this.target_field = text;
	}

	public void setTargetRule(String text) {
		this.target_rule = text;
	}

	@Override
	public String toString() {
		String result = String.format("%s@%s", this.source.toString(), this.target_rule);
		if (this.target_field != null) {
			result += ("." + this.target_field);
		}
		return result;
	}

}
