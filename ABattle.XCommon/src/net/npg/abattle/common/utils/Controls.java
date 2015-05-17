/**
 *
 */
package net.npg.abattle.common.utils;

import org.eclipse.xtext.xbase.lib.Inline;

/**
 * @author Cymric
 *
 */
public class Controls {

	@Inline(value = "if($1) return;", statementExpression = false)
	public static void returnif(final boolean condition) {
		throw new UnsupportedOperationException();
	}

	@Inline(value = "if(!$1) return;", statementExpression = false)
	public static void returnifNot(final boolean condition) {
		throw new UnsupportedOperationException();
	}

	@Inline(value = "if($1) break;", statementExpression = false)
	public static void breakif(final boolean condition) {
		throw new UnsupportedOperationException();
	}
	
	@Inline(value = "if($1) continue;", statementExpression = false)
	public static void continueif(final boolean condition) {
		throw new UnsupportedOperationException();
	}
}
