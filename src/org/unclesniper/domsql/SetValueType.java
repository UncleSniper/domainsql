package org.unclesniper.domsql;

public class SetValueType implements ValueType {

	private final DBType elementType;

	public SetValueType(DBType elementType) {
		this.elementType = elementType;
	}

	public TypeType type() {
		return TypeType.SET;
	}

	public PrimitiveType primitive() {
		return null;
	}

	public DBType compound() {
		return null;
	}

	public DBType set() {
		return elementType;
	}

}
