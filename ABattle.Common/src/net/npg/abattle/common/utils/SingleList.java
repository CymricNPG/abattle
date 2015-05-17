/**
 *
 */
package net.npg.abattle.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @author Cymric
 * 
 */
public class SingleList<T> extends ArrayList<T> {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	private static Cache cache;

	private SingleList(final T element) {
		super();
		add(element);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> create(final T element) {
		if (cache == null) {
			buildCache();
		}
		if (element == null) {
			return Collections.emptyList();
		}
		final List<T> list = (List<T>) cache.getIfPresent(element);
		if (list != null) {
			return list;
		}
		final List<T> newList = new SingleList<T>(element);
		cache.put(element, newList);
		return newList;
	}

	private static synchronized void buildCache() {
		if (cache != null) {
			return;
		}
		cache = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(10, TimeUnit.MINUTES).build();
	}
}
