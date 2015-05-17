package net.npg.abattle.server.game.impl.terrain

import java.util.Collections
import java.util.List
import java.util.Random
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.configuration.GameConfigurationData
import net.npg.abattle.common.model.CheckModelElement
import net.npg.abattle.common.utils.FieldLoop
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.common.utils.IntPoint3
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.server.ServerConstants
import net.npg.abattle.server.game.TerrainCreator
import net.npg.abattle.server.model.impl.ServerBoardImpl

import static net.npg.abattle.common.utils.Controls.*
import static net.npg.abattle.common.utils.MyArrayLiterals.*

class XBattleTerrain implements TerrainCreator {

	public static final String NAME = "terrain.xbattle"

	private final Random rand = new Random(System.currentTimeMillis)

	private final GameConfigurationData configuration

	public new() {
		configuration = ComponentLookup.getInstance().getComponent(ConfigurationComponent).gameConfiguration
	}

	override createBoard(ServerBoardImpl board, IntPoint size, CheckModelElement checker) {
		Validate.notNull(size);
		Validate.notNulls(checker);
		Validate.exclusiveBetween(ServerConstants.MIN_XSIZE, ServerConstants.MAX_XSIZE, size.x);
		Validate.exclusiveBetween(ServerConstants.MIN_YSIZE, ServerConstants.MAX_YSIZE, size.y);
		val peaks = calculatePeaks(size)
		val heightField = calculateHeights(size, peaks)
		BoardHelper.fillBoard(board, size, checker, [x, y|heightField.get(x).get(y).intValue])
	}

	def Double[][] calculateHeights(IntPoint size, List<IntPoint3> peaks) {
		val Double[][] heights = createArray(size.x, size.y)
		FieldLoop.visitAllFields(size, [x, y|heights.get(x).set(y, calcHeight(x, y, peaks))])
		return heights
	}

	def calcHeight(int x, int y, List<IntPoint3> peaks) {
		var count = 0
		var height = 0.0
		for (peak : peaks) {
			val distance = Math.sqrt(Math.pow(peak.x - x, 2) + Math.pow(peak.y - y, 2))
			if(distance < 0.1f) {
				return configuration.maxCellHeight - 1
			}
			continueif(distance > configuration.maxCellHeight)
			var peakHeight = peak.z as double * (configuration.maxCellHeight - distance + 1) / configuration.maxCellHeight
			height += peakHeight
			count++
		}
		return uniformHeight(height / count)
	}

	private def uniformHeight(double h) {
		return Math.min(Math.max(h, 0.0), configuration.maxCellHeight - 1)
	}

	private def calculatePeaks(IntPoint size) {
		val count = Math.min(configuration.peakCount * size.x * size.y / 100, 100);
		if(count == 0) {
			return Collections.emptyList
		}
		val peaks = newArrayList()
		for (var i = 0; i < count; i++) {
			peaks.add(new IntPoint3(rand.nextInt(size.x), rand.nextInt(size.y), configuration.maxCellHeight))
		}
		peaks
	}

	override getName() {
		NAME
	}

}
