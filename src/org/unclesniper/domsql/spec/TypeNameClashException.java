package org.unclesniper.domsql.spec;

import org.unclesniper.domsql.Location;

public class TypeNameClashException extends SpecSemanticsException {

	private final String typeName;

	private final Location previousDefinition;

	public TypeNameClashException(Location location, String typeName, Location previousDefinition) {
		super(location, "Type name clash" + (location == null ? "" : " at " + location) + ": Type '"
				+ typeName + "' was already defined"
				+ (previousDefinition == null ? "" : " at " + previousDefinition));
		this.typeName = typeName;
		this.previousDefinition = previousDefinition;
	}

	public String getTypeName() {
		return typeName;
	}

	public Location getPreviousDefinition() {
		return previousDefinition;
	}

}
