package net.npg.abattle.client.commands

import com.google.common.base.Optional
import net.npg.abattle.common.model.client.ClientGame
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.commands.PlayerLeftCommand

class PlayerLeftProcessor implements CommandProcessor<PlayerLeftCommand> {

	ClientGame game

	new(ClientGame game) {
		Validate.notNulls(game)
		this.game = game
	}

	override execute(PlayerLeftCommand command, int destination) {
		Validate.notNull(command)
		UpdatePlayerList.remove(command.player, game)
		Optional.absent
	}

}