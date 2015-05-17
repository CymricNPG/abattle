package net.npg.abattle.server.service.impl;

import net.npg.abattle.common.utils.MyRunnable
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandQueueServer
import net.npg.abattle.communication.command.CommandType
import net.npg.abattle.communication.command.commands.GameCountdownCommand
import net.npg.abattle.server.ServerConstants
import net.npg.abattle.server.game.GameEnvironment
import net.npg.abattle.server.game.impl.DataHelper
import net.npg.abattle.server.game.impl.InitGameSender

import static net.npg.abattle.common.utils.Controls.*

/**
 * @author Cymric
 *
 */
class StartGameThread extends MyRunnable {

	final GameEnvironment game

	CommandQueueServer commandQueue

	new(GameEnvironment game, CommandQueueServer commandQueue) {
		super("StartGameThread")
		Validate.notNulls(game, commandQueue)
		this.game = game
		this.commandQueue = commandQueue
		game.attachThread(this)
	}

	override execute() throws Exception {
		try {
			for (var i = 3; i > 0; i--) {
				sendCountdown(i)
				for (var t = 0; t < 10; t++) {
					returnif(isStopped)
					Thread.sleep(100)
				}
			}
			game.serverGame.startGame(new InitGameSender(game, commandQueue))
			game.startGame(commandQueue)
		} catch(Exception e) {
			ServerConstants.LOG.error(e.getMessage(), e)
			game.serverGame.stopGame
		}
	}

	private def sendCountdown(int i) {
		val playersData = DataHelper.createPlayerData(game);
		val command = new GameCountdownCommand(playersData, i, true, game.id)
		val destinations = game.serverGame.players.map[id].toList
		commandQueue.addCommand(command, CommandType.TOCLIENT, destinations)
	}

}
