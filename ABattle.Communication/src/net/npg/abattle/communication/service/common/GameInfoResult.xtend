package net.npg.abattle.communication.service.common

import net.npg.abattle.common.utils.TransferData

@TransferData
class GameInfoResult extends BooleanResult {

	public GameInfo gameInfo;

	/**
	 * id of game
	 */
	public int id;
}
