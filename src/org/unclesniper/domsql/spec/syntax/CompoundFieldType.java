package org.unclesniper.domsql.spec.syntax;

import org.unclesniper.domsql.Location;

public class CompoundFieldType extends FieldType {

	private String name;

	private Location starLocation;

	public CompoundFieldType(Location location, String name, Location starLocation) {
		super(location);
		this.name = name;
		this.starLocation = starLocation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getStarLocation() {
		return starLocation;
	}

	public void setStarLocation(Location starLocation) {
		this.starLocation = starLocation;
	}

}
