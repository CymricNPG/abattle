package net.npg.abattle.communication.network.impl

import net.npg.abattle.common.configuration.NetworkConfigurationData
import net.npg.abattle.common.utils.LoopRunnable
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.network.NetworkClient
import net.npg.abattle.communication.network.data.NetworkGameInfo
import com.google.common.base.Optional

class LocalSearchThread extends LoopRunnable {

	private Optional<NetworkGameInfo> gameFound

	private final NetworkConfigurationData configuration

	private final NetworkClient client

	new(NetworkConfigurationData configuration) {
		super("LocalSearch")
		Validate.notNull(configuration)
		gameFound = Optional.absent
		this.configuration = configuration
		client = new NetworkClientImpl(configuration)
	}

	override innerLoop() {
		if(gameFound == null) {
			gameFound = client.discoverHostAndGame
		} else {
			Thread.sleep(1000)
		}

	}

	def public Optional<NetworkGameInfo> getGame() {
		gameFound
	}

	def public NetworkClient getClient() {
		client
	}

}
