package net.npg.abattle.communication.command.data

import net.npg.abattle.common.utils.TransferData

@TransferData
public class CellUpdateData {
	public int id
	public int owner
	public boolean battle
	public int strength
}

