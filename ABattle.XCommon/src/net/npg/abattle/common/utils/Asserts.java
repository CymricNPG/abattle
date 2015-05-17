/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.utils;

import org.eclipse.xtext.xbase.lib.Inline;

/**
 * @author Cymric
 * 
 */
public class Asserts {

	@Inline(value = "assert $1", statementExpression = false)
	public static void assertIt(final boolean condition) {
		throw new UnsupportedOperationException();
	}
	
	@Inline(value = "assert $1 != null", statementExpression = false)
	public static void assertNotNull(final Object object) {
		throw new UnsupportedOperationException();
	}
}
