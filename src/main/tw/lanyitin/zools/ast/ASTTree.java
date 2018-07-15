package tw.lanyitin.zools.ast;

import tw.lanyitin.zools.runtime.Location;

abstract public class ASTTree {
	private final Location location;

	public Location getLocation() {
		return location;
	}

	public ASTTree(Location location) {
		this.location = location;
	}
}
