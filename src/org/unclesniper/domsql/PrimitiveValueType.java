package org.unclesniper.domsql;

public class PrimitiveValueType implements ValueType {

	private final PrimitiveType type;

	public PrimitiveValueType(PrimitiveType type) {
		this.type = type;
	}

	public TypeType type() {
		return TypeType.PRIMITIVE;
	}

	public PrimitiveType primitive() {
		return type;
	}

	public DBType compound() {
		return null;
	}

	public DBType set() {
		return null;
	}

}
