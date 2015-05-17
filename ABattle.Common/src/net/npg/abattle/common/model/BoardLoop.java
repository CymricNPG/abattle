/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model;

import java.util.ArrayList;
import java.util.List;

import net.npg.abattle.common.utils.FieldLoop;
import net.npg.abattle.common.utils.FieldVisitor;

/**
 * Visit all cells on athe board
 * 
 * @author cymric
 * 
 */
public final class BoardLoop {

	/**
	 * @param board
	 * @deprecated
	 */
	@Deprecated
	@SuppressWarnings("rawtypes")
	public static Iterable<Cell> visitAllCells(final Board board) {
		final List<Cell> cells = new ArrayList<Cell>();
		FieldLoop.visitAllFields(board.getXSize(), board.getYSize(), new FieldVisitor() {
			@Override
			public void visit(final int x, final int y) {
				cells.add(board.getCellAt(x, y));
			}
		});
		return cells;
	}

	@SuppressWarnings("rawtypes")
	public static <PLAYERTYPE extends Player, CELLTYPE extends Cell> void visitAllCells(final Board board, final CellVisitor delegate) {
		FieldLoop.visitAllFields(board.getXSize(), board.getYSize(), new FieldVisitor() {
			@SuppressWarnings("unchecked")
			@Override
			public void visit(final int x, final int y) {
				delegate.visitCell(board.getCellAt(x, y));
			}
		});
	}

	private BoardLoop() {

	}
}
