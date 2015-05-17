package net.npg.abattle.client.lan.impl

import com.google.common.base.Optional
import java.util.Collections
import net.npg.abattle.client.lan.ClientGameEnvironment
import net.npg.abattle.client.startup.Startup
import net.npg.abattle.common.configuration.GameConfigurationData
import net.npg.abattle.common.error.ErrorMessage
import net.npg.abattle.common.model.client.ClientIdRegister
import net.npg.abattle.common.model.client.ClientPlayer
import net.npg.abattle.common.model.client.impl.ClientGameImpl
import net.npg.abattle.common.model.client.impl.ClientModelComponentImpl
import net.npg.abattle.common.model.impl.GameConfigurationImpl
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.common.utils.impl.DisposeableImpl
import net.npg.abattle.communication.CommunicationConstants
import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.CommandQueueClient
import net.npg.abattle.communication.command.CommandType
import net.npg.abattle.communication.command.DeadCommand
import net.npg.abattle.communication.command.GameCommand
import net.npg.abattle.communication.command.commands.GameCountdownCommand
import net.npg.abattle.communication.command.commands.InitBoardCommand
import net.npg.abattle.communication.command.commands.LeaveCommandBuilder
import net.npg.abattle.communication.command.impl.CommandQueueImpl
import net.npg.abattle.communication.network.NetworkClient
import net.npg.abattle.communication.network.NetworkErrorException
import net.npg.abattle.communication.network.impl.GameCommandHandler
import net.npg.abattle.communication.network.impl.LoginResultHandler
import net.npg.abattle.communication.network.impl.NetworkClientProcessor
import net.npg.abattle.communication.service.common.GameInfoResult
import net.npg.abattle.communication.service.common.MutableIntPoint

import static net.npg.abattle.common.utils.Asserts.*
import static net.npg.abattle.common.utils.Controls.*

class ClientGameEnvironmentImpl extends DisposeableImpl implements ClientGameEnvironment, CommandProcessor<DeadCommand> {

	CommandQueueClient commandQueue

	ClientGameImpl game

	NetworkClient client

	ClientPlayer player

	ClientIdRegister clientModel

	volatile int gameStarted

	Optional<Integer> remainingCount = Optional.absent

	new(NetworkClient _client) {
		Validate.notNull(_client)
		client = _client
		clientModel = new ClientModelComponentImpl
		commandQueue = new CommandQueueImpl(CommandType.TOCLIENT, this)
		client.addHandler(new GameCommandHandler(commandQueue))
		commandQueue.registerCommandProcessor(new NetworkClientProcessor(client), Optional.absent, CommandType.TOSERVER)
		gameStarted = 0
	}

	override createGame(IntPoint size, int playerCount) {
		assertIt(!disposed)
		assertIt(game == null)
		Validate.notNull(player)
		commandQueue.pause
		val gameDTO = client.serverService.get.createGame(player.id, playerCount, MutableIntPoint.from(size), 0)
		if(!gameDTO.success) {
			return new ErrorMessage(CommunicationConstants.GAME_CREATION_FAILED, gameDTO.errorMessage)
		}
		buildGame(gameDTO)
		Startup.l30(this)
		commandQueue.resume
		return new ErrorMessage(false)
	}

	private def buildGame(GameInfoResult gameDTO) {
		Validate.notNull(player)
		val configuration = new GameConfigurationImpl()
		configuration.configuration = new GameConfigurationData()
		configuration.configuration.baseGrowthPerTick = 0
		configuration.configuration.maxCellHeight = gameDTO.gameInfo.parameters.maxCellHeight
		configuration.configuration.maxCellStrength = gameDTO.gameInfo.parameters.maxCellStrength
		configuration.configuration.maxMovement = gameDTO.gameInfo.parameters.maxMovement
		val creator = new EmptyBoardCreator(gameDTO.gameInfo.parameters.size.to, configuration.checker)
		this.game = new ClientGameImpl(gameDTO.id, creator, configuration)
		game.addPlayer(player)
		game.localPlayer = player
		game.initGame
	}

	override joinGame(int gameId) {
		assertIt(!disposed)
		val gameDTO = client.serverService.get.joinGame(gameId, player.id)
		if(!gameDTO.success) {
			return new ErrorMessage(CommunicationConstants.GAME_JOINED_FAILED, gameDTO.errorMessage)
		}
		buildGame(gameDTO)
		Startup.l30(this)
		return new ErrorMessage(false)
	}

	override getCommandQueue() {
		assertIt(!disposed)
		return commandQueue
	}

	override login(String name) {
		assertIt(client != null)
		assertIt(!disposed)
		clientModel.name = name
		client.addHandler(new LoginResultHandler(clientModel))
		client.doLogin(name)
		player = waitForClientId
		player.id
	}

	private def waitForClientId() {
		assertIt(clientModel != null)
		assertIt(!disposed)
		clientModel.waitForClientId
		val player = clientModel.localPlayer
		assertNotNull(player)
		clientModel.resetClientPlayer
		return player
	}

	override getGame() {
		assertIt(game != null)
		assertIt(!disposed)
		return game
	}

	override leave() {
		assertIt(!disposed)
		commandQueue.addCommand(new LeaveCommandBuilder().game(game.id).originatedPlayer(player.id).build, CommandType.TOSERVER,
			Collections.<Integer>emptyList())
		commandQueue.flush
		Thread.sleep(100)
	}

	override checkGameStart() {
		assertIt(!disposed)
		gameStarted > 0
	}

	override getRemainingCount() {
		assertIt(!disposed)
		return this.remainingCount
	}

	override receivedCommand(GameCommand command) {
		assertIt(!disposed)
		if(command instanceof InitBoardCommand) {
			gameStarted++
		} else if(command instanceof GameCountdownCommand) {
			this.remainingCount = Optional.of(Integer.valueOf((command as GameCountdownCommand).remainingCount))
		}
	}

	override dispose() {
		super.dispose
		if(!client.disposed) {
			client.dispose
		}
	}

	override execute(DeadCommand command, int destination) {
		//666 TODO 666 xxx
	}

	override ping() {
		returnif(game == null)
		client.ping
		val retValue = client.serverService.get.ping(game.id)
		if(!retValue.success) {
			throw new NetworkErrorException(retValue.errorMessage)
		}
	}
}
