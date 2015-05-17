package net.npg.abattle.client.local

import net.npg.abattle.client.lan.ClientGameEnvironment
import net.npg.abattle.common.model.client.ClientGame
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.communication.command.CommandQueueClient
import net.npg.abattle.communication.command.GameCommand
import com.google.common.base.Optional
import net.npg.abattle.communication.command.commands.InitBoardCommand
import net.npg.abattle.common.utils.impl.DisposeableImpl

class ClientGameEnvironmentLocal extends DisposeableImpl implements ClientGameEnvironment {

	ClientGame game

	CommandQueueClient commandQueue

	int gameStarted = 0

	new(ClientGame game, CommandQueueClient commandQueue) {
		this.game = game
		this.commandQueue = commandQueue
	}

	override createGame(IntPoint size, int playerCount) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override getCommandQueue() {
		commandQueue
	}

	override getGame() {
		game
	}

	override joinGame(int gameId) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override login(String name) {

		//throw new UnsupportedOperationException("TODO: auto-generated method stub")
		return 0
	}

	override leave() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override checkGameStart() {
		gameStarted > 0
	}

	override receivedCommand(GameCommand command) {
		if (command instanceof InitBoardCommand) {
			gameStarted++
		}
	}

	override getRemainingCount() {
		return Optional.absent
	}

	override dispose() {
		super.dispose
		commandQueue.dispose
	}
	
	override ping() {
	}

}
