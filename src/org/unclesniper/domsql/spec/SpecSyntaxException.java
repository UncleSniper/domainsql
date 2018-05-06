package org.unclesniper.domsql.spec;

import org.unclesniper.domsql.Location;

public class SpecSyntaxException extends SpecException {

	private final Token badToken;

	private final String expectation;

	public SpecSyntaxException(Token badToken, String expectation) {
		super(badToken.getLocation(), "Syntax error at " + (badToken.getLocation() == null ? Location.UNKNOWN
				: badToken.getLocation()) + " near " + badToken.format() + (expectation == null ? ""
				: ": Expected " + expectation));
		this.badToken = badToken;
		this.expectation = expectation;
	}

	public SpecSyntaxException(Location endLocation, String expectation) {
		super(endLocation, "Syntax error at " + (endLocation == null ? Location.UNKNOWN : endLocation)
				+ " near end of file" + (expectation == null ? ""  : ": Expected " + expectation));
		badToken = null;
		this.expectation = expectation;
	}

	public SpecSyntaxException(Token badToken, Token.Type[] expectation) {
		this(badToken, SpecSyntaxException.formatExpectation(expectation));
	}

	public SpecSyntaxException(Location endLocation, Token.Type[] expectation) {
		this(endLocation, SpecSyntaxException.formatExpectation(expectation));
	}

	public Token getBadToken() {
		return badToken;
	}

	public String getExpectation() {
		return expectation;
	}

	private static String formatExpectation(Token.Type[] types) {
		if(types == null)
			return null;
		StringBuilder builder = new StringBuilder();
		String pending = null;
		boolean first = true;
		for(Token.Type type : types) {
			if(pending != null) {
				if(first)
					first = false;
				else
					builder.append(", ");
				builder.append(pending);
			}
			pending = type == null ? "end of file" : type.formatExpectation();
		}
		if(pending != null) {
			if(!first)
				builder.append(", or ");
			builder.append(pending);
		}
		return builder.toString();
	}

}
