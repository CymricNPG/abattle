package net.npg.abattle.server.logic

import net.npg.abattle.common.configuration.GameConfigurationData
import net.npg.abattle.common.model.CellTypes
import net.npg.abattle.server.model.ServerCell

import static net.npg.abattle.common.utils.Controls.*

class LogicHelper {

	protected static def updateCellGrowth(ServerCell cell, GameConfigurationData configuration) {
		val structureGrowth = if(cell.cellType == CellTypes.BASE) configuration.baseGrowthPerTick else configuration.
				townGrowthPerTick
		returnif(!cell.hasStructure() || !cell.owner.present)
		val oldStrength = cell.strength
		returnif(oldStrength >= configuration.maxCellStrength)
		val strengthGrow = Math.min(configuration.maxCellStrength - oldStrength, structureGrowth)
		cell.addStrength(strengthGrow)
	}

	protected static def calcMaxMovement(ServerCell source, ServerCell destination, GameConfigurationData configuration) {
		val gradient = (source.getHeight() - destination.getHeight()) as double / configuration.maxCellHeight;
		val movement = configuration.maxMovement * (0.5 + configuration.terrainInfluence/100.0 * gradient)
		Math.max(0, Math.min(movement, source.strength))
	}
}
