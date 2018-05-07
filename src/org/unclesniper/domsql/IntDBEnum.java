package org.unclesniper.domsql;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;

public class IntDBEnum extends DBEnum {

	public static final class Constant extends AbstractConstant {

		private final int value;

		public Constant(String name, int value) {
			super(name);
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	private final List<Constant> constants = new LinkedList<Constant>();

	private final Map<String, Integer> name2value = new HashMap<String, Integer>();

	private final Map<Integer, String> value2name = new HashMap<Integer, String>();

	public IntDBEnum(String name) {
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
		int value = constant.getValue();
		name2value.put(name, value);
		if(!value2name.containsKey(value))
			value2name.put(value, name);
	}

	public String getNameByValue(int value) {
		return value2name.get(value);
	}

	public Integer getValueByName(String name) {
		return name2value.get(name);
	}

}
