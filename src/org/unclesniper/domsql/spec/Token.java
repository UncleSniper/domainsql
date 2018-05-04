package org.unclesniper.domsql.spec;

import org.unclesniper.domsql.Location;

public final class Token {

	public enum Type {

		NAME(null),
		ENUM,
		LEFT_ROUND("("),
		RIGHT_ROUND(")"),
		LEFT_CURLY("{"),
		RIGHT_CURLY("}"),
		COMMA(","),
		CLASS,
		EQUAL("="),
		EXTENDS,
		ARROW("->"),
		KEY,
		INVERSE,
		STAR("*"),
		INT,
		LONG,
		DOUBLE,
		STRING;

		private final boolean keyword;

		private final String rendition;

		private Type() {
			keyword = true;
			rendition = name().toLowerCase();
		}

		private Type(String rendition) {
			keyword = false;
			this.rendition = rendition;
		}

		public boolean isKeyword() {
			return keyword;
		}

		public String getRendition() {
			return rendition;
		}

	}

	private final Location location;

	private final Type type;

	private final String text;

	public Token(Location location, Type type, String text) {
		this.location = location;
		this.type = type;
		this.text = text;
	}

	public Location getLocation() {
		return location;
	}

	public Type getType() {
		return type;
	}

	public String getText() {
		return text;
	}

}
