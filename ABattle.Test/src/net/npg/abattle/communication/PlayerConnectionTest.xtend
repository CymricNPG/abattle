package net.npg.abattle.communication

import com.google.common.base.Optional
import net.npg.abattle.client.lan.ClientGameEnvironment
import net.npg.abattle.client.lan.impl.ClientGameEnvironmentImpl
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.impl.ConfigurationComponentImpl
import net.npg.abattle.common.model.client.impl.ClientModelComponentImpl
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.communication.command.CommandQueueServer
import net.npg.abattle.communication.command.CommandType
import net.npg.abattle.communication.command.commands.ChangeLinkCommand
import net.npg.abattle.communication.command.impl.CommandQueueImpl
import net.npg.abattle.communication.network.NetworkComponent
import net.npg.abattle.communication.network.NetworkServer
import net.npg.abattle.communication.network.impl.GameCommandHandler
import net.npg.abattle.communication.network.impl.LoginHandler
import net.npg.abattle.communication.network.impl.LoginResultHandler
import net.npg.abattle.communication.network.impl.NetworkComponentImpl
import net.npg.abattle.communication.network.impl.NetworkServerProcessor
import net.npg.abattle.communication.service.ServerService
import net.npg.abattle.server.command.ChangeLinkCommandProcessor
import net.npg.abattle.server.service.impl.ServerServiceImpl
import org.junit.After
import org.junit.Test

import static org.junit.Assert.*

/**
 *
 */
class PlayerConnectionTest {

	@Test
	def testPlayerConnection() {
		baseInit
		val environment1 = doPlayer1
		val environment2 = doPlayer2
		Thread.sleep(1000)
		processCommandQueue(environment1)
		processCommandQueue(environment2)

		checkEnvironment(environment1)
		checkEnvironment(environment2)
	}

	private def processCommandQueue(ClientGameEnvironment env) {
		//	env.commandQueue.processCommands
	}

	private def checkEnvironment(ClientGameEnvironment environment) {
		val game2 = environment.game
		assertEquals(2, game2.players.size)
	}

	private def baseInit() {
		ComponentLookup.createInstance
		ComponentLookup.instance.registerComponent(new ConfigurationComponentImpl)

		val cqs = new CommandQueueImpl(CommandType.TOSERVER,null)

		val networkComponent = ComponentLookup.instance.registerComponent(new NetworkComponentImpl, NetworkComponent)

		val clientIdRegister = new ClientModelComponentImpl

		val serverService = new ServerServiceImpl(cqs)
		val server = networkComponent.createServer

		server.addHandler(new LoginHandler(serverService))

		server.addHandler(new LoginResultHandler(clientIdRegister))
		server.addHandler(new GameCommandHandler(cqs))

		//	server.start
		server.registerService(serverService, ServerServiceImpl.SERVER_SERVICE_ID);
		registerServerProcessors(server, serverService, cqs)
	}

	private def registerServerProcessors(NetworkServer server, ServerService serverService,
		CommandQueueServer serverQueue) {
		serverQueue.registerCommandProcessor(new ChangeLinkCommandProcessor(serverService),
			Optional.of(ChangeLinkCommand), CommandType.TOSERVER)
		serverQueue.registerCommandProcessor(new NetworkServerProcessor(server), Optional.absent, CommandType.TOCLIENT)
	}

	private def doPlayer1() {
		val client1 = ComponentLookup.instance.getComponent(NetworkComponent).createClient

		if (!client1.discoverHost) {
			throw new RuntimeException("cannot find local host!")
		}
		val environment = new ClientGameEnvironmentImpl(client1)

		environment.login("Test")
		val error = environment.createGame(IntPoint.from(10, 10), 2)
		assertFalse(error.failed)
		environment
	}

	private def doPlayer2() {
		val client2 = ComponentLookup.instance.getComponent(NetworkComponent).createClient

		val networkGame = client2.discoverHostAndGame
		if (!networkGame.present) {
			throw new RuntimeException("cannot find local host!")
		}
		val environment = new ClientGameEnvironmentImpl(client2)
		assertTrue(client2.foundGame.present)
		environment.login("Test2")
		val result = environment.joinGame(client2.foundGame.get.gameId)
		assertFalse(result.failed)
		environment

	// todo server must now send player joined!!!
	// todo command queue leeren!
	}

	@After
	def cleanUp() {
		interruptAllThreads
	}

	private def interruptAllThreads() {
		for (thread : Thread.getAllStackTraces().keySet()) {
			if (!"main".equals(thread.name) && !"finalizer".equals(thread.name)) {
				thread.interrupt
			}
		}
	}

}
