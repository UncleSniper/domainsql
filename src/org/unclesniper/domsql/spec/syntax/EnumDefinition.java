package org.unclesniper.domsql.spec.syntax;

import java.util.List;
import java.util.LinkedList;
import org.unclesniper.domsql.Location;
import org.unclesniper.domsql.spec.EnumTypeBase;

public class EnumDefinition extends Definition {

	public static class Constant extends Syntax {

		private String name;

		public Constant(Location location, String name) {
			super(location);
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
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

}
