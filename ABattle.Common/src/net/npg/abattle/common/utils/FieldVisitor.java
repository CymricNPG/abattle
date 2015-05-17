/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.utils;

/**
 * (not a correct visitor pattern!)
 * 
 * @author cymric
 * 
 */
public abstract class FieldVisitor {

	public void visit(final int x, final int y) {
		// nothing to do
	}

	public void visitRowEnd(final int y) {
		// nothing to do
	}

	public void visitRowStart(final int y) {
		// nothing to do
	}
}
