package net.npg.abattle.server.game.impl

import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandQueueServer
import net.npg.abattle.communication.command.CommandType
import net.npg.abattle.communication.command.commands.GameUpdateCommand
import net.npg.abattle.communication.command.data.StatisticsData
import net.npg.abattle.server.game.GameEnvironment

class StatisticsSender {

	GameEnvironment game

	CommandQueueServer commandQueue

	new(GameEnvironment game, CommandQueueServer commandQueue) {
		Validate.notNull(game)
		Validate.notNull(commandQueue)

		this.game = game
		this.commandQueue = commandQueue;
	}

	def sendGameData() {
		val statistics = game.serverGame.players.map[new StatisticsData(id, strength)].toList
		val destinations = game.serverGame.players.map[id].toList
		val data = new GameUpdateCommand(statistics, true, game.id)
		commandQueue.addCommand(data, CommandType.TOCLIENT, destinations)
	}

}
