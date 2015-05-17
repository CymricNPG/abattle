/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.model.impl;

import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.BoardCreator;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.GameStatus;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.impl.GameImpl;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.server.ServerExceptionCode;
import net.npg.abattle.server.model.BoardInitializedNotifier;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerLinks;
import net.npg.abattle.server.model.ServerPlayer;

/**
 * @author cymric
 *
 */
public class ServerGameImpl extends GameImpl<ServerPlayer, ServerCell, ServerLinks> implements ServerGame {

	private final int maxPlayers;

	public ServerGameImpl(final int maxPlayers, final BoardCreator<Board<ServerPlayer, ServerCell, ServerLinks>> boardCreator,
			final GameConfiguration configuration) {
		super(boardCreator, configuration);
		this.maxPlayers = maxPlayers;
	}

	@Override
	public void accept(final ModelVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void addPlayer(final ServerPlayer player) throws BaseException {
		Validate.notNull(player);
		if (this.getPlayers().size() >= getMaxPlayers()) {
			throw new BaseException(ServerExceptionCode.TOO_MANY_PLAYERS);
		}
		super.addPlayer(player);
	}

	@Override
	public int getMaxPlayers() {
		return maxPlayers;
	}

	@Override
	public int getXSize() {
		return getGameConfiguration().getXSize();
	}

	@Override
	public int getYSize() {
		return getGameConfiguration().getYSize();
	}

	@Override
	public IntPoint getSize() {
		return IntPoint.from(getXSize(), getYSize());
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void startGame(final BoardInitializedNotifier notifier) throws BaseException {
		assert (getBoardCreator() != null);
		getBoardCreator().run(getPlayers());
		setBoard((Board<ServerPlayer, ServerCell, ServerLinks>) getBoardCreator().getBoard());
		notifier.boardCreated(getBoard());
		setStatus(GameStatus.RUNNING);
	}
}
