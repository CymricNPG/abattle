package net.npg.abattle.communication.command.commands

import net.npg.abattle.common.utils.TransferData
import net.npg.abattle.communication.command.data.PlayerData

@TransferData
class PlayerLeftCommand extends CommandImpl {
	public PlayerData player
}