package tw.lanyitin.zools.runtime;

public class Binding {
	private final String name;
	private final PropertySelector query;

	public Binding(String lValue, PropertySelector rValue) {
		this.name = lValue;
		this.query = rValue;
	}

	public String getName() {
		return name;
	}

	public PropertySelector getQuery() {
		return query;
	}
}
