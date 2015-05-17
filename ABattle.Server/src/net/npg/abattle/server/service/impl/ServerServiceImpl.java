/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.service.impl;

import static net.npg.abattle.server.service.impl.ResultBuilder.buildBooleanResultError;
import static net.npg.abattle.server.service.impl.ResultBuilder.buildBooleanResultSuccess;
import static net.npg.abattle.server.service.impl.ResultBuilder.buildClientInfoResultError;
import static net.npg.abattle.server.service.impl.ResultBuilder.buildGameInfoResultError;
import static net.npg.abattle.server.service.impl.ResultBuilder.buildGameInfoResultSuccess;
import static net.npg.abattle.server.service.impl.ResultBuilder.buildResult;
import static net.npg.abattle.server.service.impl.ResultBuilder.convert;
import static net.npg.abattle.server.service.impl.ResultBuilder.toGameInfoResult;
import static net.npg.abattle.server.service.impl.ResultBuilder.unknownGame;
import static net.npg.abattle.server.service.impl.ResultBuilder.unknownPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.impl.ColorImpl;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.common.utils.impl.DisposeableImpl;
import net.npg.abattle.communication.command.CommandQueueServer;
import net.npg.abattle.communication.service.ServerService;
import net.npg.abattle.communication.service.common.BooleanResult;
import net.npg.abattle.communication.service.common.ClientInfoResult;
import net.npg.abattle.communication.service.common.GameInfoResult;
import net.npg.abattle.communication.service.common.GameInfoResultBuilder;
import net.npg.abattle.communication.service.common.MutableIntPoint;
import net.npg.abattle.server.ServerConstants;
import net.npg.abattle.server.game.GameEnvironment;
import net.npg.abattle.server.game.GamesManager;
import net.npg.abattle.server.game.impl.GamesManagerImpl;
import net.npg.abattle.server.model.ServerPlayer;
import net.npg.abattle.server.model.impl.ServerPlayerImpl;

import com.google.common.base.Strings;

/**
 * The Class ServerServiceImpl implements the ServerService
 *
 * @author spatzenegger
 */
public class ServerServiceImpl extends DisposeableImpl implements ServerService {

	private final GamesManager gamesManager;

	/**
	 * Instantiates a new server service impl.
	 *
	 * @param gamesManager the games manager
	 */
	public ServerServiceImpl(final CommandQueueServer commandQueue) {
		Validate.notNull(commandQueue);
		this.gamesManager = new GamesManagerImpl(commandQueue);
	}

	private void addComputerPlayers(final GameEnvironment game, final int computerPlayers) throws BaseException {
		for (int i = 0; i < computerPlayers; i++) {
			final ServerPlayerImpl player = new ServerPlayerImpl("Computer" + i, ColorImpl.BLACK, true);
			game.addNewPlayer(player);
			player.setColor(gamesManager.getFreeColor(player, game.getServerGame()));
		}
	}

	@Override
	public GameInfoResult createGame(final int clientId, final int maxPlayers, final MutableIntPoint size, final int computerPlayers) {
		GameEnvironment game = null;
		try {
			final ServerPlayer player = gamesManager.getPlayer(clientId);
			if (player == null) {
				return toGameInfoResult(unknownPlayer(clientId));
			}

			final int totalPlayers = maxPlayers + computerPlayers;
			if (totalPlayers < 2 || totalPlayers > ServerConstants.MAX_PLAYERS) {
				return buildGameInfoResultError("Incorrect number of Players. Human:" + maxPlayers + " Computer:" + computerPlayers + " Total allowed:"
						+ ServerConstants.MAX_PLAYERS);
			}
			game = gamesManager.createGame(totalPlayers, size.to());
			addComputerPlayers(game, computerPlayers);
			ServerConstants.LOG.info("Created game:" + game.getServerGame().getId());

			if (!gamesManager.joinGame(game, player)) {
				gamesManager.removeGame(game);
				return buildGameInfoResultError("Player cannot join.");
			}

			return buildGameInfoResultSuccess(game);
		} catch (final Exception e) {
			ServerConstants.LOG.error(e.getMessage(), e);
			if (game != null) {
				gamesManager.removeGame(game);
			}
			return buildGameInfoResultError("Unexpected:" + e.getMessage());
		}
	}

	private ServerPlayer findPlayer(final Collection<ServerPlayer> players, final int clientId) {
		for (final ServerPlayer player : players) {
			if (player.getId() == clientId) {
				return player;
			}
		}
		return null;
	}

