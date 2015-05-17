package net.npg.abattle.client.view.screens

import com.badlogic.gdx.Game
import net.npg.abattle.common.utils.MyMap
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.common.utils.MyHashMap
import static net.npg.abattle.common.utils.Asserts.*

class ScreenManager implements ScreenSwitcher {

	MyMap<Screens, Class<? extends MyScreen>> screens

	MyScreen currentScreen

	Game control

	new(Game control) {
		this.screens = new MyHashMap()
		this.control = control
		this.currentScreen = null
	}

	def init() {
		loadScreen(Screens.Local, LocalScreen)
		loadScreen(Screens.Cloud, CloudScreen)
		loadScreen(Screens.Options, OptionsScreen)
		loadScreen(Screens.Help, HelpScreen)
		loadScreen(Screens.Main, MenuScreen)
		loadScreen(Screens.Single, SingleScreen)
		loadScreen(Screens.Win, WinScreen)
		loadScreen(Screens.Loose, LooseScreen)
		loadScreen(Screens.Waiting, WaitingScreen)
		loadScreen(Screens.New, NewScreen)
		loadScreen(Screens.Impressum, ImpressumScreen)
		loadScreen(Screens.Error, ErrorScreen)
	}

	private def loadScreen(Screens screenId, Class<? extends MyScreen> screen) {
		screens.put(screenId, screen)
	}

	override switchToScreen(Screens newScreen) {
		Validate.notNull(newScreen)
		val screen = instantiateScreen(newScreen)
		assertIt(!(screen instanceof ParameterScreen))
		switchControl(screen)
	}

	private def switchControl(MyScreen screen) {
		control.setScreen(screen)
		if(currentScreen != null) {
			currentScreen.dispose(true)
		}
		currentScreen = screen
	}

	override switchToScreen(Screens newScreen, Object parameter) {
		Validate.notNull(newScreen)
		val screen = instantiateScreen(newScreen)
		if (screen instanceof ParameterScreen) {
			screen.setParameter(parameter)
		}
		switchControl(screen)
	}

	private def instantiateScreen(Screens screen) {
		val screenClass = screens.get(screen)
		val newScreen = screenClass.newInstance
		newScreen.instantiate(this)
		return newScreen
	}

	override switchToScreen(MyScreen newScreen) {
		Validate.notNull(newScreen)
		assertIt(!(newScreen instanceof ParameterScreen))
		switchControl(newScreen)
	}
}
