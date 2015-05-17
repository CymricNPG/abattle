/**
 *
 */
package net.npg.abattle.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cymric
 * 
 */
public class MyHashMap<K, V> extends HashMap<K, V> implements MyMap<K, V> {

	/**
	 *
	 */
	private static final long serialVersionUID = 7701194546291092280L;

	public MyHashMap(final int size) {
		super(size);
	}

	public MyHashMap() {
		super();
	}

	@Override
	public Map<K, V> getMap() {
		return this;
	}
}
