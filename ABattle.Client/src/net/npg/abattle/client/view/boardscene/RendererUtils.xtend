package net.npg.abattle.client.view.boardscene

import net.npg.abattle.common.utils.ColorConvert
import net.npg.abattle.common.model.client.ClientCell
import com.badlogic.gdx.graphics.Color

class RendererUtils {

	/**
	 * animation timer
	 */
	private static final long startTime = System.currentTimeMillis

	/**
	 * returns a number between 0 and animationCount, which is increased every 1/animationsPerSecond
	 */
	public def static getAnimationNumber(float animationsPerSecond, int animationCount) {
		val time = System.currentTimeMillis - startTime
		return (time as float * animationsPerSecond / 1000.0) as int % animationCount
	}

	public def static  Color getOwnerColor(ClientCell cell) {
		val owner = cell.owner
		if (owner.present) {
			return ColorConvert.convert(owner.get.color)
		} else {
			return Color.WHITE
		}
	}
}
