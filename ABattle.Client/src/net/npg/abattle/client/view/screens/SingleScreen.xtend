package net.npg.abattle.client.view.screens

import net.npg.abattle.client.local.LocalGameStartup
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent

/**
 * single player screen
 */
class SingleScreen extends BasicScreen {

	GameParameterPartScreen parameterScreen

	override create() {
		parameterScreen = new GameParameterPartScreen(widgets, false, this)
		parameterScreen.create([|startGame], stage)
	}

	def startGame() {
		parameterScreen.finish
		configuration.save
		pause
		val screen = new LocalGameStartup(parameterScreen.getGameParameters(), switcher).run()
		switcher.switchToScreen(screen)
	}

	override render() {
	}

	override backButton() {
		switcher.switchToScreen(Screens.Main)
	}

	override getType() {
		Screens.Single
	}

	private def getConfiguration() {
		ComponentLookup.getInstance.getComponent(ConfigurationComponent)
	}
}
