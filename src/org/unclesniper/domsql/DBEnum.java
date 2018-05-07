package org.unclesniper.domsql;

public abstract class DBEnum implements SpecTypelike {

	public static abstract class AbstractConstant {

		private final String name;

		public AbstractConstant(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	private final String name;

	public DBEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
