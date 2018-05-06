package org.unclesniper.domsql.spec;

import java.util.Arrays;
import java.util.BitSet;
import java.io.IOException;
import org.unclesniper.domsql.spec.syntax.Utterance;

public class Parser {

	private static final class Expectation {

		private static final Token.Type[] TYPES = Token.Type.values();

		private static final int TYPE_COUNT = Expectation.TYPES.length;

		private final Token.Type[] types;

		private final BitSet acceptMask;

		public Expectation(Token.Type[] types) {
			this.types = Arrays.copyOf(types, types.length);
			acceptMask = new BitSet(Expectation.TYPE_COUNT + 1);
			for(Token.Type type : types)
				acceptMask.set(type == null ? Expectation.TYPE_COUNT : type.ordinal());
		}

		public Token.Type expect(Parser parser) throws SpecSyntaxException {
			Token.Type haveType = parser.token == null ? null : parser.token.getType();
			if(!acceptMask.get(haveType == null ? Expectation.TYPE_COUNT : haveType.ordinal())) {
				if(parser.token == null)
					throw new SpecSyntaxException(parser.lexer == null ? null : parser.lexer.getLocation(), types);
				else
					throw new SpecSyntaxException(parser.token, types);
			}
			return haveType;
		}

	}

	private Lexer lexer;

	private Token token;

	public Parser(Lexer lexer) {
		this.lexer = lexer;
	}

	public Lexer getLexer() {
		return lexer;
	}

	public void setLexer(Lexer lexer) {
		this.lexer = lexer;
	}

	public void prepare() throws SpecLexicalException, IOException {
		next();
	}

	private void next() throws SpecLexicalException, IOException {
		token = lexer == null ? null : lexer.nextToken();
	}

	private void noEnd(String expectation) throws SpecSyntaxException {
		if(token == null)
			throw new SpecSyntaxException(lexer == null ? null : lexer.getLocation(), expectation);
	}

	private void noEnd(Token.Type... expectation) throws SpecSyntaxException {
		if(token == null)
			throw new SpecSyntaxException(lexer == null ? null : lexer.getLocation(), expectation);
	}

	public Utterance parseUtterance(boolean shouldPrepare)
			throws SpecSyntaxException, SpecLexicalException, IOException {
		if(shouldPrepare)
			next();
		//TODO
		return null;
	}

}
