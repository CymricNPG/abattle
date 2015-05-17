/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.game;

import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.IDElement;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.utils.Disposeable;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.MyRunnable;
import net.npg.abattle.communication.command.CommandQueueServer;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerPlayer;

/**
 * each game created has its own GameEnvironment
 * 
 * @author spatzenegger
 * 
 */
public interface GameEnvironment extends IDElement, Disposeable {

	void addNewPlayer(ServerPlayer player) throws BaseException;

	void attachThread(MyRunnable gameThread);

	/**
	 * returns null
	 * 
	 * @param player
	 * @return
	 */
	ClientGame getClientGame(ServerPlayer player);

	ServerGame getServerGame();

	boolean link(ServerPlayer player, IntPoint startPoint, IntPoint endPoint, boolean create);

	boolean removePlayer(ServerPlayer player);

	/**
	 * start the game, start the game logic thread
	 */
	void startGame(CommandQueueServer commandQueue);

}
