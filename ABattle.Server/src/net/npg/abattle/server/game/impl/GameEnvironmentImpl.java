/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.game.impl;

import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.BoardCreator;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.IDElement;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.MyHashMap;
import net.npg.abattle.common.utils.MyMap;
import net.npg.abattle.common.utils.MyRunnable;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandQueueServer;
import net.npg.abattle.server.ServerConstants;
import net.npg.abattle.server.game.GameEnvironment;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerLinks;
import net.npg.abattle.server.model.ServerPlayer;
import net.npg.abattle.server.model.clientfacade.ClientGameFacade;
import net.npg.abattle.server.model.impl.ServerGameImpl;

/**
 * @author Cymric
 *
 */
public class GameEnvironmentImpl implements GameEnvironment, IDElement {

	private MyRunnable gameThread;

	private final ServerGame serverGame;

	private final MyMap<ServerPlayer, ClientGame> clientFacades;

	private volatile boolean disposed = false;

	protected GameEnvironmentImpl(final int maxPlayers, final BoardCreator<Board<ServerPlayer, ServerCell, ServerLinks>> boardCreator,
			final GameConfiguration configuration) {
		super();
		Validate.notNulls(boardCreator, configuration);
		Validate.exclusiveBetween(0, CommonConstants.MAX_PLAYERS + 1, maxPlayers);
		serverGame = new ServerGameImpl(maxPlayers, boardCreator, configuration);
		gameThread = null;
		clientFacades = new MyHashMap<ServerPlayer, ClientGame>();
	}

	@Override
	public void addNewPlayer(final ServerPlayer player) throws BaseException {
		assert !disposed;
		Validate.notNull(player);
		serverGame.addPlayer(player);
	}

	@Override
	public synchronized void attachThread(final MyRunnable gameThread) {
		assert !disposed;
		Validate.notNull(gameThread);
		if (this.gameThread != null) {
			this.gameThread.stop();
		}
		this.gameThread = gameThread;
	}

	@Override
	public ClientGame getClientGame(final ServerPlayer player) {
		assert !disposed;
		final ClientGame clientGame = clientFacades.get(player);
		if (clientGame == null) {
			final ClientGameFacade facade = new ClientGameFacade(serverGame, player);
			clientFacades.put(player, facade);
			return facade;
		} else {
			return clientGame;
		}
	}

	@Override
	public long getCreationTime() {
		assert !disposed;
		assert serverGame != null;
		return serverGame.getCreationTime();
	}

	@Override
	public int getId() {
		assert !disposed;
		assert serverGame != null;
		return serverGame.getId();
	}

	@Override
	public ServerGame getServerGame() {
		assert !disposed;
		return serverGame;
	}

	private boolean invalidPoint(final IntPoint point, final ServerPlayer player) {
		if (!serverGame.getBoard().isInside(point)) {
			ServerConstants.LOG.error(getId() + ": Player:" + player.getId() + " send an invalid coordinate:" + point.toString());
			return true;
		}
		return false;
	}

	private boolean checkLinkAction(final ServerPlayer player, final IntPoint start, final IntPoint end) {
		Validate.notNulls(player, start, end);
		if (!serverGame.getPlayers().contains(player)) {
			ServerConstants.LOG.error(getId() + ": Player:" + player.getId() + " tried to access game:" + getId());
			return false;
		}
		if (invalidPoint(start, player)) {
			ServerConstants.LOG.debug("Invalid Start-Point:" + start);
			return false;
		}
		if (invalidPoint(end, player)) {
			ServerConstants.LOG.debug("Invalid End-Point:" + end);
			return false;
		}
		return true;
	}

	@Override
	public boolean link(final ServerPlayer player, final IntPoint start, final IntPoint end, final boolean create) {
		assert !disposed;
		if (!checkLinkAction(player, start, end)) {
			return false;
		}
		final ServerCell startCell = serverGame.getBoard().getCellAt(start);
		if (!startCell.isOwner(player)) {
			ServerConstants.LOG.debug("Not owner.");
			return false;
		}
		final ServerCell endCell = serverGame.getBoard().getCellAt(end);
		if (!endCell.isAdjacentTo(startCell)) {
			ServerConstants.LOG.error(getId() + ": Player:" + player.getId() + " issued a link command with wrong cells:" + startCell + " -> " + endCell);
			return false;
		}

		if (serverGame.getBoard().getLinks().hasLink(startCell, endCell, player) == create) {
			return false;
		}

		((ServerBoard) serverGame.getBoard()).getLinks().toggleOutgoingLink(startCell, endCell, player);
		ServerConstants.LOG.debug("Linked Cells:" + start + " to " + end);
		return true;
	}

	@Override
	public boolean removePlayer(final ServerPlayer player) {
		assert !disposed;
		return serverGame.removePlayer(player);
	}

	@Override
	public void startGame(final CommandQueueServer commandQueue) {
		assert !disposed;
		final ServerGameRunner gameRunner = new ServerGameRunner(this, commandQueue);
		final Thread gameThread = new Thread(gameRunner, getId() + ": Logic Thread");
		attachThread(gameRunner);
		gameThread.start();
	}

	@Override
	public boolean isDisposed() {
		return disposed;
	}

	@Override
	public void dispose() {
		if (gameThread != null) {
			gameThread.stop();
		}
		disposed = true;
	}
}
