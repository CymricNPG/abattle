package net.npg.abattle.server.game.impl.terrain

import java.util.Random
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.configuration.GameConfigurationData
import net.npg.abattle.common.model.CheckModelElement
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.server.game.TerrainCreator
import net.npg.abattle.server.model.impl.ServerBoardImpl

class RandomTerrain implements TerrainCreator {

	private final Random rand = new Random(System.currentTimeMillis)

	private final GameConfigurationData configuration

	public new() {
		configuration = ComponentLookup.getInstance().getComponent(ConfigurationComponent).gameConfiguration
	}

	override createBoard(ServerBoardImpl board, IntPoint size, CheckModelElement checker) {
		BoardHelper.fillBoard(board, size, checker, [x, y|rand.nextInt(configuration.maxCellHeight)])
	}
	override getName() {
		"terrain.random"
	}
}