package net.npg.abattle.client.view.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen
import net.npg.abattle.client.startup.Startup
import net.npg.abattle.common.component.ExternalRegisterComponent
import net.npg.abattle.common.error.impl.SimpleErrorHandler
import net.npg.abattle.common.utils.LifecycleControl
import net.npg.abattle.common.utils.Validate

class MenuControl extends Game {

	private static boolean firstBoot = true

	ExternalRegisterComponent externalRegisterComponent

	new (ExternalRegisterComponent externalRegisterComponent) {
		Validate.notNulls(externalRegisterComponent)
		this.externalRegisterComponent = externalRegisterComponent
	}

	override create() {
		if(firstBoot) {
			LifecycleControl.getControl().setSuppressPaused(true);
			Startup.l0(new SimpleErrorHandler(),externalRegisterComponent)
			firstBoot = false
		} else {
			Startup.restart0
		}

		val screenManager = new ScreenManager(this)
		screenManager.init()
		screenManager.switchToScreen(Screens.Main)

	}

	override resize(int width, int height) {
		super.resize(width, height)
	}

	override void setScreen(Screen screen) {
		if(screen instanceof BasicScreen) {
			(screen as BasicScreen).init
		}
		super.setScreen(screen)
	}
}
