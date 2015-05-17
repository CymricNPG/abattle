/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.game;

import java.util.Collection;

import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.Color;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerPlayer;

/**
 * The Interface GamesManager manages all games (GameEnvironments) at the server side, should be only called by the
 * service and the LocalGame
 * 
 * @author spatzenegger
 */
public interface GamesManager {

	/**
	 * Creates the game.
	 * 
	 * @param maxPlayers the max players
	 * @return the server game
	 * @throws BaseException if parameters for the game are invalid
	 */
	GameEnvironment createGame(int maxPlayers, IntPoint size) throws BaseException;

	/**
	 * Gets the game.
	 * 
	 * @param gameId the game id
	 * @return the game
	 */
	GameEnvironment getGame(int gameId);

	/**
	 * Gets the pending games.
	 * 
	 * @return the pending games
	 */
	Collection<GameEnvironment> getPendingGames();

	/**
	 * Gets the player.
	 * 
	 * @param id the id
	 * @return the player or null
	 */
	ServerPlayer getPlayer(int id);

	/**
	 * Join game.
	 * 
	 * @param game the game
	 * @param player the player
	 * @return true, if successful
	 */
	boolean joinGame(GameEnvironment game, ServerPlayer player);

	/**
	 * Leave game.
	 * 
	 * @param game the game
	 * @param player the player
	 * @return true, if successful
	 */
	boolean leaveGame(GameEnvironment game, ServerPlayer player);

	/**
	 * Login.
	 * 
	 * @param name the name
	 * @return the server player
	 */
	ServerPlayer login(String name);

	/**
	 * Start game.
	 * 
	 * @param gameId the game id
	 * @throws BaseException the base exception
	 */
	public void initSingleGame(GameEnvironment game) throws BaseException;

	void removeGame(GameEnvironment game);

	void removePlayer(ServerPlayer player);

	Color getFreeColor(ServerPlayer player, ServerGame game);

}
