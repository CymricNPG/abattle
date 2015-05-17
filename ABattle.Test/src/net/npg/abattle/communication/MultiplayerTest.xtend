package net.npg.abattle.communication

import java.util.Properties
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.NetworkConfigurationData
import net.npg.abattle.common.configuration.impl.ConfigurationComponentImpl
import net.npg.abattle.communication.command.CommandType
import net.npg.abattle.communication.command.impl.CommandQueueImpl
import net.npg.abattle.communication.network.impl.NetworkClientImpl
import net.npg.abattle.communication.network.impl.NetworkServerImpl
import net.npg.abattle.communication.service.common.MutableIntPoint
import net.npg.abattle.server.service.impl.ServerServiceImpl
import org.junit.After
import org.junit.Test

import static org.junit.Assert.*

/**
 * @Status: finished
 */
class MultiplayerTest {

	NetworkServerImpl server

	@Test
	def testGame() {
		baseInit

		val configuration = new NetworkConfigurationData(new Properties)
		server = new NetworkServerImpl(configuration)

		server.start

		initServerComponents

		val client1 = doPlayer1Init(configuration)

		doPlayer2Init(configuration)

		startGame(client1)

	}

	def startGame(NetworkClientImpl client) {
		val result = client.serverService.get.initSingleGame(client.foundGame.get.gameId)
		assertTrue(result.success)
	}

	def baseInit() {
		ComponentLookup.createInstance
		ComponentLookup.instance.registerComponent(new ConfigurationComponentImpl)

	}

	@After
	def cleanUp() {
		if (server != null) {
			server.dispose
		}
		ComponentLookup.shutdownInstance
	}

	def doPlayer2Init(NetworkConfigurationData configuration) {
		val client2 = new NetworkClientImpl(configuration)

		val gameInfo = client2.discoverHostAndGame
		assertTrue(gameInfo.present)
		val gameInfoDTO = gameInfo.get

		val remote2SS = client2.serverService.get
		val client2DTO = remote2SS.login("client2")
		assertTrue(client2DTO.success)
		val joinDTO = remote2SS.joinGame(gameInfoDTO.gameId, client2DTO.clientInfo.id)
		assertTrue(joinDTO.success)
	}

	def doPlayer1Init(NetworkConfigurationData configuration) {
		val client1 = new NetworkClientImpl(configuration)
		assertTrue(client1.discoverHost)
		val remote1SS = client1.serverService.get
		val client1DTO = remote1SS.login("client1")
		assertTrue(client1DTO.success)
		val game = remote1SS.createGame(client1DTO.clientInfo.id,2, MutableIntPoint.from(16, 16), 0)
		assertTrue(game.success)
//		val joinDTO = remote1SS.joinGame(game.id, client1DTO.clientInfo.id)
//		assertTrue(joinDTO.success)
		client1.registerGame(game)
		client1
	}

	def initServerComponents() {
		val cq = new CommandQueueImpl(CommandType.TOCLIENT,null)
		val serverService = new ServerServiceImpl(cq);
		server.registerService(serverService, ServerServiceImpl.SERVER_SERVICE_ID);
	}

}
