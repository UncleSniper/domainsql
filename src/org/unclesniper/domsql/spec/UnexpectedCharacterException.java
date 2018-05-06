package org.unclesniper.domsql.spec;

import org.unclesniper.domsql.Location;

public class UnexpectedCharacterException extends SpecLexicalException {

	private final char badChar;

	public UnexpectedCharacterException(Location location, char badChar) {
		super(location, "Unexpected character at " + (location == null ? Location.UNKNOWN : location) + ": "
				+ Lexer.formatChar(badChar));
		this.badChar = badChar;
	}

	public char getBadChar() {
		return badChar;
	}

}
