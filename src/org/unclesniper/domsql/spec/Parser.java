package org.unclesniper.domsql.spec;

import java.util.Arrays;
import java.util.BitSet;
import java.io.IOException;
import org.unclesniper.domsql.Doom;
import org.unclesniper.domsql.Location;
import org.unclesniper.domsql.PrimitiveType;
import org.unclesniper.domsql.spec.syntax.Utterance;
import org.unclesniper.domsql.spec.syntax.FieldType;
import org.unclesniper.domsql.spec.syntax.KeyMapping;
import org.unclesniper.domsql.spec.syntax.EnumDefinition;
import org.unclesniper.domsql.spec.syntax.ClassDefinition;
import org.unclesniper.domsql.spec.syntax.CompoundFieldType;
import org.unclesniper.domsql.spec.syntax.PrimitiveFieldType;

public class Parser {

	private static final class Expectation {

		private static final Token.Type[] TYPES = Token.Type.values();

		private static final int TYPE_COUNT = Expectation.TYPES.length;

		private final Token.Type[] types;

		private final BitSet acceptMask;

		public Expectation(Token.Type... types) {
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

	private static final Expectation EXP_DEFINITION_OR_END
			= new Expectation(Token.Type.ENUM, Token.Type.CLASS, null);

	private static final Expectation EXP_FIELD_DEFINITION = new Expectation(
		Token.Type.KEY, Token.Type.INVERSE,
		Token.Type.INT, Token.Type.LONG, Token.Type.DOUBLE, Token.Type.STRING,
		Token.Type.NAME
	);

	private static final Expectation EXP_FIELD_TYPE = new Expectation(
		Token.Type.INT, Token.Type.LONG, Token.Type.DOUBLE, Token.Type.STRING,
		Token.Type.NAME
	);

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

	private Token.Type expect(Token.Type... types) throws SpecSyntaxException {
		Token.Type haveType = token == null ? null : token.getType();
		for(int i = 0; i < types.length; ++i) {
			if(types[i] == haveType)
				return haveType;
		}
		if(token == null)
			throw new SpecSyntaxException(lexer == null ? null : lexer.getLocation(), types);
		else
			throw new SpecSyntaxException(token, types);
	}

	private Location consume(Token.Type... types) throws SpecSyntaxException, SpecLexicalException, IOException {
		expect(types);
		Location location = token.getLocation();
		next();
		return location;
	}

	private Token.Type consumeT(Token.Type... types) throws SpecSyntaxException, SpecLexicalException, IOException {
		Token.Type type = expect(types);
		next();
		return type;
	}

	public Utterance parseUtterance(boolean shouldPrepare)
			throws SpecSyntaxException, SpecLexicalException, IOException {
		if(shouldPrepare)
			next();
		Utterance utterance = new Utterance(null);
		Location location = null;
		for(;;) {
			Token.Type type = Parser.EXP_DEFINITION_OR_END.expect(this);
			if(type == null)
				break;
			if(location == null)
				location = token.getLocation();
			switch(type) {
				case ENUM:
					utterance.addDefinition(parseEnumDefinition());
					break;
				case CLASS:
					utterance.addDefinition(parseClassDefinition());
					break;
				default:
					throw new Doom("Unprocessed token type " + type.name());
			}
		}
		utterance.setLocation(location);
		return utterance;
	}

	private EnumDefinition parseEnumDefinition() throws SpecSyntaxException, SpecLexicalException, IOException {
		Location initiator = consume(Token.Type.ENUM);
		next();
		expect(Token.Type.NAME);
		String name = token.getText();
		next();
		consume(Token.Type.LEFT_ROUND);
		EnumTypeBase typeBase = consumeT(Token.Type.INT, Token.Type.STRING) == Token.Type.INT
				? EnumTypeBase.INT : EnumTypeBase.STRING;
		consume(Token.Type.RIGHT_ROUND);
		EnumDefinition enumDef = new EnumDefinition(initiator, name, typeBase);
		consume(Token.Type.LEFT_CURLY);
		for(;;) {
			expect(Token.Type.NAME);
			enumDef.addConstant(new EnumDefinition.Constant(token.getLocation(), token.getText()));
			next();
			switch(consumeT(Token.Type.COMMA, Token.Type.RIGHT_CURLY)) {
				case COMMA:
					if(token == null || token.getType() != Token.Type.RIGHT_CURLY)
						break;
					next();
				case RIGHT_CURLY:
					return enumDef;
			}
		}
	}

	private ClassDefinition parseClassDefinition() throws SpecSyntaxException, SpecLexicalException, IOException {
		Location initiator = consume(Token.Type.CLASS);
		next();
		expect(Token.Type.NAME);
		String name = token.getText();
		next();
		consume(Token.Type.EQUAL);
		expect(Token.Type.NAME);
		String table = token.getText();
		next();
		ClassDefinition.Supertype supertype;
		switch(expect(Token.Type.EXTENDS, Token.Type.LEFT_CURLY)) {
			case EXTENDS:
				{
					Location location = token.getLocation();
					next();
					expect(Token.Type.NAME);
					String stname = token.getText();
					next();
					supertype = new ClassDefinition.Supertype(location, stname);
					consume(Token.Type.LEFT_ROUND);
				  keyMappings:
					for(;;) {
						expect(Token.Type.NAME);
						String hereName = token.getText();
						Location hereLocation = token.getLocation();
						next();
						Location arrowLocation = consume(Token.Type.ARROW);
						expect(Token.Type.NAME);
						String thereName = token.getText();
						Location thereLocation = token.getLocation();
						next();
						supertype.addKeyMapping(new KeyMapping(hereName, hereLocation, arrowLocation,
								thereName, thereLocation));
						switch(consumeT(Token.Type.COMMA, Token.Type.RIGHT_ROUND)) {
							case COMMA:
								if(token == null || token.getType() != Token.Type.RIGHT_ROUND)
									break;
								next();
							case RIGHT_ROUND:
								break keyMappings;
						}
					}
				}
				consume(Token.Type.LEFT_CURLY);
				break;
			case LEFT_CURLY:
				supertype = null;
				next();
				break;
			default:
				return null;
		}
		ClassDefinition classDef = new ClassDefinition(initiator, name, table, supertype);
		do {
			ClassDefinition.FieldDirection fieldDirection;
			switch(Parser.EXP_FIELD_DEFINITION.expect(this)) {
				case KEY:
					fieldDirection = ClassDefinition.FieldDirection.KEY;
					next();
					break;
				case INVERSE:
					fieldDirection = ClassDefinition.FieldDirection.INVERSE;
					next();
					break;
				default:
					fieldDirection = ClassDefinition.FieldDirection.NORMAL;
					break;
			}
			FieldType type = parseFieldType();
			expect(Token.Type.NAME);
			Location location = token.getLocation();
			String fname = token.getText();
			next();
			consume(Token.Type.EQUAL);
			expect(Token.Type.NAME);
			String column = token.getText();
			next();
			classDef.addField(new ClassDefinition.FieldDefinition(location, fieldDirection, type, fname, column));
		} while(token != null && token.getType() != Token.Type.RIGHT_CURLY);
		next();
		return classDef;
	}

	private FieldType parseFieldType() throws SpecSyntaxException, SpecLexicalException, IOException {
		Parser.EXP_FIELD_TYPE.expect(this);
		Location location = token.getLocation();
		String name = token.getText();
		Token.Type ttype = token.getType();
		next();
		switch(ttype) {
			case INT:
				return new PrimitiveFieldType(location, PrimitiveType.INT);
			case LONG:
				return new PrimitiveFieldType(location, PrimitiveType.LONG);
			case DOUBLE:
				return new PrimitiveFieldType(location, PrimitiveType.DOUBLE);
			case STRING:
				return new PrimitiveFieldType(location, PrimitiveType.STRING);
			case NAME:
				break;
			default:
				return null;
		}
		Location starLocation;
		if(token != null && token.getType() == Token.Type.STAR) {
			starLocation = token.getLocation();
			next();
		}
		else
			starLocation = null;
		return new CompoundFieldType(location, name, starLocation);
	}

}
