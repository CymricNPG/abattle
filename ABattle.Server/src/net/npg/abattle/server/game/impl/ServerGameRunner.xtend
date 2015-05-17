package net.npg.abattle.server.game.impl

import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.model.GameStatus
import net.npg.abattle.common.utils.MyRunnable
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandQueueServer
import net.npg.abattle.communication.command.CommandType
import net.npg.abattle.communication.command.commands.GameFinishedCommand
import net.npg.abattle.server.ServerConstants
import net.npg.abattle.server.game.GameEnvironment
import net.npg.abattle.server.logic.ComputeWinSituation
import net.npg.abattle.server.logic.ComputerAI
import net.npg.abattle.server.logic.Logics
import net.npg.abattle.server.model.ServerBoard
import net.npg.abattle.server.model.ServerGame

import static net.npg.abattle.common.utils.Controls.*

class ServerGameRunner extends MyRunnable {

	private final GameEnvironment gameEnvironment

	private final ServerGame game

	private long lastTick

	private long lastLog

	private final long fps

	private final GameSender gameSender

	private final StatisticsSender statisticsSender

	private final CommandQueueServer commandQueue

	new(GameEnvironment gameEnvironment, CommandQueueServer commandQueue) {
		super("ServerGame")
		Validate.notNull(gameEnvironment)
		Validate.notNull(commandQueue)
		this.gameEnvironment = gameEnvironment
		this.game = gameEnvironment.serverGame
		this.lastTick = System.currentTimeMillis
		this.lastLog = 0L
		gameSender = new GameSender(gameEnvironment, commandQueue)
		statisticsSender = new StatisticsSender(gameEnvironment, commandQueue)
		this.commandQueue = commandQueue
		this.fps = ComponentLookup.getInstance().getComponent(ConfigurationComponent).gameLoopConfiguration.logicUpdatesPerSecond
	}

	override execute() {
		ServerConstants.LOG.info(game.id + ": Logic started.")

		//assert game != null
		while(GameStatus.FINISHED != game.status) {
			waitForTick
			doGameLogic
			lifeCycleWait
		}
		ServerConstants.LOG.info(game.id + ": Thread finished.")
	}

	def waitForTick() {
		val nextTick = lastTick + 1000L / fps
		val currentTick = System.currentTimeMillis
		if(currentTick > nextTick) {
			lastTick = currentTick
			logServerToSlow(currentTick - nextTick)
			return
		}
		Thread.sleep(nextTick - currentTick)
		lastTick = System.currentTimeMillis
	}

	def logServerToSlow(long diff) {
		val currentTick = System.currentTimeMillis
		if(lastLog + 1000L * 10 > currentTick) {
			return
		}
		lastLog = currentTick
		ServerConstants.LOG.error(game.id + ": Server to slow: " + diff + "ms")
	}

	def doGameLogic() {
		returnif(GameStatus.RUNNING != game.status)
		makeLogic
		sendUpdates
		sendFinishedGame
	}

	def sendFinishedGame() {
		returnif(GameStatus.FINISHED != game.status)
		val destinations = game.players.map[id].toList
		val data = new GameFinishedCommand(false, game.id)
		commandQueue.addCommand(data, CommandType.TOCLIENT, destinations)
	}

	def sendUpdates() {
		gameSender.sendGameData
		statisticsSender.sendGameData
	}

	def makeLogic() {

		val logic = Logics.logicMap.selectedClass.getInstance(game.board as ServerBoard, game.gameConfiguration)
		logic.run
		new ComputerAI(game).run
		ComputeWinSituation.run(game)

	//DumpBoard.dump(game).list(System.out)
	}
}
