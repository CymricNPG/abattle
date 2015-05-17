package net.npg.abattle.communication.command.data

import net.npg.abattle.common.utils.TransferData

@TransferData
public class LinkUpdateData {
	
	public int id
	
	public int startCellId
	
	public int endCellId
}