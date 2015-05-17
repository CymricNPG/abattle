package net.npg.abattle.client.commands

import com.google.common.base.Optional
import net.npg.abattle.client.ClientException
import net.npg.abattle.common.model.Board
import net.npg.abattle.common.model.CellTypes
import net.npg.abattle.common.model.client.ClientBoard
import net.npg.abattle.common.model.client.ClientCell
import net.npg.abattle.common.model.client.ClientGame
import net.npg.abattle.common.model.client.ClientLinks
import net.npg.abattle.common.model.client.ClientPlayer
import net.npg.abattle.common.model.client.impl.ClientCellImpl
import net.npg.abattle.common.utils.FieldLoop
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.CommandUpdateNotifier
import net.npg.abattle.communication.command.commands.InitBoardCommand
import net.npg.abattle.communication.command.data.CellData

class InitBoardCommandProcessor implements CommandProcessor<InitBoardCommand> {
	ClientGame game

	CommandUpdateNotifier notifier

	new(ClientGame game, CommandUpdateNotifier notifier) {
		Validate.notNulls(game, notifier)
		this.game = game
		this.notifier = notifier
	}

	override execute(InitBoardCommand command, int destination) {
		Validate.notNull(command)
		val cellUpdates = command.initBoard.cells
		val board = game.board as ClientBoard
		if (!isUpdateValid(board, cellUpdates)) {
			throw new ClientException("Server send wrong board update")
		}
		updateBoard(board, cellUpdates)
		notifier.receivedCommand(command)
		Optional.absent
	}

	private def updateBoard(Board<ClientPlayer, ClientCell, ClientLinks> board, CellData[][] cellUpdates) {
		FieldLoop.visitAllFields(board.XSize, board.YSize,
			[ x, y |
				createCell(board, x, y, cellUpdates.get(x).get(y))
			])
	}

	private def createCell(Board<ClientPlayer, ClientCell, ClientLinks> board, int x, int y, CellData update) {
		val newCell = new ClientCellImpl(update.id, IntPoint.from(x, y), update.height,
			CellTypes.values.get(update.cellType), game.gameConfiguration.checker)
		updateCell(newCell, update)
		board.setCellAt(newCell)
	}

	private def updateCell(ClientCell cell, CellData update) {
		cell.setVisible(false)
		cell.setHeight(update.height)
		val cellType = CellTypes.values.get(update.cellType)
		cell.setCellType(cellType)
	}

	private def isUpdateValid(Board<ClientPlayer, ClientCell, ClientLinks> board, CellData[][] cellUpdates) {
		if (board.XSize == cellUpdates.length) {
			return board.YSize == cellUpdates.get(0).length
		}
		return false;
	}
}
