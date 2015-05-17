/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.model;

import java.util.ArrayList;
import java.util.List;

import net.npg.abattle.client.view.selection.twocells.Cursor;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.model.client.impl.ClientBoardImpl;
import net.npg.abattle.common.model.client.impl.ClientCellImpl;
import net.npg.abattle.common.model.client.impl.ClientGameImpl;
import net.npg.abattle.common.utils.ColorConvert;
import net.npg.abattle.common.utils.FieldLoop;
import net.npg.abattle.common.utils.FieldVisitor;
import net.npg.abattle.common.utils.IntPoint;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.badlogic.gdx.graphics.Color;

/**
 * @author cymric
 * 
 */
public class MockFactory {

	public static final int BOARD_X_SIZE = 17;
	public static final int BOARD_Y_SIZE = 17;

	public static ClientBoardImpl createBoard(final ClientPlayer player) {
		final int xsize = BOARD_X_SIZE;
		final int ysize = BOARD_Y_SIZE;
		final Mockery context = new Mockery();
		final CheckModelElement check = context.mock(CheckModelElement.class);
		context.checking(new Expectations() {
			{
				allowing(check);
			}
		});
		final ClientBoardImpl board = new ClientBoardImpl(xsize, ysize);
		FieldLoop.visitAllFields(xsize, ysize, new FieldVisitor() {

			private ClientCell createCell(final int x, final int y) {

				final ClientCellImpl cell = new ClientCellImpl((x * 100 + y) * 42, IntPoint.from(x, y), (int) (Math.random() * 3), getCellType(), check);
				cell.setBattle(false);
				cell.setStrength((int) (Math.random() * 100));
				cell.setVisible(true);
				cell.setOwner(getOwner());
				return cell;
			}

			private CellTypes getCellType() {
				for (final CellTypes cellType : CellTypes.values()) {
					if (Math.random() < 0.2) {
						return cellType;
					}
				}
				return CellTypes.PLAIN;
			}

			private ClientPlayer getOwner() {
				return player;
			}

			@Override
			public void visit(final int x, final int y) {
				board.setCellAt(createCell(x, y));
			}

		});
		return board;
	}

	public static List<Cursor> createCursors(final ClientBoard board) {
		final List<Cursor> cursors = new ArrayList<Cursor>();
		final Cursor cursor1 = new Cursor(new HexBase(1.0f), board);
		cursor1.show();
		final Cursor cursor2 = new Cursor(new HexBase(1.0f), board);
		cursor1.show();
		cursors.add(cursor1);
		cursors.add(cursor2);
		return cursors;
	}

	public static ClientPlayer createPlayer() {
		return new ClientPlayer() {

			@Override
			public void accept(final ModelVisitor visitor) {
				throw new RuntimeException();
			}

			@Override
			public net.npg.abattle.common.model.Color getColor() {
				return ColorConvert.convert(Color.BLACK);
			}

			@Override
			public long getCreationTime() {
				throw new RuntimeException();
			}

			@Override
			public int getId() {
				return 0;
			}

			@Override
			public String getName() {
				return "Dummy";
			}

			@Override
			public int getStrength() {
				return 0;
			}

			@Override
			public void setStrength(final int strength) {
			}

			@Override
			public void setColor(final net.npg.abattle.common.model.Color color) {
				throw new RuntimeException();
			}
		};
	}

	public static ClientGameImpl createSimpleRandomGame() throws BaseException {
		final ClientGameImpl game = new ClientGameImpl(12);
		final ClientPlayer player = createPlayer();
		game.setBoard(createBoard(player));
		game.addPlayer(player);
		game.setLocalPlayer(player);
		return game;
	}
}
