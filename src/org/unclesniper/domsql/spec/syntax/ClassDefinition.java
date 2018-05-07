package org.unclesniper.domsql.spec.syntax;

import java.util.List;
import java.util.LinkedList;
import org.unclesniper.domsql.Location;
import org.unclesniper.domsql.spec.CompilationContext;
import org.unclesniper.domsql.spec.SpecSemanticsException;

public class ClassDefinition extends Definition {

	public static class Supertype extends Syntax {

		private String name;

		private final List<KeyMapping> keyMappings = new LinkedList<KeyMapping>();

		public Supertype(Location location, String name) {
			super(location);
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Iterable<KeyMapping> getKeyMappings() {
			return keyMappings;
		}

		public void addKeyMapping(KeyMapping keyMapping) {
			if(keyMapping != null)
				keyMappings.add(keyMapping);
		}

	}

	public enum FieldDirection {
		KEY,
		NORMAL,
		INVERSE
	}

	public static class FieldDefinition extends Syntax {

		private FieldDirection direction;

		private FieldType type;

		private String name;

		private String column;

		public FieldDefinition(Location location, FieldDirection direction,
				FieldType type, String name, String column) {
			super(location);
			this.direction = direction;
			this.type = type;
			this.name = name;
			this.column = column;
		}

		public FieldDirection getDirection() {
			return direction;
		}

		public void setDirection(FieldDirection direction) {
			this.direction = direction;
		}

		public FieldType getType() {
			return type;
		}

		public void setType(FieldType type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getColumn() {
			return column;
		}

		public void setColumn(String column) {
			this.column = column;
		}

	}

	private String name;

	private String table;

	private Supertype supertype;

	private final List<FieldDefinition> fields = new LinkedList<FieldDefinition>();

	public ClassDefinition(Location location, String name, String table, Supertype supertype) {
		super(location);
		this.name = name;
		this.table = table;
		this.supertype = supertype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Supertype getSupertype() {
		return supertype;
	}

	public void setSupertype(Supertype supertype) {
		this.supertype = supertype;
	}

	public Iterable<FieldDefinition> getFields() {
		return fields;
	}

	public void addField(FieldDefinition field) {
		if(field != null)
			fields.add(field);
	}

	public void createElements(CompilationContext cctx) throws SpecSemanticsException {
		//TODO
	}

}
