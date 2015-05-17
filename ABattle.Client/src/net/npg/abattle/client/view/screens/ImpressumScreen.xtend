package net.npg.abattle.client.view.screens

import static net.npg.abattle.client.view.screens.Layout.*
import net.npg.abattle.common.CommonConstants

class ImpressumScreen extends BasicScreen {

	private static String text = "ABattle V"+CommonConstants.VERSION+"

Roland Spatzenegger
Wiesbachhornstr. 13
81825 Muenchen
Telefon: 089-40268100
E-Mail: c@npg.net
"

	override create() {
		widgets.addLabel(IMPR_TEXT.x, IMPR_TEXT.y, text)
		widgets.addButton(Back, Icons.Back, [|switcher.switchToScreen(Screens.Main)])
	}

	override render() {
	}

	override backButton() {
		switcher.switchToScreen(Screens.Main)
	}

	override getType() {
		Screens.Impressum
	}

}
