package net.npg.abattle.client.commands

import com.google.common.base.Optional
import net.npg.abattle.common.model.client.ClientGame
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.commands.GameFinishedCommand

class GameFinishedProcessor implements CommandProcessor<GameFinishedCommand> {

	ClientGame game

	new(ClientGame game) {
		Validate.notNull(game)
		this.game = game
	}

	override execute(GameFinishedCommand command, int destination) {
		Validate.notNull(command)
		game.stopGame
		Optional.absent
	}

}
