package net.npg.abattle.communication.command.commands

import net.npg.abattle.communication.command.DeadCommand
import net.npg.abattle.common.utils.TransferData

@TransferData
class DeadCommandImpl extends CommandImpl implements DeadCommand {

	public String errorMessage

	override getErrorMessage() {
		errorMessage
	}

}
