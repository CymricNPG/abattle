package net.npg.abattle.client.view.screens

import net.npg.abattle.client.ClientConstants
import net.npg.abattle.client.lan.LANGameStartup
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent

class NewScreen extends BasicScreen {
	GameParameterPartScreen parameterScreen

	override create() {
		parameterScreen = new GameParameterPartScreen(widgets, true, this)
		parameterScreen.create([|startGame], stage)
	}

	def startGame() {
		parameterScreen.finish
		configuration.save
		pause
		val startup = new LANGameStartup(parameterScreen.getGameParameters())
		try {
			startup.create
		} catch(Exception e) {
			ClientConstants.LOG.error(e.message, e)
			startup.dispose
			switcher.switchToScreen(Screens.Error, e.message)
			return
		}
		switcher.switchToScreen(Screens.Waiting, startup)
	}

	override render() {
	}

	override backButton() {
		switcher.switchToScreen(Screens.Main)
	}

	override getType() {
		Screens.New
	}

	private def getConfiguration() {
		ComponentLookup.getInstance.getComponent(ConfigurationComponent)
	}
}
