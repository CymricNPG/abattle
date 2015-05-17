package net.npg.abattle.communication.service.common

import net.npg.abattle.common.utils.TransferData

@TransferData
class GameParameterInfo {
	public int maxCellStrength
	public int maxCellHeight
	public int maxMovement
	public MutableIntPoint size
}
