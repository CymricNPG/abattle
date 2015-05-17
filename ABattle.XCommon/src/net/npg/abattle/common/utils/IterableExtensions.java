/**
 *
 */
package net.npg.abattle.common.utils;

import java.util.Iterator;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

/**
 * @author Cymric
 *
 */
public class IterableExtensions {

	private IterableExtensions() {
		//
	}

	public static <T> void removeConditional(Iterable<T> iterable,
			Function1<? super T, Boolean> predicate) {
		if (predicate == null) {
			throw new NullPointerException("predicate");
		}
		for (Iterator<T> it = iterable.iterator(); it.hasNext();) {
			if (predicate.apply(it.next())) {
				it.remove();
			}
		}
	}
}
