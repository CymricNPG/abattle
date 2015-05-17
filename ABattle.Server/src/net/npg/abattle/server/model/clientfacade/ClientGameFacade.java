/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.model.clientfacade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.GameStatus;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.model.impl.IDElementImpl;
import net.npg.abattle.common.utils.MyHashMap;
import net.npg.abattle.common.utils.MyMap;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerPlayer;

import com.google.common.base.Optional;

/**
 * @author Cymric
 * 
 */
public class ClientGameFacade extends IDElementImpl implements ClientGame, PlayersFacade {

	private final ClientPlayer localPlayer;
	private final List<ClientPlayer> players;
	private final ServerGame serverGame;
	private final ClientBoard clientBoard;
	private final MyMap<ServerPlayer, Optional<ClientPlayer>> optionalPlayers;

	public ClientGameFacade(final ServerGame serverGame, final ServerPlayer localPlayer) {
		super(serverGame.getId());
		Validate.notNull(serverGame);
		Validate.notNull(localPlayer);
		Validate.isTrue(serverGame.getPlayers().contains(localPlayer));
		this.localPlayer = new ClientPlayerFacade(localPlayer);
		this.serverGame = serverGame;
		clientBoard = new ClientBoardFacade((ServerBoard) serverGame.getBoard(), localPlayer, serverGame.getGameConfiguration().getChecker(), this);
		players = new ArrayList<ClientPlayer>();
		optionalPlayers = new MyHashMap<ServerPlayer, Optional<ClientPlayer>>();
	}

	@Override
	public void accept(final ModelVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public ClientBoard getBoard() {
		return clientBoard;
	}

	@Override
	public ClientPlayer getLocalPlayer() {
		return localPlayer;
	}

	@Override
	public Collection<ClientPlayer> getPlayers() {
		if (players.size() != serverGame.getPlayers().size()) {
			players.clear();
			for (final ServerPlayer player : serverGame.getPlayers()) {
				players.add(new ClientPlayerFacade(player));
			}
		}
		return players;
	}

	@Override
	public GameStatus getStatus() {
		return serverGame.getStatus();
	}

	@Override
	public void initGame() throws BaseException {
		throw new RuntimeException("Not allowed");
	}

	@Override
	public GameConfiguration getGameConfiguration() {
		return serverGame.getGameConfiguration();
	}

	@Override
	public void stopGame() {
		throw new RuntimeException("Not allowed");
	}

	@Override
	public void addPlayer(final ClientPlayer player) throws BaseException {
		throw new RuntimeException("Not allowed");
	}

	@Override
	public Optional<ClientPlayer> getPlayer(final ServerPlayer serverPlayer) {
		final Collection<ServerPlayer> players = serverGame.getPlayers();
		if (players.size() != optionalPlayers.size()) {
			for (final ServerPlayer player : players) {
				optionalPlayers.put(player, Optional.<ClientPlayer> of(new ClientPlayerFacade(player)));
			}
		}
		return optionalPlayers.get(serverPlayer);
	}

	@Override
	public boolean removePlayer(final ClientPlayer player) {
		throw new RuntimeException("Not allowed");
	}
}
