package org.unclesniper.domsql.spec;

import org.unclesniper.domsql.Location;

public class SpecException extends Exception {

	private final Location location;

	public SpecException(Location location, String message) {
		super(message);
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

}
