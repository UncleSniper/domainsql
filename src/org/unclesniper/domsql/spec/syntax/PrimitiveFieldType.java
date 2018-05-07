package org.unclesniper.domsql.spec.syntax;

import org.unclesniper.domsql.Location;
import org.unclesniper.domsql.PrimitiveType;

public class PrimitiveFieldType extends FieldType {

	private PrimitiveType type;

	public PrimitiveFieldType(Location location, PrimitiveType type) {
		super(location);
		this.type = type;
	}

	public PrimitiveType getType() {
		return type;
	}

	public void setType(PrimitiveType type) {
		this.type = type;
	}

}
