package net.npg.abattle.server.model.clientfacade;

import net.npg.abattle.common.error.NotAvailableException;
import net.npg.abattle.common.hex.Directions;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientLinks;
import net.npg.abattle.common.model.impl.IDElementImpl;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.server.game.impl.fog.Fog;
import net.npg.abattle.server.game.impl.fog.Fogs;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerPlayer;

public class ClientBoardFacade extends IDElementImpl implements ClientBoard {

	private final ServerBoard board;

	private final ServerPlayer player;
	private final ClientCell[][] clientBoard;
	private final ClientCell[][] invisibleCells;

	private final Fog fog;

	public ClientBoardFacade(final ServerBoard board, final ServerPlayer player, final CheckModelElement checker, final PlayersFacade playersFacade) {
		super(board.getId());
		Validate.notNulls(board, player, checker, playersFacade);
		this.player = player;
		this.board = board;
		clientBoard = new ClientCell[board.getXSize()][board.getYSize()];
		invisibleCells = new ClientCell[board.getXSize()][board.getYSize()];
		fillClientBoard(checker, playersFacade);
		fog = Fogs.fogList.getSelectedClass();
	}

	private void fillClientBoard(final CheckModelElement checker, final PlayersFacade playersFacade) {
		for (int x = 0; x < board.getXSize(); x++) {
			for (int y = 0; y < board.getYSize(); y++) {
				final ServerCell serverCell = board.getCellAt(x, y);
				clientBoard[x][y] = convert(serverCell, checker, playersFacade);
				invisibleCells[x][y] = new InvisibleClientCell(serverCell.getId(), serverCell.getBoardCoordinate(), checker);
			}
		}
	}

	private ClientCell convert(final ServerCell serverCell, final CheckModelElement checker, final PlayersFacade playersFacade) {
		if (serverCell == null) {
			return null;
		}
		return new ClientCellFacade(serverCell, checker, playersFacade);
	}

	@Override
	public void accept(final ModelVisitor visitor) {
		Validate.notNulls(visitor);
		visitor.visit(this);
	}

	@Override
	public ClientCell getAdjacentCell(final ClientCell cell, final Directions direction) {
		throw new NotAvailableException();
	}

	@Override
	public ClientCell getCell(final int cellId) {
		throw new RuntimeException();
	}

	@Override
	public ClientCell getCellAt(final int x, final int y) {
		assert (board != null);
		final ClientCell cell = clientBoard[x][y];
		if ((cell.getOwner().isPresent() && player.getId() == cell.getOwner().get().getId())) {
			return cell;
		}
		if (fog.isVisible(clientBoard, board, player, cell)) {
			return cell;
		} else {
			return invisibleCells[x][y];
		}
	}

	@Override
	public ClientCell getCellAt(final IntPoint boardCoordinate) {
		Validate.notNull(boardCoordinate);
		return getCellAt(boardCoordinate.x, boardCoordinate.y);
	}

	@Override
	public ClientLinks getLinks() {
		return new ClientLinksFacade(board, player, this);
	}

	@Override
	public int getXSize() {
		assert (board != null);
		return board.getXSize();
	}

	@Override
	public int getYSize() {
		assert (board != null);
		return board.getYSize();
	}

	@Override
	public boolean isInside(final IntPoint coordinate) {
		assert (board != null);
		return board.isInside(coordinate);
	}

	@Override
	public boolean isCellInitialized(final int x, final int y) {
		return true;
	}

	@Override
	public void setCellAt(final ClientCell cell) {
		throw new NotAvailableException();
	}
}
