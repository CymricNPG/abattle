/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.utils;

import java.util.Collection;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * @author cymric
 *
 */
public class Validate {

	public static void notEmpty(final Collection<?> collection) {
		Validate.notNull(collection);
		Validate.isTrue(!collection.isEmpty());
		for (final Object o : collection) {
			Validate.notNull(o);
		}
	}

	public static void isTrue(final boolean condition) {
		Preconditions.checkArgument(condition);
	}

	public static void notNull(final Object test) {
		Preconditions.checkNotNull(test);
	}

	public static void notNulls(final Object arg0) {
		notNull(arg0);
	}

	/**
	 * needed because ...args allocates an aray
	 *
	 * @param arg0
	 * @param arg1
	 */
	public static void notNulls(final Object arg0, final Object arg1) {
		notNull(arg0);
		notNull(arg1);
	}

	public static void notNulls(final Object arg0, final Object arg1, final Object arg2) {
		notNull(arg0);
		notNull(arg1);
		notNull(arg2);
	}

	public static void notNulls(final Object arg0, final Object arg1, final Object arg2, final Object arg3) {
		notNull(arg0);
		notNull(arg1);
		notNull(arg2);
		notNull(arg3);
	}

	public static void notNulls(final Object arg0, final Object arg1, final Object arg2, final Object arg3, final Object... args) {
		notNulls(arg0, arg1, arg2, arg3);
		for (final Object arg : args) {
			notNull(arg);
		}
	}

	public static void notZero(final int value) {
		if (value == 0) {
			throw new IllegalArgumentException("Value was null!");
		}
	}

	public static void inclusiveBetween(final int min, final int max, final int test) {
		if (test >= min && test <= max) {
			return;
		}
		throw new IllegalArgumentException("Min:" + min + " Max:" + max + " Test:" + test);
	}

	public static void notBlank(final String test) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(test));
	}

	public static void exclusiveBetween(final int min, final int max, final int test) {
		if (test > min && test < max) {
			return;
		}
		throw new IllegalArgumentException("Min:" + min + " Max:" + max + " Test:" + test);
	}

	public static void noNullElements(final Object[] array) {
		notNull(array);
		for (final Object test : array) {
			notNull(test);
		}
	}

	public static void isNull(final Object obj) {
		if (obj != null) {
			throw new IllegalArgumentException("Object was not null:" + obj.toString());
		}
	}

	public static void isFalse(final boolean condition) {
		Preconditions.checkArgument(!condition);
	}
}
