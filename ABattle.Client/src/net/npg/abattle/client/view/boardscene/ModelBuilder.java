/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import java.util.List;

import net.npg.abattle.client.view.boardscene.debug.DebugCells;
import net.npg.abattle.client.view.selection.twocells.Cursor;
import net.npg.abattle.common.configuration.GraphicsConfigurationData;
import net.npg.abattle.common.hex.Directions;
import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.model.BoardLoop;
import net.npg.abattle.common.model.CellVisitor;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.MyHashMap;
import net.npg.abattle.common.utils.MyMap;
import net.npg.abattle.common.utils.Validate;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

/**
 * @author cymric
 *
 */
public class ModelBuilder {

	private final HexBase hexBase;
	private final GraphicsConfigurationData graphicsConfiguration;
	private final GameConfiguration gameConfiguration;
	private final MyMap<IntPoint, Hex> hexMap;
	private final Function1<CellShape, String> textCreator;

	public ModelBuilder(final HexBase hexBase, final GraphicsConfigurationData graphicsConfiguration, final GameConfiguration gameConfiguration) {
		Validate.notNulls(hexBase, graphicsConfiguration, gameConfiguration, gameConfiguration);
		this.hexBase = hexBase;
		this.gameConfiguration = gameConfiguration;
		this.graphicsConfiguration = graphicsConfiguration;
		this.hexMap = new MyHashMap<IntPoint, Hex>();
		textCreator = DebugCells.debugList.getSelectedClass().getTextCreator();
	}

	private Hex getHex(final ClientCell cell) {
		assert hexBase != null;
		assert cell != null;
		assert hexMap != null;
		final IntPoint boardCoordinate = cell.getBoardCoordinate();
		final Hex exisitingHex = hexMap.get(boardCoordinate);
		if (exisitingHex != null) {
			return exisitingHex;
		}
		final Hex newHex = new Hex(boardCoordinate, hexBase);
		hexMap.put(boardCoordinate, newHex);
		return newHex;
	}

	private LayerModel createBaseLayer(final ClientBoard board) {
		assert board != null;
		final LayerModel layer = new LayerModel(true);
		final CellVisitor<ClientPlayer, ClientCell> visitor = new CellVisitor<ClientPlayer, ClientCell>() {
			@Override
			public void visitCell(final ClientCell cell) {
				layer.addShape(new FilledHexShape(board, cell, getHex(cell), gameConfiguration));
			}
		};
		BoardLoop.visitAllCells(board, visitor);
		return layer;
	}

	/**
	 * creates the complete model, normally this should be done at the beginning of the game, afte the Board is
	 * initialized. All view model elements are adapters for the model. If a view Model (shape) is painted is decided in
	 * the renderer. Because of the static and limited game coverage all possible view model elements are generated.
	 */
	public BoardViewModel createBoardModel(final ClientGame game, final List<Cursor> cursors) {
		Validate.notNulls(game, cursors);
		final ClientBoard board = (ClientBoard) game.getBoard();
		final BoardViewModel boardModel = new BoardViewModel();
		setSize(board, boardModel);
		boardModel.addLayer(createBaseLayer(board));
		boardModel.addLayer(createGridLayer(board));
		boardModel.addLayer(createUnitLayer(board));
		boardModel.addLayer(createStructureLayer(board));
		boardModel.addLayer(createFightLayer(board));
		boardModel.addLayer(createLinkLayer(board));
		boardModel.addLayer(createCursorLayer(cursors));
		boardModel.addLayer(createHUD(game));
		boardModel.addLayer(createCellTextLayer(board, cursors));
		return boardModel;
	}

	private LayerModel createHUD(final ClientGame game) {
		assert game != null;
		assert hexBase != null;
		final LayerModel layer = new LayerModel(true);
		layer.addShape(new ComplexBarShape(game, hexBase));
		return layer;
	}

