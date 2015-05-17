package net.npg.abattle.client.view.screens

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import java.net.InetAddress
import java.util.ArrayList
import net.npg.abattle.client.lan.LANGameStartup
import net.npg.abattle.communication.network.data.NetworkGameInfo

import static net.npg.abattle.common.utils.Controls.*
import static net.npg.abattle.client.view.screens.Layout.*
import net.npg.abattle.common.i18n.I18N

class LocalScreen extends BasicScreen {

	ArrayList<NetworkGameInfo> games

	GameSearcher gameSearcher

	Table table

	final static float CELLPAD = 4

	override create() {
		games = newArrayList
		initGames
		table = new Table

		fillTable

		val scrollpane = new ScrollPane(table)
		scrollpane.setSize(LOCAL_SIZE.x, LOCAL_SIZE.y)
		scrollpane.setPosition(LOCAL_SCROLL.x, LOCAL_SCROLL.y)
		stage.addActor(scrollpane)

		widgets.addButton(Back, Icons.Back, [|exitScreen(Screens.Main)])
		widgets.addButton(LOCAL_NEW, Icons.New, [|exitScreen(Screens.New)])
	}

	private def exitScreen(Screens to) {
		if (gameSearcher != null) {
			gameSearcher.exit = true
			gameSearcher = null
		}
		switcher.switchToScreen(to)
	}

	def fillTable() {
		table.clear
		table.add(widgets.createLabel("IP")).pad(CELLPAD)
		table.add(widgets.createLabel("Name")).pad(CELLPAD)
		table.add(widgets.createLabel(" # / # ")).pad(CELLPAD)
		table.add(widgets.createLabel("")).pad(CELLPAD)
		table.row
		for (game : games) {
			table.add(widgets.createLabel(game.ipAddr.hostAddress)).pad(CELLPAD)
			table.add(widgets.createLabel(game.gameName)).pad(CELLPAD)
			table.add(widgets.createLabel(" " + game.currentPlayer + " / " + game.maxPlayer + " ")).pad(CELLPAD)
			table.add(widgets.createButton(if(game.gameId == -1) Icons.Join else Icons.JoinActivate, [|startGame(game)], if(game.gameId == -1) true else false)).
				pad(CELLPAD)
			table.row
		}
	}

	private def startGame(NetworkGameInfo game) {
		returnif(game.gameId < 0)
		gameSearcher.exit = true
		gameSearcher = null
		val startup = new LANGameStartup(game.gameId, game.ipAddr)
		startup.join
		switcher.switchToScreen(Screens.Waiting, startup)
	}

	private def startNetworkBoradcast() {
		if (gameSearcher == null) {
			gameSearcher = new GameSearcher()
			new Thread(gameSearcher).start
		}
	}

	private def initGames() {
		games.add(new NetworkGameInfo(InetAddress.getLoopbackAddress, I18N.get("local_search"), 0, 8, -1))
	}

	override render() {
		startNetworkBoradcast
		queryGames
	}

	private def void queryGames() {
		returnif(!gameSearcher.newDataAvailable)
		var newGames = gameSearcher.games
		returnif(newGames.empty)
		games.clear
		games.addAll(newGames)
		fillTable
	}
override backButton() {
		switcher.switchToScreen(Screens.Main)
	}
	
		override getType() {
		Screens.Local
	}
	
}
