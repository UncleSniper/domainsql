package org.unclesniper.domsql;

public final class Location {

	public static final Location UNKNOWN = new Location(null, 0, 0);

	private final String file;

	private final int line;

	private final int column;

	public Location(String file, int line, int column) {
		this.file = file;
		this.line = line;
		this.column = column;
	}

	public String getFile() {
		return file;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public String toString() {
		if(file == null)
			return "<unknown location>";
		StringBuilder builder = new StringBuilder(file);
		if(line <= 0)
			builder.append(":<unknown line>");
		else {
			builder.append(':');
			builder.append(String.valueOf(line));
			if(column > 0) {
				builder.append(':');
				builder.append(String.valueOf(column));
			}
		}
		return builder.toString();
	}

}
