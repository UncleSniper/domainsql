package org.unclesniper.domsql.spec;

import org.unclesniper.domsql.Location;

public class SpecSemanticsException extends SpecException {

	public SpecSemanticsException(Location location, String message) {
		super(location, message);
	}

}
