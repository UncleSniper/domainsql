package org.unclesniper.domsql.spec;

import org.unclesniper.domsql.Location;
import org.unclesniper.domsql.IntDBEnum;
import org.unclesniper.domsql.NamingContext;

public class IntEnumNamingContext extends NamingContext<IntDBEnum.Constant> {

	private IntDBEnum enumElement;

	public IntEnumNamingContext(IntDBEnum enumElement) {
		this.enumElement = enumElement;
	}

	public IntDBEnum getEnumElement() {
		return enumElement;
	}

	public void setEnumElement(IntDBEnum enumElement) {
		this.enumElement = enumElement;
	}

	public void addConstant(Location location, String name, int value) throws EnumConstantNameClashException {
		IntDBEnum.Constant constant = new IntDBEnum.Constant(name, value);
		Location previousDefinition = tryPut(name, location, constant);
		if(previousDefinition != null)
			throw new EnumConstantNameClashException(location, enumElement.getName(), name, previousDefinition);
		enumElement.addConstant(constant);
	}

}
