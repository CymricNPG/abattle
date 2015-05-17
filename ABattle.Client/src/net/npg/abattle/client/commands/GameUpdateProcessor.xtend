package net.npg.abattle.client.commands

import com.google.common.base.Optional
import net.npg.abattle.common.model.client.ClientGame
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.commands.GameUpdateCommand

class GameUpdateProcessor implements CommandProcessor<GameUpdateCommand> {

	ClientGame game

	new(ClientGame game) {
		Validate.notNull(game)
		this.game = game
	}

	override execute(GameUpdateCommand command, int destination) {
		Validate.notNull(command)

		for (statistic : command.statistics) {
			val player = game.players.findFirst[id == statistic.playerId]
			if (player == null) {
				throw new IllegalArgumentException("Server send unknown player:" + statistic.playerId)
			}
			player.strength = statistic.strength
		}

		Optional.absent
	}

}
