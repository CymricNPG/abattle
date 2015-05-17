/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.utils;

import com.google.common.base.Strings;

/**
 * @author Cymric
 * 
 */
public class StringUtils {

	/**
	 * Convert string without exception.
	 * 
	 * @param string the string
	 * @return the integer or null if not convertible
	 */
	public static Integer convert(final String string) {
		if (Strings.isNullOrEmpty(string)) {
			return null;
		}
		final String plain = string.trim();
		if (!plain.matches("\\d+") || plain.length() > 3) {
			return null;
		}
		return Integer.parseInt(plain);
	}
}
