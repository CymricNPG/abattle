/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.utils;

import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

/**
 * @author cymric
 * 
 */
public class FieldLoop {

	public static void visitAllFields(final int xsize, final int ysize, final FieldVisitor delegate) {
		for (int y = 0; y < ysize; y++) {
			delegate.visitRowStart(y);
			for (int x = 0; x < xsize; x++) {
				delegate.visit(x, y);
			}
			delegate.visitRowEnd(y);
		}
	}

	public static void visitAllFields(final IntPoint size, final Procedure2<Integer, Integer> delegate) {
		visitAllFields(size.x, size.y, delegate);
	}

	public static void visitAllFields(final int xsize, final int ysize, final Procedure2<Integer, Integer> delegate) {
		for (int y = 0; y < ysize; y++) {
			for (int x = 0; x < xsize; x++) {
				delegate.apply(x, y);
			}
		}
	}

}
