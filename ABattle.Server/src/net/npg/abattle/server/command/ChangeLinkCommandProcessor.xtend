package net.npg.abattle.server.command

import com.google.common.base.Optional
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.commands.ChangeLinkCommand
import net.npg.abattle.communication.command.commands.ServerReplyCommand
import net.npg.abattle.communication.service.ServerService

class ChangeLinkCommandProcessor implements CommandProcessor<ChangeLinkCommand> {

	final ServerService service

	new(ServerService service) {
		Validate.notNull(service)
		this.service = service
	}

	override execute(ChangeLinkCommand command, int destination) {
		Validate.notNull(command)

		val result = service.link(command.game, command.originatedPlayer, command.startCell, command.endCell, command.create)
		if(!result.success) {
			val reply = new ServerReplyCommand(result, command.originatedPlayer, true, command.game)
			return Optional.of(reply)
		} else {
			return Optional.absent
		}
	}

}
