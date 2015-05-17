package net.npg.abattle.client.startup

import com.google.common.base.Optional
import java.net.InetAddress
import net.npg.abattle.client.asset.impl.AssetManagerFactory
import net.npg.abattle.client.commands.BoardUpdateCommandProcessor
import net.npg.abattle.client.commands.GameCountdownProcessor
import net.npg.abattle.client.commands.GameFinishedProcessor
import net.npg.abattle.client.commands.GameUpdateProcessor
import net.npg.abattle.client.commands.InitBoardCommandProcessor
import net.npg.abattle.client.commands.PingProcessor
import net.npg.abattle.client.commands.PlayerJoinedProcessor
import net.npg.abattle.client.commands.PlayerLeftProcessor
import net.npg.abattle.client.commands.ServerReplyProcessor
import net.npg.abattle.client.lan.ClientGameEnvironment
import net.npg.abattle.client.lan.impl.ClientGameEnvironmentImpl
import net.npg.abattle.client.view.boardscene.BoardSceneComponentImpl
import net.npg.abattle.client.view.screens.Icons
import net.npg.abattle.client.view.selection.impl.SelectionComponentImpl
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.component.ExternalRegisterComponent
import net.npg.abattle.common.configuration.impl.ConfigurationComponentImpl
import net.npg.abattle.common.error.ErrorHandler
import net.npg.abattle.common.error.impl.ErrorComponentImpl
import net.npg.abattle.common.i18n.impl.I18NComponentImpl
import net.npg.abattle.communication.command.CommandQueueServer
import net.npg.abattle.communication.command.CommandType
import net.npg.abattle.communication.command.commands.BoardUpdateCommand
import net.npg.abattle.communication.command.commands.ChangeLinkCommand
import net.npg.abattle.communication.command.commands.GameCountdownCommand
import net.npg.abattle.communication.command.commands.GameFinishedCommand
import net.npg.abattle.communication.command.commands.GameUpdateCommand
import net.npg.abattle.communication.command.commands.InitBoardCommand
import net.npg.abattle.communication.command.commands.LeaveCommand
import net.npg.abattle.communication.command.commands.PingCommand
import net.npg.abattle.communication.command.commands.PlayerJoinedCommand
import net.npg.abattle.communication.command.commands.PlayerLeftCommand
import net.npg.abattle.communication.command.commands.ServerReplyCommand
import net.npg.abattle.communication.command.impl.CommandQueueImpl
import net.npg.abattle.communication.network.NetworkComponent
import net.npg.abattle.communication.network.impl.GameCommandHandler
import net.npg.abattle.communication.network.impl.LoginHandler
import net.npg.abattle.communication.network.impl.NetworkComponentImpl
import net.npg.abattle.communication.network.impl.NetworkServerProcessor
import net.npg.abattle.server.command.ChangeLinkCommandProcessor
import net.npg.abattle.server.command.LeaveCommandProcessor
import net.npg.abattle.server.service.impl.ServerServiceImpl

import static net.npg.abattle.common.utils.Controls.*

class Startup {

	/**
	 * components before any gui is started
	 */
	def static l0(ErrorHandler errorHandler,ExternalRegisterComponent externalRegisterComponent) {
		if(!ComponentLookup.initialized) {
			val lookup = ComponentLookup.createInstance
			val configuration = new ConfigurationComponentImpl()
			lookup.registerComponent(configuration)
		}
		val lookup = ComponentLookup.instance;
		lookup.registerComponent(new I18NComponentImpl)
		lookup.registerComponent(new NetworkComponentImpl)
		startAssetManager
		lookup.registerComponent(new BoardSceneComponentImpl)
		val errorComponent = lookup.registerComponent(new ErrorComponentImpl)
		errorComponent.registerErrorHandler(errorHandler)
		externalRegisterComponent.registerComponents(lookup)
		lookup.registerComponent(new SelectionComponentImpl())
	}

	private static def startAssetManager() {
		val assets = AssetManagerFactory.create
		assets.loadIcons(Icons.values.map[it.filename])
		assets.loadFonts
	}

