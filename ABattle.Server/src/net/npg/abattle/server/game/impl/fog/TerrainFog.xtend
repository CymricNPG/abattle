package net.npg.abattle.server.game.impl.fog

import net.npg.abattle.common.model.client.ClientCell
import net.npg.abattle.server.model.ServerBoard
import net.npg.abattle.server.model.ServerPlayer

class TerrainFog implements Fog {

	override getName() {
		return "terrainfog"
	}
	
	override isVisible(ClientCell[][] clientBoard, ServerBoard board, ServerPlayer player, ClientCell cell) {
		return true
	}

}