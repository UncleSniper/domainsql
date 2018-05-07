package org.unclesniper.domsql.spec;

import java.io.Reader;
import java.util.Deque;
import java.io.IOException;
import java.util.LinkedList;
import org.unclesniper.domsql.Doom;
import org.unclesniper.domsql.Location;

public class Lexer {

	private enum State {
		NONE,
		MINUS,
		NAME,
		QUOTED_NAME,
		INT_LITERAL,
		STRING_LITERAL
	}

	private String file;

	private int line;

	private int column;

	private final Deque<Token> tokens = new LinkedList<Token>();

	private State state = State.NONE;

	private int start;

	private StringBuilder buffer;

	private Reader reader;

	private char[] inputBuffer;

	public Lexer(String file) {
		this(file, 1, 1, null);
	}

	public Lexer(String file, int line, int column) {
		this(file, line, column, null);
	}

	public Lexer(String file, Reader reader) {
		this(file, 1, 1, reader);
	}

	public Lexer(String file, int line, int column, Reader reader) {
		this.file = file;
		this.line = line;
		this.column = column;
		setReader(reader);
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		if(this.reader == reader)
			return;
		inputBuffer = null;
		this.reader = reader;
		if(reader != null)
			inputBuffer = new char[128];
	}

	public Location getLocation() {
		return new Location(file, line, column);
	}

	private void token(Token.Type type, String text) {
		tokens.addLast(new Token(new Location(file, line, start), type, text));
	}

	private void token(Token.Type type) {
		String text;
		if(buffer != null) {
			text = buffer.toString();
			buffer = null;
		}
		else
			text = type.getRendition();
		token(type, text);
	}

	private void nameToken() {
		String text = buffer.toString();
		buffer = null;
		token(Token.typeByName(text), text);
	}

	private void unexpected(char c) throws UnexpectedCharacterException {
		throw new UnexpectedCharacterException(new Location(file, line, column), c);
	}

	public void pushSource(char[] chars, int offset, int count) throws UnexpectedCharacterException {
		int end = offset + count;
		for(int i = offset; i < end; ++i) {
			char c = chars[i];
			switch(state) {
				case NONE:
					start = column;
					switch(c) {
						case ' ':
						case '\t':
						case '\r':
						case '\n':
						case '\f':
							break;
						case '(':
							token(Token.Type.LEFT_ROUND);
							break;
						case ')':
							token(Token.Type.RIGHT_ROUND);
							break;
						case '{':
							token(Token.Type.LEFT_CURLY);
							break;
						case '}':
							token(Token.Type.RIGHT_CURLY);
							break;
						case ',':
							token(Token.Type.COMMA);
							break;
						case '=':
							token(Token.Type.EQUAL);
							break;
						case '-':
							state = State.MINUS;
							break;
						case '*':
							token(Token.Type.STAR);
							break;
						case '`':
							buffer = new StringBuilder();
							state = State.QUOTED_NAME;
							break;
						case '_':
							buffer = new StringBuilder();
							buffer.append(c);
							state = State.NAME;
							break;
						case '"':
							buffer = new StringBuilder();
							state = State.STRING_LITERAL;
							break;
						default:
							if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
								buffer = new StringBuilder();
								buffer.append(c);
								state = State.NAME;
							}
							else if(c >= '0' && c <= '9') {
								buffer = new StringBuilder();
								buffer.append(c);
								state = State.INT_LITERAL;
							}
							else
								unexpected(c);
							break;
					}
					break;
				case MINUS:
					if(c != '>')
						unexpected(c);
					token(Token.Type.ARROW);
					state = State.NONE;
					break;
				case NAME:
					if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_')
						buffer.append(c);
					else {
						nameToken();
						state = State.NONE;
						--i;
						continue;
					}
					break;
				case QUOTED_NAME:
					switch(c) {
						case '\r':
						case '\n':
						case '\t':
						case '\b':
						case '\f':
							unexpected(c);
						case '`':
							if(buffer.length() == 0)
								unexpected(c);
							token(Token.Type.NAME);
							state = State.NONE;
							break;
						default:
							buffer.append(c);
							break;
					}
					break;
				case INT_LITERAL:
					if(c >= '0' && c <= '9')
						buffer.append(c);
					else {
						token(Token.Type.INT_LITERAL);
						state = State.NONE;
						--i;
						continue;
					}
					break;
				case STRING_LITERAL:
					switch(c) {
						case '\r':
						case '\n':
						case '\b':
						case '\f':
							unexpected(c);
						case '"':
							token(Token.Type.STRING_LITERAL);
							state = State.NONE;
							break;
						default:
							buffer.append(c);
							break;
					}
					break;
				default:
					throw new Doom("Unrecognized state: " + state.name());
			}
			if(c == '\n') {
				++line;
				column = 1;
			}
			else
				++column;
		}
	}

	public void endUnit() throws UnexpectedEndOfUnitException {
		switch(state) {
			case NONE:
				break;
			case NAME:
				nameToken();
				break;
			case QUOTED_NAME:
			case MINUS:
				throw new UnexpectedEndOfUnitException(new Location(file, line, column));
			default:
				throw new Doom("Unrecognized state: " + state.name());
		}
		state = State.NONE;
	}

	public Token fetchToken() {
		return tokens.isEmpty() ? null : tokens.removeFirst();
	}

	public Token nextToken() throws SpecLexicalException, IOException {
		if(reader == null)
			return fetchToken();
		while(tokens.isEmpty()) {
			int count = reader.read(inputBuffer);
			if(count > 0)
				pushSource(inputBuffer, 0, count);
			else {
				endUnit();
				if(tokens.isEmpty())
					return null;
			}
		}
		return tokens.removeFirst();
	}

	public static String formatChar(char c) {
		switch(c) {
			case ' ':
				return "<space>";
			case '\t':
				return "<tab>";
			case '\r':
				return "<carriage return>";
			case '\n':
				return "<linefeed>";
			case '\f':
				return "<formfeed>";
			default:
				return "'" + c + '\'';
		}
	}

	public static boolean isIdentifier(String name) {
		int length = name.length();
		if(length == 0)
			return false;
		for(int i = 0; i < length; ++i) {
			char c = name.charAt(i);
			if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_')
				continue;
			if(i > 0 && c >= '0' && c <= '9')
				continue;
			return false;
		}
		return true;
	}

}
