package net.npg.abattle.client.commands

import com.google.common.base.Optional
import net.npg.abattle.common.model.client.ClientGame
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.commands.PlayerJoinedCommand

class PlayerJoinedProcessor implements CommandProcessor<PlayerJoinedCommand> {

	ClientGame game

	new(ClientGame game) {
	Validate.notNulls(game)
		this.game = game
	}

	override execute(PlayerJoinedCommand command, int destination) {
		Validate.notNull(command)
		UpdatePlayerList.update(command.players,game)
		Optional.absent
	}

}
