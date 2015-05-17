package net.npg.abattle.communication.command.data

import net.npg.abattle.common.utils.TransferData

@TransferData
public class BoardUpdateData  {
	public CellUpdateData[][] cellUpdates
	
	public LinkUpdateData[] linkUpdates
	
}
