/**
 *
 */
package net.npg.abattle.client.lan;

import net.npg.abattle.common.error.ErrorMessage;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.utils.Disposeable;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.communication.command.CommandQueueClient;
import net.npg.abattle.communication.command.CommandUpdateNotifier;

import com.google.common.base.Optional;

/**
 * @author Cymric
 *
 */
public interface ClientGameEnvironment extends CommandUpdateNotifier, Disposeable {
	ErrorMessage createGame(IntPoint size, int playerCount);

	ErrorMessage joinGame(int gameId);

	CommandQueueClient getCommandQueue();

	int login(String name);

	ClientGame getGame();

	void leave();

	boolean checkGameStart();

	Optional<Integer> getRemainingCount();

	void ping();
}
