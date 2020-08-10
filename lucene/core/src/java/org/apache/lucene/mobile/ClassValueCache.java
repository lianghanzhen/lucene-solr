package org.apache.lucene.mobile;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class ClassValueCache<T> {

	private final Map<Class<?>, T> map = new WeakHashMap<>();

	protected abstract T computeValue(Class<?> type);

	@SuppressWarnings("Java8MapApi")
	public T get(Class<?> type) {
		if (map.containsKey(type)) {
			return map.get(type);
		}

		T value = computeValue(type);

		synchronized (this) {
			if (!map.containsKey(type)) {
				map.put(type, value);
			} else {
				value = map.get(type);
			}
		}

		return value;
	}

}

