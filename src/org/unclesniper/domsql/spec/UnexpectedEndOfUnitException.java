package org.unclesniper.domsql.spec;

import org.unclesniper.domsql.Location;

public class UnexpectedEndOfUnitException extends SpecLexicalException {

	public UnexpectedEndOfUnitException(Location location) {
		super(location, "Unexpected end of lexical unit at " + (location == null ? Location.UNKNOWN : location));
	}

}
