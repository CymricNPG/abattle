package net.npg.abattle.communication.service.common

import net.npg.abattle.common.utils.TransferData

@TransferData
class GameInfo {

	/** The current players. */
	public int currentPlayers

	/** The max players. */
	public int maxPlayers

	/** The players. */
	public ClientInfo[] players
	
	public GameParameterInfo parameters
}
