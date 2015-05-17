/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Properties;

import net.npg.abattle.client.model.MockFactory;
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
public class ModelBuilderTest {

	/**
	 * Test method for
	 * {@link net.npg.abattle.client.view.boardscene.ModelBuilder#createBoardModel(net.npg.abattle.model.Board)}.
	 * 
	 * @throws BaseException
	 */
	@Test
	public final void testCreateBoardModel() throws BaseException {
		final ModelBuilder modelBuilder = new ModelBuilder(new HexBase(1.0f), new GraphicsConfigurationData(new Properties()), new GameConfigurationImpl());
		final ClientGame game = MockFactory.createSimpleRandomGame();
		final List<Cursor> cursors = MockFactory.createCursors((ClientBoard) game.getBoard());
		final BoardViewModel boardModel = modelBuilder.createBoardModel(game, cursors);
		assertNotNull(boardModel);
		assertEquals(31.17f, boardModel.getHeight(), 0.01f);
		assertEquals(27.0f, boardModel.getWidth(), 0.01f);
	}

}
