package net.npg.abattle.communication.command.commands

import net.npg.abattle.common.utils.TransferData
import net.npg.abattle.communication.command.data.BoardUpdateData

@TransferData  
public class BoardUpdateCommand extends CommandImpl {
	public BoardUpdateData boardUpdate
}
