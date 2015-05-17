package net.npg.abattle.server.game.impl

import java.util.Collection
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.error.BaseException
import net.npg.abattle.common.model.GameConfiguration
import net.npg.abattle.common.model.GameStatus
import net.npg.abattle.common.model.impl.ColorImpl
import net.npg.abattle.common.model.impl.GameConfigurationImpl
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.common.utils.MyConcurrentHashMap
import net.npg.abattle.common.utils.MyMap
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandQueueServer
import net.npg.abattle.communication.command.CommandType
import net.npg.abattle.communication.command.commands.PlayerJoinedCommand
import net.npg.abattle.communication.command.data.PlayerData
import net.npg.abattle.server.ServerConstants
import net.npg.abattle.server.ServerExceptionCode
import net.npg.abattle.server.game.GameEnvironment
import net.npg.abattle.server.game.GamesManager
import net.npg.abattle.server.model.ServerGame
import net.npg.abattle.server.model.ServerPlayer
import net.npg.abattle.server.model.impl.ServerPlayerImpl
import net.npg.abattle.server.service.impl.StartGameThread

import static net.npg.abattle.common.utils.Asserts.*
import java.util.HashSet
import net.npg.abattle.communication.command.commands.PlayerLeftCommandBuilder

class GamesManagerImpl implements GamesManager {

	/** The max time (120s) until a pendimg game is purged */
	private static final long MAX_PENDING_TIME = 120 * 1000

	private final MyMap<Integer, ServerPlayer> players

	/** The server games (key=id,value=ServerGame). */
	private final MyMap<Integer, GameEnvironment> serverGames

	private final CommandQueueServer commandQueue

	new(CommandQueueServer commandQueue) {
		Validate.notNull(commandQueue)
		this.commandQueue = commandQueue
		serverGames = new MyConcurrentHashMap<Integer, GameEnvironment>()
		players = new MyConcurrentHashMap<Integer, ServerPlayer>()
	}

	def private boolean boardSizeExceedsLimits(IntPoint size) {
		return size.x < ServerConstants.MIN_XSIZE || size.x > ServerConstants.MAX_XSIZE || size.y < ServerConstants.MIN_YSIZE ||
			size.y > ServerConstants.MAX_YSIZE
	}

	/**
	 * Cleanup players which are connected to the service longer than 1h and not playing.
	 */
	def private void cleanupPlayers() {
		val expireDate = System.currentTimeMillis - 1000 * 60 * 60
		val removeList = players.values.filter[it.creationTime < expireDate].map[id]
		for (playerId : removeList) {
			players.remove(playerId)
		}
	}

	/**
	 * Cleanup server games which are too old.
	 */
	def private void cleanupServerGames() {
		for (GameEnvironment pendingGame : getPendingGames()) {
			if(System.currentTimeMillis() - pendingGame.getCreationTime() > MAX_PENDING_TIME) {
				serverGames.remove(pendingGame.getId())
				pendingGame.dispose
			}
		}
	}

	override public GameEnvironment createGame(int maxPlayers, IntPoint size) throws BaseException {
		assertIt(serverGames != null)
		Validate.notNull(size)
		Validate.notNulls(size.x, size.y)
		if(boardSizeExceedsLimits(size)) {
			throw new BaseException(ServerExceptionCode.INVALID_BOARD_SIZE)
		}
		if(maxPlayers < 2 || maxPlayers > ServerConstants.MAX_PLAYERS) {
			throw new BaseException(ServerExceptionCode.INCORRECT_NUMBER_OF_PLAYERS)
		}
		val configuration = buildGameConfiguration(size)
		val boardCreator = new BoardCreatorImpl(size, configuration.getChecker())
		val serverGame = new GameEnvironmentImpl(maxPlayers, boardCreator, configuration)
		serverGames.put(serverGame.getServerGame().getId(), serverGame)
		cleanupServerGames()
		val preGameThread = new PreGameThread(commandQueue, serverGame);
		serverGame.attachThread(preGameThread)
		new Thread(preGameThread).start
		return serverGame
	}

