/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.utils;

import net.npg.abattle.client.ClientConstants;

/**
 * @author cymric
 * 
 */
public class Landscape {

	private static class InitVisitor extends FieldVisitor {
		public int count = 0;
		private final int[][] field;

		public InitVisitor(final int[][] field) {
			this.field = field;
		}

		@Override
		public void visit(final int x, final int y) {
			field[x][y] = -1;
			count++;
		}

	}

	public static void fillField(final int[][] field, final int xsize, final int ysize, final int maxHeight) {
		FieldLoop.visitAllFields(xsize, ysize, new FieldVisitor() {
			@Override
			public void visit(final int x, final int y) {
				field[x][y] = (int) (Math.random() * maxHeight);
			}
		});

	}

	public static void initField(final int[][] field, final int xsize, final int ysize) {

		final InitVisitor delegate = new InitVisitor(field);
		FieldLoop.visitAllFields(xsize, ysize, delegate);
		if (xsize * ysize != delegate.count) {
			throw new RuntimeException();
		}
	}

	public static void printField(final int[][] field, final int xsize, final int ysize) {
		FieldLoop.visitAllFields(xsize, ysize, new FieldVisitor() {
			private String line = "";

			@Override
			public void visit(final int x, final int y) {
				line = line + (line.isEmpty() ? "" : ",") + field[x][y];
			}

			@Override
			public void visitRowEnd(final int y) {
				ClientConstants.LOG.debug(line);
			}

			@Override
			public void visitRowStart(final int y) {
				line = "";
			}
		});
	}
}
