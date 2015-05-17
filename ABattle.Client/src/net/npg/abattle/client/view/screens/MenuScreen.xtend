package net.npg.abattle.client.view.screens

import com.badlogic.gdx.Gdx
import net.npg.abattle.common.i18n.I18N

import static net.npg.abattle.client.view.screens.Layout.*

class MenuScreen extends BasicScreen {

	override create() {
		addButton(Menu_Option, Icons.Options, Screens.Options, Screens.Main)
		addButton(Menu_Single, Icons.Single, Screens.Single)
		addButton(Menu_Cloud, Icons.Cloud, Screens.Cloud)
		addButton(Menu_Local, Icons.Local, Screens.Local)
		addButton(Menu_Help, Icons.Help, Screens.Help)
		addButton(Menu_Impr, Icons.Impressum, Screens.Impressum)

	}

	private def addButton(Layout position, Icons icon, Screens screen, Screens back) {
		widgets.addButton(position, icon, [|switcher.switchToScreen(screen, back)])
	}

	private def addButton(Layout position, Icons icon, Screens screen) {
		widgets.addButton(position, icon, [|switcher.switchToScreen(screen)])
	}

	override String getDefaultText() {
		I18N.get("menu_default")
	}

	override render() {
	}

	override backButton() {
		Gdx.app.exit()
	}

	override getType() {
		Screens.Main
	}

}
