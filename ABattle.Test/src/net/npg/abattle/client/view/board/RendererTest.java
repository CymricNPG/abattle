/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Properties;

import net.npg.abattle.client.model.MockFactory;
import net.npg.abattle.client.view.boardscene.BoardViewModel;
import net.npg.abattle.client.view.boardscene.CellTextShape;
import net.npg.abattle.client.view.boardscene.CircleShape;
import net.npg.abattle.client.view.boardscene.ComplexBarShape;
import net.npg.abattle.client.view.boardscene.CursorHexShape;
import net.npg.abattle.client.view.boardscene.ErrorMessage;
import net.npg.abattle.client.view.boardscene.FightShape;
import net.npg.abattle.client.view.boardscene.FilledCircleShape;
import net.npg.abattle.client.view.boardscene.FilledHexShape;
import net.npg.abattle.client.view.boardscene.HexShape;
import net.npg.abattle.client.view.boardscene.LayerModel;
import net.npg.abattle.client.view.boardscene.ModelBuilder;
import net.npg.abattle.client.view.boardscene.OutgoingLinkShape;
import net.npg.abattle.client.view.boardscene.SceneRenderer;
import net.npg.abattle.client.view.boardscene.Shape;
import net.npg.abattle.client.view.selection.twocells.Cursor;
import net.npg.abattle.common.configuration.GraphicsConfigurationData;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.impl.GameConfigurationImpl;

import org.junit.Test;

/**
 * @author cymric
 * 
 */
public class RendererTest {

	private class MyRenderer implements SceneRenderer {

		boolean circleShapeVisited = false;

		int count = 0;

		boolean cursorHexVisited = false;

		boolean fightShapeVisited = false;

		boolean filledCircleShapeVisited = false;

		boolean filledHexShapeVisited = false;

		boolean hexShapeVisited = false;

		boolean shapeVisited = false;

		private void assertShape(final Shape shape) {
			assertNotNull(shape);
			assertNotNull(shape.getHex());
			shapeVisited = true;
		}

		@Override
		public void visit(final BoardViewModel board) {
			for (final LayerModel layer : board.getLayers()) {
				layer.accept(this);
			}
		}

		@Override
		public void visit(final CellTextShape cellTextShape) {
			assertShape(cellTextShape);

		}

		@Override
		public void visit(final CircleShape circleShape) {
			assertShape(circleShape);
			circleShapeVisited = true;
		}

		@Override
		public void visit(final CursorHexShape cursorHexShape) {
			assertShape(cursorHexShape);
			cursorHexVisited = true;
		}

		@Override
		public void visit(final FightShape fightShape) {
			assertShape(fightShape);
			fightShapeVisited = true;
		}

		@Override
		public void visit(final FilledCircleShape filledCircleShape) {
			assertShape(filledCircleShape);
			filledCircleShapeVisited = true;
		}

		@Override
		public void visit(final FilledHexShape filledHexShape) {
			count++;
			assertShape(filledHexShape);
			filledHexShapeVisited = true;
		}

		@Override
		public void visit(final HexShape hexShape) {
			hexShapeVisited = true;
			assertShape(hexShape);
		}

		@Override
		public void visit(final LayerModel layer) {
			for (final Shape shape : layer.getShapes()) {
				shape.accept(this);
			}
		}

		@Override
		public void visit(final OutgoingLinkShape outgoingLinkShape) {
			assertShape(outgoingLinkShape);
		}

		@Override
		public void visit(final ComplexBarShape complexBarShape) {

		}

		@Override
		public void visit(final ErrorMessage errorMessage) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * Test method for
	 * {@link net.npg.abattle.client.view.boardscene.SceneRenderer#render(net.npg.abattle.client.view.boardscene.BoardViewModel)}
	 * .
	 * 
	 * @throws BaseException
	 */
	@Test
	public final void testRenderBoardModel() throws BaseException {
		final ModelBuilder modelBuilder = new ModelBuilder(new HexBase(1.0f), new GraphicsConfigurationData(new Properties()), new GameConfigurationImpl());
		final ClientGame game = MockFactory.createSimpleRandomGame();
		final List<Cursor> cursors = MockFactory.createCursors((ClientBoard) game.getBoard());
		final BoardViewModel boardModel = modelBuilder.createBoardModel(game, cursors);
		final MyRenderer renderer = new MyRenderer();
		renderer.visit(boardModel);
		assertEquals(MockFactory.BOARD_X_SIZE * MockFactory.BOARD_Y_SIZE, renderer.count);
		assertTrue(renderer.circleShapeVisited);
		assertTrue(renderer.cursorHexVisited);
		assertTrue(renderer.fightShapeVisited);
		assertTrue(renderer.filledCircleShapeVisited);
		assertTrue(renderer.filledHexShapeVisited);
		assertTrue(renderer.hexShapeVisited);
		// assertFalse(renderer.outgoingLinkShapeVisited);
		assertTrue(renderer.shapeVisited);
		// assertTrue(renderer.cellTextShapeVisited);
	}

}
