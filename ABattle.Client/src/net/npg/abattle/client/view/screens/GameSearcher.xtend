package net.npg.abattle.client.view.screens

import com.google.common.base.Optional
import java.util.ArrayList
import java.util.List
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.utils.MyRunnable
import net.npg.abattle.communication.network.NetworkClient
import net.npg.abattle.communication.network.NetworkComponent
import net.npg.abattle.communication.network.data.NetworkGameInfo
import net.npg.abattle.client.ClientConstants
import org.eclipse.xtend.lib.annotations.Accessors

// TODO rework (racing condition ..) games get lost (e.g. if two games are in the network)
class GameSearcher extends MyRunnable {

	NetworkClient client

	private final ArrayList<NetworkGameInfo> games

	@Accessors
	volatile boolean newDataAvailable

	@Accessors
	volatile boolean exit

	new() {
		super("GameSearcher")
		games = newArrayList
		exit = false
		newDataAvailable = false
	}

	def List<NetworkGameInfo> getGames() {
		val List<NetworkGameInfo> retGames = newArrayList
		synchronized (games) {
			retGames.addAll(games)
			games.clear 
			newDataAvailable = false
		}
		retGames
	}

	override execute() {
		val networkComponent = ComponentLookup.getInstance.getComponent(NetworkComponent)
		client = networkComponent.createClient
		do {
			val game = client.discoverHostAndGame
			if (game.present) {
				ClientConstants.LOG.info("Found game:" + game.get.gameId)
			}
			addGame(game)
			lifeCycleWait
		} while (!exit && games.size == 0);
		client.dispose
	}

	def addGame(Optional<NetworkGameInfo> game) {
		synchronized (games) {
			if (game.present) {
				games.clear
				games.add(game.get)
			}
			newDataAvailable = true
		}
	}
}
