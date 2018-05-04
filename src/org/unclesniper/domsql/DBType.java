package org.unclesniper.domsql;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;

public class DBType {

	public static class Supertype {

		private final DBType type;

		private final List<KeyPair> keyPairs = new LinkedList<KeyPair>();

		public Supertype(DBType type) {
			this.type = type;
		}

		public DBType getType() {
			return type;
		}

		public Iterable<KeyPair> getKeyPairs() {
			return keyPairs;
		}

		public void addKeyPair(DBField here, DBField there) {
			here.getName();
			there.getName();
			keyPairs.add(new KeyPair(here, there));
		}

	}

	private final String name;

	private final String table;

	private final Map<String, DBField> fields = new HashMap<String, DBField>();

	private final Supertype supertype;

	private final List<DBField> primaryKey = new LinkedList<DBField>();

	public DBType(String name, String table) {
		this(name, table, (Supertype)null);
	}

	public DBType(String name, String table, Supertype supertype) {
		this.name = name;
		this.table = table;
		this.supertype = supertype;
	}

	public DBType(String name, String table, DBType supertype) {
		this(name, table, supertype == null ? null : new Supertype(supertype));
	}

	public String getName() {
		return name;
	}

	public String getTable() {
		return table;
	}

	public Iterable<DBField> getFields() {
		return fields.values();
	}

	public DBField getField(String fn) {
		return fields.get(fn);
	}

	public void addField(DBField field) {
		String fn = field.getName();
		if(fn == null)
			throw new NullPointerException("Cowardly refusing to add field with null name");
		if(fields.containsKey(fn))
			throw new IllegalArgumentException("DB type '" + name + "' already has a field named '" + fn + '\'');
		field.setEnclosingType(this);
		fields.put(fn, field);
	}

	public Supertype getSupertype() {
		return supertype;
	}

	public Iterable<DBField> getPrimaryKey() {
		return primaryKey;
	}

	public void addPrimaryKey(DBField field) {
		field.getName();
		DBType enclosingType = field.getEnclosingType();
		if(enclosingType != this)
			throw new IllegalArgumentException("Cannot add foreign field '" + (enclosingType == null
					? "" : enclosingType.getName() + '.') + field.getName() + "' to primary key of '"
					+ name + '\'');
		if(!primaryKey.contains(field))
			primaryKey.add(field);
	}

}