	def private GameConfiguration buildGameConfiguration(IntPoint size) {
		val configuration = ComponentLookup.getInstance().getComponent(ConfigurationComponent).getGameConfiguration()
		val newConfiguration = new GameConfigurationImpl()
		newConfiguration.configuration = configuration
		newConfiguration.XSize = size.x
		newConfiguration.YSize = size.y
		return newConfiguration
	}

	override public GameEnvironment getGame(int gameId) {
		return serverGames.get(gameId)
	}

	override public Collection<GameEnvironment> getPendingGames() {
		assertIt(serverGames != null)
		return serverGames.values().filter[GameStatus.PENDING == it.serverGame.status].toList
	}

	override public ServerPlayer getPlayer(int id) {
		return players.get(id)
	}

	override public boolean joinGame(GameEnvironment game, ServerPlayer player) {
		if(game.serverGame.players.contains(player)) {
			ServerConstants.LOG.error("Player already joined:" + player.id)
			return false
		}
		try {
			player.color = getFreeColor(player, game.serverGame)
			synchronized(game) {
				game.addNewPlayer(player)
			}
			notifyGamers(game)
		} catch(BaseException e) {
			ServerConstants.LOG.error(e.getMessage(), e)
			return false
		}
		startGameIfPossible(game)
		return true
	}

	private def startGameIfPossible(GameEnvironment game) {
		val serverGame = game.serverGame
		if(serverGame.players.size == serverGame.maxPlayers) {
			new Thread(new StartGameThread(game, commandQueue)).start
		}
	}

	def private void notifyGamers(GameEnvironment game) {
		val PlayerData[] playersData = DataHelper.createPlayerData(game)
		val PlayerJoinedCommand command = new PlayerJoinedCommand(playersData, true, game.getId())
		val destinations = game.serverGame.players.map[id].toList
		commandQueue.addCommand(command, CommandType.TOCLIENT, destinations)
	}

	override public void removeGame(GameEnvironment game) {
		serverGames.remove(game.id)
		game.dispose
	}

	override public boolean leaveGame(GameEnvironment game, ServerPlayer player) {
		try {
			var boolean success;
			synchronized(game) {
				if(!game.serverGame.players.contains(player)) {
					ServerConstants.LOG.error("Player already left:" + player.id + " game:" + game.id)
					return false
				}
				success = game.removePlayer(player)
			}
			if(success) {
				notifyGamers(game)
			} else {
				val command = new PlayerLeftCommandBuilder().dropable(false).game(game.getId()).player(DataHelper.createPlayerData(player)).build
				commandQueue.addCommand(command, CommandType.TOCLIENT, game.serverGame.players.map[id].toList)
				if(game.serverGame.players.size == 0) {
					removeGame(game)
				}
			}
		} catch(BaseException e) {
			ServerConstants.LOG.error(e.getMessage(), e)
			return false
		}
		return true

	}

	override

public ServerPlayer login(String name) {
		val serverPlayer = new ServerPlayerImpl(name, ColorImpl.RED, false)
		players.put(serverPlayer.getId(), serverPlayer)
		cleanupPlayers()
		return serverPlayer
	}

	override

public void initSingleGame(GameEnvironment gameEnvironment) throws BaseException {
		Validate.notNull(gameEnvironment)

		val ServerGame game = gameEnvironment.getServerGame()
		game.startGame(new InitGameSender(gameEnvironment, commandQueue))
	}

	override removePlayer(ServerPlayer player) {
		players.remove(player.id)
		serverGames.values.filter[it.serverGame.players.contains(player)].forEach[leaveGame(it, player)]
	}

	synchronized override getFreeColor(ServerPlayer player, ServerGame game) {
		val colors = new HashSet(PlayerColors.colors)
		for (existingPlayer : game.players) {
			colors.remove(existingPlayer.color)
		}
		colors.iterator.next
	}

}
