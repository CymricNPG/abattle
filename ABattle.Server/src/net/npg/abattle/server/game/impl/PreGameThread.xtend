package net.npg.abattle.server.game.impl

import net.npg.abattle.common.utils.MyRunnable
import net.npg.abattle.communication.command.CommandQueue
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.server.game.GameEnvironment
import net.npg.abattle.communication.command.commands.PingCommand
import net.npg.abattle.communication.command.CommandType

class PreGameThread extends MyRunnable {

	CommandQueue commandQueue

	GameEnvironment game

	new(CommandQueue commandQueue, GameEnvironment game) {
		super("PreGameThread")
		Validate.notNulls(commandQueue,game)
		this.commandQueue = commandQueue
		this.game = game;
	}

	override execute() throws Exception {
		while(!stopped && !game.disposed && !game.serverGame.status.running) {
			sendPing
			Thread.sleep(1000L);
			lifeCycleWait
		}
	}

	def sendPing() {
		val destinationIds = game.serverGame.players.map[id].toList
		commandQueue.addCommand(new PingCommand,CommandType.TOCLIENT,destinationIds)
	}

}
