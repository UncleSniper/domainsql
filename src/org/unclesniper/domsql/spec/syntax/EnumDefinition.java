package org.unclesniper.domsql.spec.syntax;

import java.util.List;
import java.util.LinkedList;
import org.unclesniper.domsql.Doom;
import org.unclesniper.domsql.DBEnum;
import org.unclesniper.domsql.Location;
import org.unclesniper.domsql.IntDBEnum;
import org.unclesniper.domsql.StringDBEnum;
import org.unclesniper.domsql.spec.EnumTypeBase;
import org.unclesniper.domsql.spec.CompilationContext;
import org.unclesniper.domsql.spec.IntEnumNamingContext;
import org.unclesniper.domsql.spec.SpecSemanticsException;
import org.unclesniper.domsql.spec.StringEnumNamingContext;

public class EnumDefinition extends Definition {

	public static class Constant extends Syntax {

		private String name;

		private String stringValue;

		private int intValue;

		public Constant(Location location, String name, String stringValue, int intValue) {
			super(location);
			this.name = name;
			this.stringValue = stringValue;
			this.intValue = intValue;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getStringValue() {
			return stringValue;
		}

		public void setStringValue(String stringValue) {
			this.stringValue = stringValue;
		}

		public int getIntValue() {
			return intValue;
		}

		public void setIntValue(int intValue) {
			this.intValue = intValue;
		}

	}

	private String name;

	private EnumTypeBase typeBase;

	private final List<Constant> constants = new LinkedList<Constant>();

	public EnumDefinition(Location location, String name, EnumTypeBase typeBase) {
		super(location);
		this.name = name;
		this.typeBase = typeBase;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EnumTypeBase getTypeBase() {
		return typeBase;
	}

	public void setTypeBase(EnumTypeBase typeBase) {
		this.typeBase = typeBase;
	}

	public Iterable<Constant> getConstants() {
		return constants;
	}

	public void addConstant(Constant constant) {
		if(constant != null)
			constants.add(constant);
	}

	public void createElements(CompilationContext cctx) throws SpecSemanticsException {
		DBEnum type;
		switch(typeBase) {
			case INT:
				type = createIntEnum(cctx);
				break;
			case STRING:
				type = createStringEnum(cctx);
				break;
			default:
				throw new Doom("Unrecognized enum type base: " + typeBase.name());
		}
		cctx.getTypeNamingContext().put(type, getLocation());
	}

	private IntDBEnum createIntEnum(CompilationContext cctx) throws SpecSemanticsException {
		IntDBEnum enumElement = new IntDBEnum(name);
		IntEnumNamingContext naming = new IntEnumNamingContext(enumElement);
		for(Constant constant : constants)
			naming.addConstant(constant.getLocation(), constant.getName(), constant.getIntValue());
		return enumElement;
	}

	private StringDBEnum createStringEnum(CompilationContext cctx) throws SpecSemanticsException {
		StringDBEnum enumElement = new StringDBEnum(name);
		StringEnumNamingContext naming = new StringEnumNamingContext(enumElement);
		for(Constant constant : constants)
			naming.addConstant(constant.getLocation(), constant.getName(), constant.getStringValue());
		return enumElement;
	}

}
