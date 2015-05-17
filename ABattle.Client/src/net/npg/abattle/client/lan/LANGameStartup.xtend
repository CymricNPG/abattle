package net.npg.abattle.client.lan

import java.net.InetAddress
import net.npg.abattle.client.GameBaseParameters
import net.npg.abattle.client.local.LocalClient
import net.npg.abattle.client.startup.Startup
import net.npg.abattle.client.view.screens.ScreenSwitcher
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.error.BaseException
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.common.utils.impl.DisposeableImpl

import static net.npg.abattle.common.utils.Asserts.*

/**
 * start a game server and connect the local client to it
 * TODO SOLID violation
 */
class LANGameStartup extends DisposeableImpl {

	GameBaseParameters model

	ClientGameEnvironment environment

	new(GameBaseParameters model) {
		Validate.notNull(model)
		this.model = model
	}

	int gameId

	InetAddress ipAddr

	new(int gameId, InetAddress ipAddr) {
		this.gameId = gameId
		this.ipAddr = ipAddr
	}

	def join() throws BaseException {
		var name = ComponentLookup.getInstance.getComponent(ConfigurationComponent).globalOptions.name
		if(name.isNullOrEmpty) {
			name = "Unknown"
		}
		environment = Startup.l15remote(name, ipAddr)
		val error = environment.joinGame(gameId)
		if(error.failed) {
			throw new BaseException(error.errorCode, error.errorMessage)
		}
	}

	def create() throws BaseException {
		Validate.notNull(model)
		Startup.l10
		environment = Startup.l15local(model.name)
		val error = environment.createGame(IntPoint.from(model.XSize, model.YSize), model.humanPlayers)
		if(error.failed) {
			throw new BaseException(error.errorCode, error.errorMessage)
		}
	}

	def leave() {
		environment.leave
		environment.dispose
	}

	def checkGameStart() {
		environment.checkGameStart
	}

	def checkCountdown() {
		environment.remainingCount
	}

	def start(ScreenSwitcher switcher) {
		val runnable = new LocalClient(switcher)
		return runnable.run(environment);
	}

	def getPlayers() {
		assertIt(environment != null)
		environment.game.players
	}

	def ping() {
		environment.ping
	}

	override dispose() {
		super.dispose
		if(environment != null) {
			environment.dispose
		}
	}

}
