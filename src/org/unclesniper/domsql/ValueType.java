package org.unclesniper.domsql;

public interface ValueType {

	public enum TypeType {
		PRIMITIVE,
		COMPOUND,
		SET
	}

	TypeType type();

	PrimitiveType primitive();

	DBType compound();

	DBType set();

}
