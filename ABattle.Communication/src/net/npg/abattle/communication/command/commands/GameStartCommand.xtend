package net.npg.abattle.communication.command.commands

import net.npg.abattle.common.utils.TransferData
import net.npg.abattle.communication.command.data.CellUpdateData
import net.npg.abattle.communication.command.data.PlayerData

@TransferData
class GameStartCommand extends CommandImpl {
	public PlayerData[] players
	
	public CellUpdateData[][] cellUpdates

}