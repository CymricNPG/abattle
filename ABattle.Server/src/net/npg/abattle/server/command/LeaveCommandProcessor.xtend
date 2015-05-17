package net.npg.abattle.server.command

import com.google.common.base.Optional
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.commands.LeaveCommand
import net.npg.abattle.communication.service.ServerService

class LeaveCommandProcessor implements CommandProcessor<LeaveCommand> {

	final ServerService service

	new(ServerService service) {
		Validate.notNull(service)
		this.service = service
	}

	override execute(LeaveCommand command, int destination) {
		Validate.notNull(command)
		service.leaveGame(command.game, command.originatedPlayer)
		return Optional.absent
	}

}