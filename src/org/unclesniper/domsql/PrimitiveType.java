package org.unclesniper.domsql;

public enum PrimitiveType {

	INT,
	LONG,
	DOUBLE,
	STRING;

	public final PrimitiveValueType valueType = new PrimitiveValueType(this);

}
