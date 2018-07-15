package tw.lanyitin.zools.runtime.type;

import tw.lanyitin.zools.runtime.Location;

public abstract class Type {
	private Location location;
	public void setLocation(Location l) {
		this.location = l;
	}
	public Location getLocation() {
		return location;
	}
	abstract public String getName();
}
