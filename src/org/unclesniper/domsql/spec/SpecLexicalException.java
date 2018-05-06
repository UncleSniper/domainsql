package org.unclesniper.domsql.spec;

import org.unclesniper.domsql.Location;

public class SpecLexicalException extends SpecException {

	public SpecLexicalException(Location location, String message) {
		super(location, message);
	}

}
