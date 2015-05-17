/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.client.impl;

import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.BoardCreator;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.GameStatus;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientLinks;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.model.impl.GameImpl;

/**
 * @author cymric
 *
 */
public class ClientGameImpl extends GameImpl<ClientPlayer, ClientCell, ClientLinks> implements ClientGame {

	private ClientPlayer localPlayer;

	public ClientGameImpl(final int id, final BoardCreator<ClientBoard> boardCreator, final GameConfiguration configuration) {
		super(id, boardCreator, configuration);
	}

	public ClientGameImpl(final int id) {
		super(id);
	}

	@Override
	public void accept(final ModelVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public ClientPlayer getLocalPlayer() {
		assert localPlayer != null;
		return localPlayer;
	}

	public void setLocalPlayer(final ClientPlayer player) {
		if (!getPlayers().contains(player)) {
			throw new IllegalArgumentException();
		}
		this.localPlayer = player;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void initGame() throws BaseException {
		assert (getBoardCreator() != null);
		getBoardCreator().run(getPlayers());
		setBoard((Board<ClientPlayer, ClientCell, ClientLinks>) getBoardCreator().getBoard());
		setStatus(GameStatus.PENDING_CLIENT);
	}
}
