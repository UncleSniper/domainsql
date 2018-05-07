package org.unclesniper.domsql.spec;

import org.unclesniper.domsql.Location;
import org.unclesniper.domsql.SpecTypelike;
import org.unclesniper.domsql.NamingContext;

public class TypeNamingContext extends NamingContext<SpecTypelike> {

	public TypeNamingContext() {}

	public void put(SpecTypelike type, Location location) throws TypeNameClashException {
		Location previousDefinition = tryPut(type.getName(), location, type);
		if(previousDefinition != null)
			throw new TypeNameClashException(location, type.getName(), previousDefinition);
	}

}
