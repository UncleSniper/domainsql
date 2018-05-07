package org.unclesniper.domsql.spec.syntax;

import java.util.List;
import java.util.LinkedList;
import org.unclesniper.domsql.Location;

public class Utterance extends Syntax {

	private final List<Definition> definitions = new LinkedList<Definition>();

	public Utterance(Location location) {
		super(location);
	}

	public Iterable<Definition> getDefinitions() {
		return definitions;
	}

	public void addDefinition(Definition definition) {
		if(definition != null)
			definitions.add(definition);
	}

}
