package org.unclesniper.domsql.spec;

import org.unclesniper.domsql.Location;

public class IntLiteralExceedsBoundsException extends SpecLexicalException {

	private final String specifier;

	public IntLiteralExceedsBoundsException(Location location, String specifier) {
		super(location, "Integer literal" + (location == null ? "" : " at " + location)
				+ " exceeds bounds: " + specifier);
		this.specifier = specifier;
	}

	public String getSpecifier() {
		return specifier;
	}

}
