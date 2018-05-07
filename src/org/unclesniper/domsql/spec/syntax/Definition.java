package org.unclesniper.domsql.spec.syntax;

import org.unclesniper.domsql.Location;
import org.unclesniper.domsql.spec.CompilationContext;
import org.unclesniper.domsql.spec.SpecSemanticsException;

public abstract class Definition extends Syntax {

	public Definition(Location location) {
		super(location);
	}

	public abstract void createElements(CompilationContext cctx) throws SpecSemanticsException;

}
