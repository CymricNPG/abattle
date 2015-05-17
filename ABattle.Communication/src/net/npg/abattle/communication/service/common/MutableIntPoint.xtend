package net.npg.abattle.communication.service.common

import net.npg.abattle.common.utils.TransferData
import net.npg.abattle.common.utils.IntPoint

/**
 * a transferable intpoint
 */
@TransferData
class MutableIntPoint {

	public int x

	public int y

	override int hashCode() {
		val prime = 31
		var result = 1
		result = prime * result + x
		result = prime * result + y
		return result
	}

	override boolean equals(Object obj) {
		if(this == obj) {
			return true
		}
		if(obj == null) {
			return false
		}
		if(!(obj instanceof MutableIntPoint)) {
			return false
		}
		val other = obj as MutableIntPoint
		if(x != other.x) {
			return false
		}
		if(y != other.y) {
			return false
		}
		return true
	}

	def static MutableIntPoint from(int x, int y) {
		val point = new MutableIntPoint
		point.x = x
		point.y = y
		return point
	}

	def static MutableIntPoint from(IntPoint ipoint) {
		val point = new MutableIntPoint
		point.x = ipoint.x
		point.y = ipoint.y
		return point
	}

	def IntPoint to() {
		return IntPoint.from(x, y)
	}
}