	def static restart0() {
		val lookup = ComponentLookup.instance
		lookup.restart
		lookup.registerComponent(new NetworkComponentImpl, NetworkComponent)
		startAssetManager
	}

	/*
	 * Start a network server (if not runnin)
	 */
	def static l10() {
		val networkComponent = ComponentLookup.instance.getComponent(NetworkComponent)
		returnif(networkComponent.serverRunning)
		val server = networkComponent.createServer

		val serverQueue = new CommandQueueImpl(CommandType.TOSERVER, null)

		val serverService = new ServerServiceImpl(serverQueue)
		server.addHandler(new LoginHandler(serverService))
		server.addHandler(new GameCommandHandler(serverQueue))

		Thread.sleep(200)
		server.registerService(serverService, ServerServiceImpl.SERVER_SERVICE_ID);

		registerServerProcessors(serverQueue, serverService)
		serverQueue.registerCommandProcessor(new NetworkServerProcessor(server), Optional.absent, CommandType.TOCLIENT)
	}

	/**
	 * create client and login to localhost
	 */
	def static l15local(String name) {
		val client = ComponentLookup.instance.getComponent(NetworkComponent).createClient

		client.connectToLocalhost
		val environment = new ClientGameEnvironmentImpl(client)
		environment.login(name)
		environment
	}

	/**
	 * create client and login to remote host
	 */
	def static l15remote(String name, InetAddress address) {
		val client = ComponentLookup.instance.getComponent(NetworkComponent).createClient

		client.connectTo(address)
		val environment = new ClientGameEnvironmentImpl(client)
		environment.login(name)
		environment
	}

	/**
	 * start local server service and register command processors
	 * @param serverCommandType to which commandtype the server should listen
	 */
	def static l20local(CommandQueueServer serverQueue) {
		val serverService = new ServerServiceImpl(serverQueue)
		registerServerProcessors(serverQueue, serverService)
		serverService
	}

	private def static registerServerProcessors(CommandQueueServer serverQueue, ServerServiceImpl serverService) {
		serverQueue.registerCommandProcessor(new ChangeLinkCommandProcessor(serverService), Optional.of(ChangeLinkCommand), CommandType.TOSERVER)
		serverQueue.registerCommandProcessor(new LeaveCommandProcessor(serverService), Optional.of(LeaveCommand), CommandType.TOSERVER)
	}

	/**
	 * start client command processors
	 * @param serverCommandType to which commandtype the client should listen
	 */
	def static l30(ClientGameEnvironment gameEnvironment) {
		val commandQueue = gameEnvironment.commandQueue
		val game = gameEnvironment.game
		commandQueue.registerCommandProcessor(new BoardUpdateCommandProcessor(game, gameEnvironment), Optional.of(BoardUpdateCommand), CommandType.TOCLIENT)
		commandQueue.registerCommandProcessor(new InitBoardCommandProcessor(game, gameEnvironment), Optional.of(InitBoardCommand), CommandType.TOCLIENT)
		commandQueue.registerCommandProcessor(new ServerReplyProcessor, Optional.of(ServerReplyCommand), CommandType.TOCLIENT)
		commandQueue.registerCommandProcessor(new GameUpdateProcessor(game), Optional.of(GameUpdateCommand), CommandType.TOCLIENT)
		commandQueue.registerCommandProcessor(new PlayerJoinedProcessor(game), Optional.of(PlayerJoinedCommand), CommandType.TOCLIENT)
		commandQueue.registerCommandProcessor(new PlayerLeftProcessor(game), Optional.of(PlayerLeftCommand), CommandType.TOCLIENT)
		commandQueue.registerCommandProcessor(new GameFinishedProcessor(game), Optional.of(GameFinishedCommand), CommandType.TOCLIENT)
		commandQueue.registerCommandProcessor(new GameCountdownProcessor(game, gameEnvironment), Optional.of(GameCountdownCommand), CommandType.TOCLIENT)
		commandQueue.registerCommandProcessor(new PingProcessor(game, gameEnvironment), Optional.of(PingCommand), CommandType.TOCLIENT)

	}
}
