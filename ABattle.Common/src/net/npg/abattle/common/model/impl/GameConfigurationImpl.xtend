package net.npg.abattle.common.model.impl

import net.npg.abattle.common.configuration.GameConfigurationData
import net.npg.abattle.common.model.CheckModelElement
import net.npg.abattle.common.model.GameConfiguration
import net.npg.abattle.common.model.ModelVisitor
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * TODO builder pattern
 */
class GameConfigurationImpl extends IDElementImpl implements GameConfiguration {
// TODO merge with GameConfigurationData

	@Accessors int xSize

	@Accessors int ySize

	@Accessors GameConfigurationData configuration

	CheckModelElement checker

	new() {
		this.checker = new CheckModelElementImpl(this)
	}

	override CheckModelElement getChecker() {
		this.checker
	}

	override accept(ModelVisitor visitor) {
		visitor.visit(this)
	}

}
