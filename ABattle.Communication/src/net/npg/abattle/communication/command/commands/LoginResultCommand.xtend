package net.npg.abattle.communication.command.commands

import net.npg.abattle.common.utils.TransferData
import net.npg.abattle.communication.service.common.ClientInfoResult

@TransferData
class LoginResultCommand {

	public ClientInfoResult result
}