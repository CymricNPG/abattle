package net.npg.abattle.client.view.screens

import net.npg.abattle.client.asset.AssetManager
import net.npg.abattle.client.dependent.ReturnControl
import net.npg.abattle.client.dependent.UIDialogComponent
import net.npg.abattle.common.component.ComponentLookup

class HelpScreen extends BasicScreen implements ReturnControl {

	private volatile	boolean inHelpScreen = false;

	// TODO use same as in options
	new() {
		super()		
	}

	override create() {
		inHelpScreen = true
		val manager = ComponentLookup.getInstance.getComponent(AssetManager)
		val htmlText = manager.loadTextfile("help.html")
		ComponentLookup.getInstance().getComponent(UIDialogComponent).getRequestHandler().showHTMLView(htmlText, this)
	}

	override render() {
		if(!inHelpScreen) {
			backButton
		}
	}

	override backButton() {
		switcher.switchToScreen(Screens.Main)
	}

	override getType() {
		Screens.Help
	}

	override switchBack() {
		inHelpScreen = false
	}

}
