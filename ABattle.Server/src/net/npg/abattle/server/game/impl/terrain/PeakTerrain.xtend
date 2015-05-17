package net.npg.abattle.server.game.impl.terrain

import java.util.List
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.configuration.GameConfigurationData
import net.npg.abattle.common.utils.IntPoint3

import static net.npg.abattle.common.utils.Controls.*

class PeakTerrain extends XBattleTerrain {

	private final GameConfigurationData configuration

	public new() {
		configuration = ComponentLookup.getInstance().getComponent(ConfigurationComponent).gameConfiguration
	}

	override calcHeight(int x, int y, List<IntPoint3> peaks) {
		var height = 0.0
		for (peak : peaks) {
			val distance = Math.sqrt(Math.pow(peak.x - x, 2) + Math.pow(peak.y - y, 2))
			if(distance < 0.1f) {
				return configuration.maxCellHeight - 1
			}
			continueif(distance > configuration.maxCellHeight)
			var peakHeight = peak.z as double * (configuration.maxCellHeight - distance ) / configuration.maxCellHeight / 1.5f
			if(Math.floor(peakHeight) == Math.floor(height) && peakHeight > configuration.maxCellHeight/2) {
				height += 2f
			} else {
				height = Math.max(peakHeight, height)
			}
		}
		return uniformHeight(height)
	}

	private def uniformHeight(double h) {
		return Math.min(Math.max(h, 0.0), configuration.maxCellHeight - 1)
	}

	override getName() {
		"terrain.peak"
	}

}
