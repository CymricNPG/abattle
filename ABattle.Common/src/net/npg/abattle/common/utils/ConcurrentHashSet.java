/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cymric
 * 
 */
public class ConcurrentHashSet<K> implements Set<K> {

	private final Set<K> delegate;

	@SuppressWarnings("unchecked")
	public ConcurrentHashSet() {
		this.delegate = (Set<K>) Collections.newSetFromMap(new ConcurrentHashMap<Object, Boolean>());
	}

	@Override
	public boolean add(final K e) {
		return delegate.add(e);
	}

	@Override
	public boolean addAll(final Collection<? extends K> c) {
		return delegate.addAll(c);
	}

	@Override
	public void clear() {
		delegate.clear();
	}

	@Override
	public boolean contains(final Object o) {
		return delegate.contains(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return delegate.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public Iterator<K> iterator() {
		return delegate.iterator();
	}

	@Override
	public boolean remove(final Object o) {
		return delegate.remove(o);
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		return delegate.removeAll(c);
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		return delegate.retainAll(c);
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public Object[] toArray() {
		return delegate.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return delegate.toArray(a);
	}

}
