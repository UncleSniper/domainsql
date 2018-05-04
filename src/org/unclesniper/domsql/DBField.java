package org.unclesniper.domsql;

public class DBField {

	private final String name;

	private final String column;

	private final ValueType type;

	private final boolean inverse;

	private DBType enclosingType;

	public DBField(String name, String column, ValueType type) {
		this.name = name;
		this.column = column;
		this.type = type;
		inverse = type.type() == ValueType.TypeType.SET;
	}

	public DBField(String name, String column, ValueType type, boolean inverse) {
		this.name = name;
		this.column = column;
		this.type = type;
		this.inverse = inverse;
		if(inverse && type.type() == ValueType.TypeType.PRIMITIVE)
			throw new IllegalArgumentException("Primitive column cannot be inverse: " + name);
	}

	public String getName() {
		return name;
	}

	public String getColumn() {
		return column;
	}

	public ValueType getType() {
		return type;
	}

	public boolean isInverse() {
		return inverse;
	}

	public DBType getEnclosingType() {
		return enclosingType;
	}

	public void setEnclosingType(DBType enclosingType) {
		if(enclosingType == null || enclosingType == this.enclosingType)
			return;
		if(this.enclosingType != null)
			throw new IllegalArgumentException("Cannot assign field '" + name + "' to type '"
					+ enclosingType.getName() + "', as it is already assigned to '" + this.enclosingType.getName()
					+ '\'');
		this.enclosingType = enclosingType;
	}

}
