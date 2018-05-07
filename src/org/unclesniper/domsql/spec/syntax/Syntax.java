package org.unclesniper.domsql.spec.syntax;

import org.unclesniper.domsql.Location;

public abstract class Syntax {

	private Location location;

	public Syntax(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
