/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.model;

import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.Game;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.utils.IntPoint;

/**
 * The Interface ServerGame.
 * 
 * @author spatzenegger
 */
public interface ServerGame extends Game<ServerPlayer, ServerCell, ServerLinks> {

	@Override
	void addPlayer(ServerPlayer player) throws BaseException;

	int getMaxPlayers();

	int getXSize();

	int getYSize();

	IntPoint getSize();

	/**
	 * get the game configuration
	 * 
	 * @return
	 */
	@Override
	GameConfiguration getGameConfiguration();

	/**
	 * notify the model to start the game
	 * 
	 * @throws BaseException if game start fails
	 */
	void startGame(BoardInitializedNotifier notifier) throws BaseException;

}
