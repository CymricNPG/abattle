/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import net.npg.abattle.client.model.MockFactory;
import net.npg.abattle.common.hex.Directions;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.Cell;
import net.npg.abattle.common.model.client.impl.ClientBoardImpl;

import org.junit.Test;

/**
 * @author cymric
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BoardImplTest {

	private void assertNoAdjacentCell(final Board board, final Cell cell, final Directions direction) {
		final Cell result = board.getAdjacentCell(cell, direction);
		if (result != null) {
			fail("Expected null, but got:" + result.toString());
		}
	}

	private void assertNotNorthEven(final Board board, final int x, final int y) {
		final Cell cell = board.getCellAt(x, y);
		assertNoAdjacentCell(board, cell, Directions.N);
		assertNoAdjacentCell(board, cell, Directions.NE);
		assertNoAdjacentCell(board, cell, Directions.NW);
	}

	private void assertNotNorthOdd(final Board board, final int x, final int y) {
		final Cell cell = board.getCellAt(x, y);
		assertNoAdjacentCell(board, cell, Directions.N);
		assertNoAdjacentCell(board, cell, Directions.NE);
	}

	private void assertNotSouthEven(final Board board, final int x, final int y) {
		final Cell cell = board.getCellAt(x, y);
		assertNoAdjacentCell(board, cell, Directions.S);
		assertNoAdjacentCell(board, cell, Directions.SW);
	}

	private void assertNotSouthOdd(final Board board, final int x, final int y) {
		final Cell cell = board.getCellAt(x, y);
		assertNoAdjacentCell(board, cell, Directions.S);
		assertNoAdjacentCell(board, cell, Directions.SE);
		assertNoAdjacentCell(board, cell, Directions.SW);
	}

	private void expectException(final Board board, final int x, final int y) {
		try {
			board.getCellAt(x, y);
			fail("Expected an exception");
		} catch (final ArrayIndexOutOfBoundsException e) {
			// expected;
		}
	}

	/**
	 * Test method for {@link net.npg.abattle.common.model.impl.BoardImpl#BoardImpl(int, int)}.
	 */
	@Test
	public final void testBoardImpl() {
		final Board board = new ClientBoardImpl(42, 32);
		assertEquals(32, board.getYSize());
		assertEquals(42, board.getXSize());
	}

	/**
	 * Test method for
	 * {@link net.npg.abattle.common.model.impl.BoardImpl#getAdjacentCell(net.npg.abattle.common.model.Cell, net.npg.abattle.common.hex.Directions)}
	 * .
	 */
	@Test
	public final void testGetAdjacentCell() {
		final Board board = MockFactory.createBoard(MockFactory.createPlayer());
		final int xleft = 0;
		final int ytop = 0;
		final int xright = MockFactory.BOARD_X_SIZE - 1;
		final int ybottom = MockFactory.BOARD_Y_SIZE - 1;

		// works only with these sizes!
		assertEquals(16, xright);
		assertEquals(16, ybottom);

		assertNotNorthEven(board, xleft, ytop);
		assertNoAdjacentCell(board, board.getCellAt(xleft, ytop), Directions.SW);

		assertNotNorthOdd(board, xright, ytop);
		assertNoAdjacentCell(board, board.getCellAt(xright, ytop), Directions.SE);

		assertNotSouthEven(board, xleft, ybottom);
		assertNoAdjacentCell(board, board.getCellAt(xleft, ybottom), Directions.NW);

		assertNotSouthOdd(board, xright - 1, ybottom);
		assertNoAdjacentCell(board, board.getCellAt(xright, ybottom), Directions.NE);

	}

	/**
	 * Test method for {@link net.npg.abattle.common.model.impl.BoardImpl#getCellAt(int, int)}.
	 */
	@Test
	public final void testGetCellAt() {
		final Board board = MockFactory.createBoard(MockFactory.createPlayer());
		assertNotNull(board.getCellAt(0, 0));
		expectException(board, -1, 0);
		expectException(board, -1, -1);
		expectException(board, 0, -1);
		expectException(board, MockFactory.BOARD_X_SIZE, 0);
		expectException(board, MockFactory.BOARD_X_SIZE, MockFactory.BOARD_Y_SIZE);
		expectException(board, 0, MockFactory.BOARD_Y_SIZE);
	}
}
