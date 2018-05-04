package org.unclesniper.domsql;

public class CompoundValueType implements ValueType {

	private final DBType type;

	public CompoundValueType(DBType type) {
		this.type = type;
	}

	public TypeType type() {
		return TypeType.COMPOUND;
	}

	public PrimitiveType primitive() {
		return null;
	}

	public DBType compound() {
		return type;
	}

	public DBType set() {
		return null;
	}

}
