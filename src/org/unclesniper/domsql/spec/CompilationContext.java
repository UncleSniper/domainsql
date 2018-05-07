package org.unclesniper.domsql.spec;

public class CompilationContext {

	private final TypeNamingContext types = new TypeNamingContext();

	public CompilationContext() {}

	public TypeNamingContext getTypeNamingContext() {
		return types;
	}

}
