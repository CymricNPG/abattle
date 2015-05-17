package net.npg.abattle.server.logic

import net.npg.abattle.server.model.ServerCell

class SimpleMoveDistribution {

	public ServerCell destinationCell
	public boolean hasFight
	public int maxMoveArmiesTo

	new(int maxMoveArmiesTo, ServerCell destinationCell, boolean hasFight) {
		this.maxMoveArmiesTo = maxMoveArmiesTo
		this.destinationCell = destinationCell
		this.hasFight = hasFight;
	}

}
