package net.npg.abattle.client.view.screens

import org.eclipse.xtend.lib.annotations.Accessors
import static net.npg.abattle.client.view.screens.Layout.*
import com.badlogic.gdx.scenes.scene2d.ui.Label

class ErrorScreen extends BasicScreen implements ParameterScreen<String> {

	@Accessors String parameter

	Label label

	override create() {
		label = widgets.addLabel(IMPR_TEXT.x, IMPR_TEXT.y, "...")
		label = widgets.addLabel(IMPR_TEXT.x, IMPR_TEXT.y, "...")
		widgets.addButton(Back, Icons.Back, [|switcher.switchToScreen(Screens.Main)])
	}

	override render() {
		label.text = "ERROR
" + parameter
	}

	override backButton() {
		switcher.switchToScreen(Screens.Main)
	}

	override getType() {
		Screens.Error
	}

}
