/**
 *
 */
package net.npg.abattle.common.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cymric
 * 
 */
public class MyConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> implements MyMap<K, V> {

	/**
	 *
	 */
	private static final long serialVersionUID = -999743253644587445L;

	@Override
	public Map<K, V> getMap() {
		return this;
	}

}
