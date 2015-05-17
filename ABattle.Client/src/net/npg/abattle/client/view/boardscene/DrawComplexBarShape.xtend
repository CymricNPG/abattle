package net.npg.abattle.client.view.boardscene

import java.util.List
import net.npg.abattle.client.view.renderer.Renderer
import net.npg.abattle.common.utils.ColorConvert
import net.npg.abattle.common.utils.FloatHolder
import net.npg.abattle.common.utils.FloatPoint

class DrawComplexBarShape {

	static def draw(Renderer renderer, ComplexBarShape shape) {
		val List<Float> sizes = shape.getSizes()
		val FloatPoint startCoordinate = shape.getStartCoordinate()
		val FloatPoint endCoordinate = shape.getEndCoordinate()
		val startCoordinates = getStartCoordinates(sizes).iterator
		val endCoordinates = getEndCoordinates(sizes).iterator
		val colors = shape.getColors.iterator
		val distance = endCoordinate.x - startCoordinate.x
		for (int i : 0 .. (sizes.size)-1) {
			val color = ColorConvert.convert(colors.next)
			val spos = startCoordinate.x + distance * startCoordinates.next
			val epos = startCoordinate.x + distance * endCoordinates.next
			val start = new FloatPoint(spos, startCoordinate.y);
			val end = new FloatPoint(epos, endCoordinate.y);
			renderer.drawBox(start, end, color)
		}
	}

	private static def getStartCoordinates(Iterable<Float> sizes) {
		val startPos = new FloatHolder
		sizes.map[startPos.replace(startPos.value + it)]
	}

	private static def getEndCoordinates(Iterable<Float> sizes) {
		val endPos = new FloatHolder
		sizes.map[endPos.add(it)]
	}
}
