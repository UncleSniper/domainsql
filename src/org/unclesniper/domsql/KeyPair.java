package org.unclesniper.domsql;

public final class KeyPair {

	private final DBField here;

	private final DBField there;

	public KeyPair(DBField here, DBField there) {
		this.here = here;
		this.there = there;
	}

	public DBField getHere() {
		return here;
	}

	public DBField getThere() {
		return there;
	}

}
