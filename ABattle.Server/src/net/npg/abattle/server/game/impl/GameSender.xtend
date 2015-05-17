package net.npg.abattle.server.game.impl

import net.npg.abattle.common.model.Board
import net.npg.abattle.common.model.Player
import net.npg.abattle.common.model.client.ClientCell
import net.npg.abattle.common.model.client.ClientLinks
import net.npg.abattle.common.model.client.ClientPlayer
import net.npg.abattle.common.utils.SingleList
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandQueueServer
import net.npg.abattle.communication.command.CommandType
import net.npg.abattle.communication.command.commands.BoardUpdateCommand
import net.npg.abattle.communication.command.data.BoardUpdateData
import net.npg.abattle.communication.command.data.CellUpdateData
import net.npg.abattle.communication.command.data.LinkUpdateData
import net.npg.abattle.server.game.GameEnvironment
import net.npg.abattle.server.model.ServerPlayer

import static net.npg.abattle.common.utils.MyArrayLiterals.*

import static extension net.npg.abattle.communication.command.data.LinkUpdateHelper.*
import net.npg.abattle.communication.command.data.CellUpdateDataBuilder
import net.npg.abattle.common.utils.FieldLoop

class GameSender {

	GameEnvironment game

	CommandQueueServer commandQueue

	new(GameEnvironment game, CommandQueueServer commandQueue) {
		Validate.notNull(game)
		Validate.notNull(commandQueue)

		this.game = game
		this.commandQueue = commandQueue;
	}

	def sendGameData() {
		for (ServerPlayer player : game.serverGame.players.filter[!computer]) {
			val clientGame = game.getClientGame(player)
			val boardUpdate = createBoardUpdate(clientGame.board, player)
			addCommandToQueue(boardUpdate, player)
		}
	}

	private def addCommandToQueue(BoardUpdateData boardUpdate, Player player) {
		val command = new BoardUpdateCommand(boardUpdate, false, game.serverGame.id)
		commandQueue.addCommand(command, CommandType.TOCLIENT, SingleList.create(player.id))
	}

	private def BoardUpdateData createBoardUpdate(Board<ClientPlayer, ClientCell, ClientLinks> board, ServerPlayer player) {
		val cellUpdates = fillCells(board)
		val linkUpdates = fillLinks(board)
		new BoardUpdateData(cellUpdates, linkUpdates);
	}

	private def fillLinks(Board<ClientPlayer, ClientCell, ClientLinks> board) {
		val links = board.links.links
		return links.map[new LinkUpdateData(it.id, it.sourceCell.id, it.destinationCell.id)].toArray
	}

	private def fillCells(Board<ClientPlayer, ClientCell, ClientLinks> board) {
		val CellUpdateData[][] cellUpdates = createArray(board.XSize, board.YSize)
		FieldLoop.visitAllFields(board.XSize, board.YSize,
			[ x, y |
				val originalCell = board.getCellAt(x, y)
				val cell = if(originalCell.visible)
						buildCell(originalCell)
					else
						null
				cellUpdates.get(x).set(y, cell)
			])
		return cellUpdates
	}

	private def buildCell(ClientCell originalCell) {
		val ownerId = if(!originalCell.owner.isPresent) 0 as int else originalCell.owner.get.id
		new CellUpdateDataBuilder().id(originalCell.id).owner(ownerId).battle(originalCell.hasBattle).strength(originalCell.strength).build
	}

}
