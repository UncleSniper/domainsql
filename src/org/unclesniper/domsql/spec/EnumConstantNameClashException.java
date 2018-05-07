package org.unclesniper.domsql.spec;

import org.unclesniper.domsql.Location;

public class EnumConstantNameClashException extends SpecSemanticsException {

	private final String typeName;

	private final String constantName;

	private final Location previousDefinition;

	public EnumConstantNameClashException(Location location, String typeName,
			String constantName, Location previousDefinition) {
		super(location, "Enum constant name clash within enum type '" + typeName + '\''
				+ (location == null ? "" : " at " + location) + ": Constant '" + constantName
				+ "' was already defined" + (previousDefinition == null ? "" : " at " + previousDefinition));
		this.typeName = typeName;
		this.constantName = constantName;
		this.previousDefinition = previousDefinition;
	}

	public String getTypeName() {
		return typeName;
	}

	public String getConstantName() {
		return constantName;
	}

	public Location getPreviousDefinition() {
		return previousDefinition;
	}

}
