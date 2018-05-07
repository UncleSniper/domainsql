package org.unclesniper.domsql.spec;

import java.util.Map;
import java.util.HashMap;
import org.unclesniper.domsql.Location;

public final class Token {

	public enum Type {

		NAME(null),
		INT_LITERAL(null),
		STRING_LITERAL(null),
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

		public String formatExpectation() {
			switch(this) {
				case NAME:
					return "identifier";
				case INT_LITERAL:
					return "integer literal";
				case STRING_LITERAL:
					return "string literal";
				default:
					return '\'' + rendition + '\'';
			}
		}

	}

	private static final Map<String, Type> KEYWORDS;

	static {
		KEYWORDS = new HashMap<String, Type>();
		for(Type type : Type.values()) {
			if(type.isKeyword())
				KEYWORDS.put(type.getRendition(), type);
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

	public String format() {
		switch(type) {
			case NAME:
				if(Lexer.isIdentifier(text))
					return '\'' + text + '\'';
				else
					return '`' + text + '`';
			case INT_LITERAL:
				return '\'' + text + '\'';
			case STRING_LITERAL:
				return "'\"" + text + "\"'";
			default:
				return '\'' + type.getRendition() + '\'';
		}
	}

	public static Type typeByName(String text) {
		Type type = Token.KEYWORDS.get(text);
		return type == null ? Type.NAME : type;
	}

}
