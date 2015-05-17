package net.npg.abattle.communication.command.commands

import net.npg.abattle.common.utils.TransferData
import net.npg.abattle.communication.command.ErrorCommand
import net.npg.abattle.communication.service.common.BooleanResult

@TransferData
class ServerReplyCommand extends CommandImpl implements ErrorCommand {

	public BooleanResult result

	public int destinationId

	override int getDestinationId() {
		return this.destinationId
	}
}
