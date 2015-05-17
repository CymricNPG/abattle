package net.npg.abattle.communication.command.commands

import net.npg.abattle.common.utils.TransferData
import net.npg.abattle.communication.command.data.InitBoardData

@TransferData
class InitBoardCommand extends CommandImpl {
	public InitBoardData initBoard
}
