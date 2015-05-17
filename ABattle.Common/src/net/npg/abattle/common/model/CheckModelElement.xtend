package net.npg.abattle.common.model

import net.npg.abattle.common.utils.IntPoint

interface CheckModelElement {

	def void checkCoordinate(IntPoint coordinate)

	def void checkStrength(int strength)

	def void checkHeight(int height)
}
