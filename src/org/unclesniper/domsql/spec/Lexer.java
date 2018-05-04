package org.unclesniper.domsql.spec;

import java.util.Deque;
import java.util.LinkedList;
import org.unclesniper.domsql.Doom;
import org.unclesniper.domsql.Location;

public class Lexer {

	private enum State {
		NONE,
		MINUS,
		NAME,
		QUOTED_NAME
	}

	private String file;

	private int line;

	private int column;

	private final Deque<Token> tokens = new LinkedList<Token>();

	private State state = State.NONE;

	private int start;

	private StringBuilder buffer;

	public Lexer(String file) {
		this(file, 1, 1);
	}

	public Lexer(String file, int line, int column) {
		this.file = file;
		this.line = line;
		this.column = column;
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

	private void token(Token.Type type, String text) {
		tokens.add(new Token(new Location(file, line, start), type, text));
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

	private void unexpected(char c) {
		//TODO
	}

	public void pushSource(char[] chars, int offset, int count) {
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
						case '*':
							token(Token.Type.STAR);
							break;
						case '_':
							buffer = new StringBuilder();
							buffer.append(c);
							state = State.NAME;
							break;
						default:
							if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
								buffer = new StringBuilder();
								buffer.append(c);
								state = State.NAME;
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
						token(Token.Type.NAME);
						state = State.NONE;
						--i;
						continue;
					}
					break;
				case QUOTED_NAME:
					//TODO
				default:
					throw new Doom("Unrecognized state: " + state.name());
			}
		}
	}

	public void endUnit() {
		//TODO
	}

}
