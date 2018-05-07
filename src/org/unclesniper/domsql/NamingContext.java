package org.unclesniper.domsql;

import java.util.Map;
import java.util.HashMap;

public abstract class NamingContext<E> {

	public static final class Binding<E> {

		private final String key;

		private Location location;

		private E value;

		public Binding(String key, Location location, E value) {
			this.key = key;
			this.location = location;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}

		public E getValue() {
			return value;
		}

		public void setValue(E value) {
			this.value = value;
		}

	}

	protected final Map<String, Binding<E>> bindings = new HashMap<String, Binding<E>>();

	public NamingContext() {}

	public E get(String name) {
		Binding<E> binding = bindings.get(name);
		return binding == null ? null : binding.getValue();
	}

	public Binding<E> getBinding(String name) {
		return bindings.get(name);
	}

	protected Location tryPut(String key, Location location, E value) {
		Binding<E> binding = bindings.get(key);
		if(binding != null)
			return binding.getLocation();
		bindings.put(key, new Binding<E>(key, location == null ? Location.UNKNOWN : location, value));
		return null;
	}

}
