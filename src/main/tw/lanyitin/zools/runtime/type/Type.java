package tw.lanyitin.zools.runtime.type;

import tw.lanyitin.zools.runtime.Location;
import tw.lanyitin.zools.runtime.context.RuleContext;

public abstract class Type {
	private Location location;

	public void setLocation(Location l) {
		this.location = l;
	}

	public Location getLocation() {
		return location;
	}

	abstract public String getName();

	abstract public RuleContext generateContext();

	abstract public String toString();

	@Override
	abstract public int hashCode();

	@Override
	abstract public boolean equals(Object obj);

}
