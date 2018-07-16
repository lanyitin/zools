package tw.lanyitin.zools.runtime;

public class Binding {
	private final PropertySelector name;
	private final PropertySelector query;

	public Binding(PropertySelector lValue, PropertySelector rValue) {
		this.name = lValue;
		this.query = rValue;
	}

	public PropertySelector getName() {
		return name;
	}

	public PropertySelector getQuery() {
		return query;
	}
}
