package net.npg.abattle.common.utils

class Transformation {
	def static FloatPoint translate(FloatPoint source, float x, float y) {
		new FloatPoint(source.x + x, source.y + y)
	}
}
