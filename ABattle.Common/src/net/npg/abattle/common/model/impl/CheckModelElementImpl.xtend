package net.npg.abattle.common.model.impl

import net.npg.abattle.common.CommonConstants
import net.npg.abattle.common.model.CheckModelElement
import net.npg.abattle.common.model.GameConfiguration
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.common.utils.Validate

package class CheckModelElementImpl implements CheckModelElement {
		GameConfiguration configuration

	protected new(GameConfiguration configuration) {
		Validate.notNull(configuration)
		this.configuration = configuration
	}

	override checkCoordinate(IntPoint coordinate) {
		Validate.notNull(coordinate)
		Validate.inclusiveBetween(0, CommonConstants.MAX_BOARD_SIZE - 1, coordinate.x);
		Validate.inclusiveBetween(0, CommonConstants.MAX_BOARD_SIZE - 1, coordinate.y)
	}

	override checkStrength(int strength) {
		Validate.inclusiveBetween(0, configuration.configuration.maxCellStrength, strength)
	}
	
	override checkHeight(int height) {
		Validate.inclusiveBetween(0, configuration.configuration.maxCellHeight-1, height)
	}
}