package org.unclesniper.domsql.spec;

import org.unclesniper.domsql.Location;
import org.unclesniper.domsql.StringDBEnum;
import org.unclesniper.domsql.NamingContext;

public class StringEnumNamingContext extends NamingContext<StringDBEnum.Constant> {

	private StringDBEnum enumElement;

	public StringEnumNamingContext(StringDBEnum enumElement) {
		this.enumElement = enumElement;
	}

	public StringDBEnum getEnumElement() {
		return enumElement;
	}

	public void setEnumElement(StringDBEnum enumElement) {
		this.enumElement = enumElement;
	}

	public void addConstant(Location location, String name, String value) throws EnumConstantNameClashException {
		StringDBEnum.Constant constant = new StringDBEnum.Constant(name, value);
		Location previousDefinition = tryPut(name, location, constant);
		if(previousDefinition != null)
			throw new EnumConstantNameClashException(location, enumElement.getName(), name, previousDefinition);
		enumElement.addConstant(constant);
	}

}
