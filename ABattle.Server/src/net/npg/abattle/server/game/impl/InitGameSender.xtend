package net.npg.abattle.server.game.impl

import net.npg.abattle.common.model.Board
import net.npg.abattle.common.model.Player
import net.npg.abattle.common.utils.FieldLoop
import net.npg.abattle.common.utils.SingleList
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandQueueServer
import net.npg.abattle.communication.command.CommandType
import net.npg.abattle.communication.command.commands.InitBoardCommand
import net.npg.abattle.communication.command.data.CellData
import net.npg.abattle.communication.command.data.CellDataBuilder
import net.npg.abattle.communication.command.data.InitBoardData
import net.npg.abattle.server.game.GameEnvironment
import net.npg.abattle.server.model.BoardInitializedNotifier
import net.npg.abattle.server.model.ServerCell
import net.npg.abattle.server.model.ServerLinks
import net.npg.abattle.server.model.ServerPlayer

import static net.npg.abattle.common.utils.MyArrayLiterals.*

class InitGameSender implements BoardInitializedNotifier {

	GameEnvironment game

	CommandQueueServer commandQueue

	new(GameEnvironment game, CommandQueueServer commandQueue) {
		Validate.notNulls(game, commandQueue)
		this.game = game
		this.commandQueue = commandQueue;
	}

	private def addCommandToQueue(InitBoardData boardUpdate, Player player) {
		val command = new InitBoardCommand(boardUpdate, false, game.serverGame.id)
		commandQueue.addCommand(command, CommandType.TOCLIENT, SingleList.create(player.id))
	}

	private def InitBoardData createBoardUpdate(Board<ServerPlayer, ServerCell, ServerLinks>  board, ServerPlayer player) {
		val cellUpdates = fillCells(board)
		new InitBoardData(cellUpdates);
	}


	private def fillCells(Board<ServerPlayer, ServerCell, ServerLinks>  board) {
		val CellData[][] cellUpdates = createArray(board.XSize, board.YSize)
		FieldLoop.visitAllFields(board.XSize, board.YSize,
			[ x, y |
				val originalCell = board.getCellAt(x, y)
				cellUpdates.get(x).set(y, buildCell(originalCell))
			])
		return cellUpdates
	}

	private def buildCell(ServerCell originalCell) {
		new CellDataBuilder().id(originalCell.id).height(originalCell.height).cellType(originalCell.cellType.ordinal).build
	}

	override boardCreated(Board<ServerPlayer, ServerCell, ServerLinks> board) {
		for (ServerPlayer player : game.serverGame.players.filter[!computer]) {
			val boardUpdate = createBoardUpdate(board, player)
			addCommandToQueue(boardUpdate, player)
		}
	}

}