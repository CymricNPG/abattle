package net.npg.abattle.client.view.screens

import static net.npg.abattle.client.view.screens.Layout.*

abstract class WinLooseScreen extends BasicScreen {

	def create(Icons icon) {
		widgets.addImage(50, 50, icon)
		widgets.addButton(Back, Icons.Back, [|switcher.switchToScreen(Screens.Main)])
	}

	override render() {
	}

	override backButton() {
		switcher.switchToScreen(Screens.Main)
	}

}
