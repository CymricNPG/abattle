package net.npg.abattle.client.view.screens

import net.npg.abattle.client.dependent.MultiplayerScreen
import net.npg.abattle.client.dependent.UIDialogComponent
import net.npg.abattle.common.component.ComponentLookup

class CloudScreen extends BasicScreen {

	private volatile MultiplayerScreen multiplayerScreen

	new() {
		super()

	}

	override create() {
		widgets.addButton(Layout.Back, Icons.Back, [switcher.switchToScreen(Screens.Main)])
		getMultiplayerScr().create(widgets, stage, this)
	}

	override render() {
		getMultiplayerScr().setSwitcher(switcher) //bad hack
	}

	override backButton() {
		switcher.switchToScreen(Screens.Main)
	}

	override getType() {
		Screens.Cloud
	}

	private def getMultiplayerScr() {
		if(this.multiplayerScreen == null) {
			this.multiplayerScreen = ComponentLookup.instance.getComponent(UIDialogComponent).multiplayerScreen
		}
		return this.multiplayerScreen
	}

	override String getDefaultText() {
		getMultiplayerScr().defaultText

	}
}
