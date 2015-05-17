package net.npg.abattle.communication.network.impl

import com.esotericsoftware.kryonet.Client
import com.esotericsoftware.kryonet.rmi.ObjectSpace
import com.esotericsoftware.kryonet.rmi.RemoteObject
import com.google.common.base.Optional
import java.net.InetAddress
import net.npg.abattle.common.configuration.NetworkConfigurationData
import net.npg.abattle.common.error.BaseException
import net.npg.abattle.common.utils.LifecycleControl
import net.npg.abattle.common.utils.StopListener
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.common.utils.impl.DisposeableImpl
import net.npg.abattle.communication.CommunicationConstants
import net.npg.abattle.communication.command.commands.LoginCommand
import net.npg.abattle.communication.network.NetworkClient
import net.npg.abattle.communication.network.NetworkErrorException
import net.npg.abattle.communication.network.data.NetworkGameInfo
import net.npg.abattle.communication.service.ServerService
import net.npg.abattle.communication.service.common.GameInfoResult

import static net.npg.abattle.common.utils.Asserts.*
import static net.npg.abattle.common.utils.Controls.*
import net.npg.abattle.communication.command.ReceiveHandler

/**
 * TODO complete rework!
 */
class NetworkClientImpl extends DisposeableImpl implements NetworkClient, StopListener {

	private Client client

	private final NetworkConfigurationData configuration

	private Optional<NetworkGameInfo> gameFound

	private Optional<ServerService> serverService

	int bytesSent

	new(NetworkConfigurationData configuration) {
		Validate.notNull(configuration)
		this.configuration = configuration
		client = new Client
		ObjectSpace.registerClasses(client.kryo)
		gameFound = Optional.absent
		serverService = Optional.absent
		LifecycleControl.getControl.addStopListener(this)
	}

	private def connectToFoundHost(InetAddress serverAddress) {
		assertNotNull(client)
		client.stop
		client = new Client(8192, 8192 * 4)
		ObjectSpace.registerClasses(client.kryo)
		client.start
		Thread.sleep(500)
		client.connect(configuration.connectTimeout, serverAddress, configuration.port, configuration.port)
	}

	override discoverHostAndGame() {
		assertNotNull(client)
		if(gameFound != null && gameFound.present) {
			throw new RuntimeException("Todo: multiple use of discoverHost ..")
		}
		val address = findHostAndService
		if(!address.present) {
			return Optional.absent
		}
		queryGames(address.get)
		gameFound
	}

	private def findHostAndService() {
		assertNotNull(client)
		val address = client.discoverHosts(configuration.port, configuration.searchTimeout)
		if(address == null || address.isEmpty) {
			return Optional.absent
		}
		try {
			connectToFoundHost(address.get(0))
		} catch(Exception e) {
			CommunicationConstants.LOG.error("Failed during connect:" + e.message, e)
			return Optional.absent
		}
		initializeDeserializer
		getRemoteServerService
		Optional.of(address.get(0))
	}

	private def void queryGames(InetAddress address) {
		returnif(!serverService.present)
		val games = serverService.get.pendingGames
		returnif(games == null || games.length == 0)
		val game = games.get(0)
		if(!game.success) {
			throw new NetworkErrorException("Server failed to deliver:" + game.errorMessage)
		}
		gameFound = Optional.of(new NetworkGameInfo(address, "N/A", game.gameInfo.currentPlayers, game.gameInfo.maxPlayers, game.id))
	}

	private def initializeDeserializer() {
		SerializerHelper.initializeKryo(client.kryo)
	}

	override getServerService() {
		serverService
	}

	private def getRemoteServerService() {
		val someObject = ObjectSpace.getRemoteObject(client, ServerService.SERVER_SERVICE_ID, ServerService);
		(someObject as RemoteObject).setNonBlocking(false)
		this.serverService = Optional.fromNullable(someObject)
	}

	override dispose() {
		super.dispose
		client.stop
	}

	override getFoundGame() {
		gameFound
	}

	override connectToLocalhost() {
		assertNotNull(client)
		val address = InetAddress.getLocalHost
		connectToFoundHost(address)
		initializeDeserializer
		getRemoteServerService
		Optional.of(address)
	}

	override connectTo(InetAddress address) {
		assertNotNull(client)

		connectToFoundHost(address)
		initializeDeserializer
		getRemoteServerService
		Optional.of(address)
	}

	override discoverHost() {
		if(serverService != null && serverService.present) {
			throw new RuntimeException("Todo: multiple use of discoverHost ..")
		}
		return findHostAndService.present
	}

	def registerGame(GameInfoResult info) {
		if(info == null || !info.success) {
			throw new IllegalArgumentException("Invalid game info!")
		}
		gameFound = Optional.of(new NetworkGameInfo(InetAddress.getLocalHost, "N/A", info.gameInfo.currentPlayers, info.gameInfo.maxPlayers, info.id))
	}

	override isDead() {
		!client.connected
	}

	override send(Object object) {
		val currentBytes = client.sendTCP(object)
		if(currentBytes == 0) {
			throw new BaseException(CommunicationConstants.NETWORK_SERVER_UNREACHABLE, "Fail to send object.")
		}
		bytesSent = bytesSent + currentBytes
	}

	override doLogin(String name) {
		Validate.isTrue(client.connected)
		send(new LoginCommand(name))
	}

	override clearFoundHost() {
		if(gameFound != null) {
			gameFound = null;
		}
		client.stop
	}

	override applicationStopped() {
		CommunicationConstants.LOG.info("Stopping client.")
		client.stop
	}

	@SuppressWarnings("rawtypes")
	override addHandler(ReceiveHandler handler) {
		Validate.notNull(handler)
		client.addListener(new KyroListenerAdapter(handler))
	}

	override ping() {
		if(!client.connected) {
			throw new NetworkErrorException("Not connected.")
		}
	}

}