	private LayerModel createCellTextLayer(final ClientBoard board, final List<Cursor> cursors) {
		assert hexBase != null;
		assert board != null;
		assert cursors != null;
		final LayerModel layer = new LayerModel(true);
		final ClientCellVisitor visitor = new ClientCellVisitor() {
			@Override
			public void visitCell(final ClientCell cell) {
				layer.addShape(new CellTextShape(cell, getHex(cell), Fonts.CellFont, textCreator));
			}
		};
		BoardLoop.visitAllCells(board, visitor);
		return layer;
	}

	private LayerModel createCursorLayer(final List<Cursor> cursors) {
		assert hexBase != null;
		assert cursors != null;
		final LayerModel layerModel = new LayerModel(false);
		for (final Cursor cursor : cursors) {
			Validate.notNulls(cursor);
			final CursorHexShape cursorHexShape = new CursorHexShape(cursor, hexBase);
			layerModel.addShape(cursorHexShape);
		}
		return layerModel;

	}

	private LayerModel createFightLayer(final ClientBoard board) {
		assert hexBase != null;
		assert board != null;
		final LayerModel layer = new LayerModel(false);
		final ClientCellVisitor visitor = new ClientCellVisitor() {
			@Override
			public void visitCell(final ClientCell cell) {
				layer.addShape(new FightShape(cell, getHex(cell), graphicsConfiguration));
			}
		};
		BoardLoop.visitAllCells(board, visitor);
		return layer;
	}

	private LayerModel createGridLayer(final ClientBoard board) {
		assert hexBase != null;
		assert board != null;
		final LayerModel layer = new LayerModel(true);
		final ClientCellVisitor visitor = new ClientCellVisitor() {
			@Override
			public void visitCell(final ClientCell cell) {
				layer.addShape(new HexShape(cell, getHex(cell)));
			}
		};
		BoardLoop.visitAllCells(board, visitor);
		return layer;
	}

	private LayerModel createLinkLayer(final ClientBoard board) {
		assert hexBase != null;
		assert board != null;
		final LayerModel layer = new LayerModel(false);

		final CellVisitor<ClientPlayer, ClientCell> visitor = new ClientCellVisitor() {
			@Override
			public void visitCell(final ClientCell cell) {
				for (final Directions direction : Directions.values()) {
					final ClientCell adjacentCell = board.getAdjacentCell(cell, direction);
					if (adjacentCell != null) {
						layer.addShape(new OutgoingLinkShape(cell, adjacentCell, board, getHex(cell), getHex(adjacentCell)));
					}
				}
			}
		};
		BoardLoop.visitAllCells(board, visitor);
		return layer;
	}

	private LayerModel createStructureLayer(final ClientBoard board) {
		assert hexBase != null;
		assert board != null;
		final LayerModel layer = new LayerModel(true);
		final ClientCellVisitor visitor = new ClientCellVisitor() {
			@Override
			public void visitCell(final ClientCell cell) {
				layer.addShape(new CircleShape(cell, getHex(cell), graphicsConfiguration));

			}
		};
		BoardLoop.visitAllCells(board, visitor);
		return layer;
	}

	private LayerModel createUnitLayer(final ClientBoard board) {
		assert hexBase != null;
		assert board != null;
		final LayerModel layer = new LayerModel(false);
		final ClientCellVisitor visitor = new ClientCellVisitor() {
			@Override
			public void visitCell(final ClientCell cell) {
				layer.addShape(new FilledCircleShape(cell, getHex(cell), graphicsConfiguration, gameConfiguration));
			}
		};
		BoardLoop.visitAllCells(board, visitor);
		return layer;
	}

	private void setSize(final ClientBoard board, final BoardViewModel boardModel) {
		final float x = (board.getXSize() + 1) * hexBase.side;
		final float y = (board.getYSize() + 1) * hexBase.height;
		boardModel.setHeight(y);
		boardModel.setWidth(x);
	}
}
