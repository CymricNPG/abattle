/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.communication.service;

import net.npg.abattle.communication.network.NetworkService;
import net.npg.abattle.communication.service.common.BooleanResult;
import net.npg.abattle.communication.service.common.ClientInfoResult;
import net.npg.abattle.communication.service.common.GameInfoResult;
import net.npg.abattle.communication.service.common.MutableIntPoint;

/**
 * The Interface ServerService. the frontend for the GamesManager
 *
 * @author spatzenegger
 */
public interface ServerService extends NetworkService {

	static int SERVER_SERVICE_ID = 1;

	/**
	 * Creates the game and joins the player
	 *
	 * @param maxPlayers the max players
	 * @param client the client which creates the game (also joins the game)
	 * @return the game info result
	 */
	GameInfoResult createGame(final int clientId, int maxPlayers, MutableIntPoint size, int computerPlayers);

	/**
	 * Gets the pending games.
	 *
	 * @return the pending games
	 */
	GameInfoResult[] getPendingGames();

	/**
	 * Join game.
	 *
	 * @param gameId the game id
	 * @param client the client
	 * @return the boolean result
	 */
	GameInfoResult joinGame(int gameId, int clientId);

	/**
	 * Leave game.
	 *
	 * @param gameId the game id
	 * @param client the client
	 * @return the boolean result
	 */
	BooleanResult leaveGame(int gameId, int clientId);

	/**
	 * Login.
	 *
	 * @param name the name
	 * @return the client info result
	 */
	ClientInfoResult login(String name);

	BooleanResult link(int gameId, int clientId, MutableIntPoint startCell, MutableIntPoint endCell, boolean create);

	void leaveGames(int clientId);

	BooleanResult initSingleGame(int gameId);

	BooleanResult ping(int gameId);

}
