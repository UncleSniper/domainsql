package org.unclesniper.domsql.spec.syntax;

import org.unclesniper.domsql.Location;

public class KeyMapping extends Syntax {

	private String hereName;

	private Location hereLocation;

	private String thereName;

	private Location thereLocation;

	public KeyMapping(String hereName, Location hereLocation, Location arrowLocation,
			String thereName, Location thereLocation) {
		super(arrowLocation);
		this.hereName = hereName;
		this.hereLocation = hereLocation;
		this.thereName = thereName;
		this.thereLocation = thereLocation;
	}

	public String getHereName() {
		return hereName;
	}

	public void setHereName(String hereName) {
		this.hereName = hereName;
	}

	public Location getHereLocation() {
		return hereLocation;
	}

	public void setHereLocation(Location hereLocation) {
		this.hereLocation = hereLocation;
	}

	public String getThereName() {
		return thereName;
	}

	public void setThereName(String thereName) {
		this.thereName = thereName;
	}

	public Location getThereLocation() {
		return thereLocation;
	}

	public void setThereLocation(Location thereLocation) {
		this.thereLocation = thereLocation;
	}

}