	/*
	 * only for local games ...
	 */
	public GameEnvironment getGameEnvironment(final int gameId) {
		final GameEnvironment game = gamesManager.getGame(gameId);
		if (game == null) {
			throw new IllegalArgumentException("Unknown game:" + gameId);
		}
		return game;
	}

	/**
	 * Gets the pending games.
	 *
	 * @return the pending games
	 */
	@Override
	public GameInfoResult[] getPendingGames() {
		final Collection<GameEnvironment> games = gamesManager.getPendingGames();
		final List<GameInfoResult> gameResults = new ArrayList<GameInfoResult>();
		for (final GameEnvironment game : games) {
			gameResults.add(buildGameInfoResultSuccess(game));
		}
		return gameResults.toArray(new GameInfoResult[gameResults.size()]);
	}

	/**
	 * Join game.
	 *
	 * @param gameId the game id
	 * @param client the client
	 * @return the boolean result
	 */
	@Override
	public GameInfoResult joinGame(final int gameId, final int clientId) {
		try {
			final ServerPlayer player = gamesManager.getPlayer(clientId);
			if (player == null) {
				return toGameInfoResult(unknownPlayer(clientId));
			}
			final GameEnvironment game = gamesManager.getGame(gameId);
			if (game == null) {
				return toGameInfoResult(unknownGame(gameId));
			}
			final boolean success = gamesManager.joinGame(game, player);
			if (success) {
				return buildGameInfoResultSuccess(game);
			} else {
				return toGameInfoResult(buildBooleanResultError("Cannot join game!"));
			}
		} catch (final Exception e) {
			ServerConstants.LOG.error(gameId + " Uncatched Exception for client:" + clientId, e);
			return new GameInfoResultBuilder().success(false).errorMessage(e.getMessage()).build();
		}
	}

	/**
	 * Leave game.
	 *
	 * @param gameId the game id
	 * @param client the client
	 * @return the boolean result
	 */
	@Override
	public BooleanResult leaveGame(final int gameId, final int clientId) {

		final GameEnvironment game = gamesManager.getGame(gameId);
		if (game == null) {
			return unknownGame(gameId);
		}
		final ServerPlayer player = findPlayer(game.getServerGame().getPlayers(), clientId);
		if (player == null) {
			return unknownPlayer(clientId);
		}
		final boolean success = gamesManager.leaveGame(game, player);
		return buildResult(success, "Cannot leave game!");
	}

	@Override
	public BooleanResult link(final int gameId, final int clientId, final MutableIntPoint startCell, final MutableIntPoint endCell, final boolean create) {
		final GameEnvironment game = gamesManager.getGame(gameId);
		if (game == null) {
			return unknownGame(gameId);
		}

		final ServerPlayer player = findPlayer(game.getServerGame().getPlayers(), clientId);
		if (player == null) {
			return unknownPlayer(clientId);
		}

		final boolean success = game.link(player, startCell.to(), endCell.to(), create);
		return buildResult(success, "Cannot link!");
	}

	/**
	 * Login.
	 *
	 * @param name the name
	 * @return the client info result
	 */
	@Override
	public ClientInfoResult login(final String name) {
		if (Strings.isNullOrEmpty(name)) {
			return buildClientInfoResultError("Login with empty name failed.");
		}
		final ServerPlayer player = gamesManager.login(name);
		if (player == null) {
			return buildClientInfoResultError("Login failed.");
		}
		final ClientInfoResult clientInfoResult = new ClientInfoResult(convert(player), "", true);
		return clientInfoResult;
	}

	@Override
	public void leaveGames(final int clientId) {
		final ServerPlayer player = gamesManager.getPlayer(clientId);
		if (player == null) {
			return;
		}
		gamesManager.removePlayer(player);
	}

	@Override
	public BooleanResult initSingleGame(final int gameId) {
		final GameEnvironment game = gamesManager.getGame(gameId);
		if (game == null) {
			return unknownGame(gameId);
		}
		try {
			gamesManager.initSingleGame(game);
		} catch (final BaseException e) {
			ServerConstants.LOG.error(gameId + " Error while init game.", e);
			return new GameInfoResultBuilder().success(false).errorMessage(e.getMessage()).build();
		}
		return buildBooleanResultSuccess();
	}

	@Override
	public BooleanResult ping(final int gameId) {
		final GameEnvironment game = gamesManager.getGame(gameId);
		if (game == null || game.isDisposed()) {
			return unknownGame(gameId);
		}
		return buildBooleanResultSuccess();
	}
}
