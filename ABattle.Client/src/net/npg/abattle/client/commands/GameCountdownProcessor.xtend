package net.npg.abattle.client.commands;

import com.google.common.base.Optional
import net.npg.abattle.common.model.client.ClientGame
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.CommandUpdateNotifier
import net.npg.abattle.communication.command.commands.GameCountdownCommand

class GameCountdownProcessor implements CommandProcessor<GameCountdownCommand> {

	ClientGame game
	CommandUpdateNotifier notifier

	new(ClientGame game, CommandUpdateNotifier notifier) {
	Validate.notNulls(game, notifier)
		this.game = game
		this.notifier = notifier
	}

	override execute( GameCountdownCommand command,  int destination) {
		Validate.notNull(command)
		UpdatePlayerList.update(command.players,game)
		notifier.receivedCommand(command)
		Optional.absent
	}

}
