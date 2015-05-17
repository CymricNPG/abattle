/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.game.impl;

import java.util.Collection;

import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.BoardCreator;
import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.server.ServerConstants;
import net.npg.abattle.server.game.impl.terrain.TerrainCreators;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerLinks;
import net.npg.abattle.server.model.ServerPlayer;
import net.npg.abattle.server.model.impl.ServerBoardImpl;
import net.npg.abattle.server.model.impl.ServerCellImpl;

/**
 * creates a random board
 *
 * @author Cymric
 *
 */
public class BoardCreatorImpl implements BoardCreator<Board<ServerPlayer, ServerCell, ServerLinks>> {

	private static final int INITIAL_STRENGTH = 10;

	private final ServerBoardImpl board;
	private final int xsize;
	private final int ysize;

	private final CheckModelElement checker;

	private final ConfigurationComponent configuration;

	private final IntPoint size;

	public BoardCreatorImpl(final IntPoint size, final CheckModelElement checker) {
		Validate.notNull(size);
		Validate.notNulls(checker);
		Validate.exclusiveBetween(ServerConstants.MIN_XSIZE, ServerConstants.MAX_XSIZE, size.x);
		Validate.exclusiveBetween(ServerConstants.MIN_YSIZE, ServerConstants.MAX_YSIZE, size.y);
		this.xsize = size.x;
		this.ysize = size.y;
		this.size = size;
		this.checker = checker;
		this.board = new ServerBoardImpl(xsize, ysize);
		configuration = ComponentLookup.getInstance().getComponent(ConfigurationComponent.class);
	}

	public IntPoint calcBasePosition(final int nrPlayers, final int playerCount) {
		final double midX = xsize / 2;
		final double midY = ysize / 2;
		final double rad = Math.min(midX, midY) * 0.9;
		final double angle = 360 / nrPlayers * playerCount;
		final double xpos = midX + rad * Math.cos(Math.toRadians(angle));
		final double ypos = midY + rad * Math.sin(Math.toRadians(angle));
		final IntPoint basePosition = IntPoint.from((int) xpos, (int) ypos);
		return basePosition;
	}

	public void createBase(final Player player, final IntPoint basePosition) {
		final ServerCellImpl baseCell = (ServerCellImpl) board.getCellAt(basePosition);
		baseCell.setCellType(CellTypes.BASE);
		baseCell.setOwner(player);
		baseCell.setStrength(INITIAL_STRENGTH);
	}

	public void distributeTowns() {
		assert board != null;
		for (int i = 0; i < xsize * ysize * configuration.getGameConfiguration().getRandomBases() / 100; i++) {
			final int x = (int) (Math.random() * xsize);
			final int y = (int) (Math.random() * ysize);
			final IntPoint cityPosition = IntPoint.from(x, y);
			final ServerCellImpl cell = (ServerCellImpl) board.getCellAt(cityPosition);
			cell.setCellType(CellTypes.TOWN);
		}
	}

	@Override
	public ServerBoard getBoard() {
		Validate.notNull(board);
		return board;
	}

	public void makeHomeBases(final Collection<? extends Player> players) {
		final int nrPlayers = players.size();
		int playerCount = 0;
		for (final Player player : players) {
			final IntPoint basePosition = calcBasePosition(nrPlayers, playerCount);
			createBase(player, basePosition);
			playerCount++;
		}
	}

	@Override
	public void run(final Collection<? extends Player> players) throws BaseException {
		ServerConstants.LOG.info("Start creating world");
		createLandscape();
		distributeTowns();
		makeHomeBases(players);
	}

	private void createLandscape() {
		TerrainCreators.terrainMap.getSelectedClass().createBoard(board, size, checker);
	}

}
