package org.unclesniper.domsql;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;

public class StringDBEnum extends DBEnum {

	public static final class Constant extends AbstractConstant {

		private final String value;

		public Constant(String name, String value) {
			super(name);
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

	private final List<Constant> constants = new LinkedList<Constant>();

	private final Map<String, String> name2value = new HashMap<String, String>();

	private final Map<String, String> value2name = new HashMap<String, String>();

	public StringDBEnum(String name) {
		super(name);
	}

	public Iterable<Constant> getConstants() {
		return constants;
	}

	public void addConstant(Constant constant) {
		if(constant == null)
			return;
		String name = constant.getName();
		if(name2value.containsKey(name))
			throw new IllegalArgumentException("Duplicate constant name: " + name);
		constants.add(constant);
		String value = constant.getValue();
		name2value.put(name, value);
		if(!value2name.containsKey(value))
			value2name.put(value, name);
	}

	public String getNameByValue(String value) {
		return value2name.get(value);
	}

	public String getValueByName(String name) {
		return name2value.get(name);
	}

}
