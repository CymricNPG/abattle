package net.npg.abattle.client.view.screens

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import net.npg.abattle.client.lan.LANGameStartup
import net.npg.abattle.communication.CommunicationConstants
import org.eclipse.xtend.lib.annotations.Accessors

import static net.npg.abattle.client.view.screens.Layout.*

import static extension net.npg.abattle.common.utils.ColorConvert.*
import net.npg.abattle.common.i18n.I18N

class WaitingScreen extends BasicScreen implements ParameterScreen<LANGameStartup> {

	@Accessors LANGameStartup parameter

	Table table

	Label waitLabel

	long lastTime = 0L

	override create() {
		table = new Table
		table.add(widgets.createLabel(I18N.get("wait_name")))
		table.row

		val scrollpane = new ScrollPane(table)
		scrollpane.setSize(LOCAL_SIZE.x, LOCAL_SIZE.y)
		scrollpane.setPosition(LOCAL_SCROLL.x, LOCAL_SCROLL.y)
		stage.addActor(scrollpane)

		widgets.addButton(Back, Icons.Back, [|leaveGame])
		this.waitLabel = widgets.addLabel(WAIT_START.x, WAIT_START.y, I18N.get("wait_msg"))

	}

	override render() {
		updateTable
		checkGameStart
		addWaiting
		pingServer
	}

	def pingServer() {
		if(System.currentTimeMillis > lastTime + 1000L) {
			try {
				lastTime = System.currentTimeMillis
				parameter.ping
			} catch(Exception e) {
				CommunicationConstants.LOG.error(e.message, e)
				errorGame(e.message)
			}
		}
	}

	def errorGame(String errorMessage) {
		switcher.switchToScreen(Screens.Error, errorMessage)
	}

	def addWaiting() {
		if(parameter.checkCountdown.present) {
			waitLabel.text = I18N.format("wait_start", parameter.checkCountdown.get.toString)
		}
	}

	private def updateTable() {
		val players = parameter.players
		table.clear
		table.add(widgets.createLabel(I18N.get("wait_name")))
		table.row
		for (player : players) {
			table.add(widgets.createLabel(player.name, player.color.convert))
			table.row
		}
	}

	private def checkGameStart() {
		if(parameter.checkGameStart) {
			val screen = parameter.start(switcher)
			switcher.switchToScreen(screen)
		}
	}

	private def leaveGame() {
		parameter.leave
		switcher.switchToScreen(Screens.Main)
	}

	override backButton() {
		switcher.switchToScreen(Screens.Main)
	}

	override getType() {
		Screens.Waiting
	}

}
