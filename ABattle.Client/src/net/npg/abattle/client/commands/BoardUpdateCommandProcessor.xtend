package net.npg.abattle.client.commands

import com.google.common.base.Optional
import java.util.HashSet
import net.npg.abattle.client.ClientException
import net.npg.abattle.common.model.Board
import net.npg.abattle.common.model.client.ClientBoard
import net.npg.abattle.common.model.client.ClientCell
import net.npg.abattle.common.model.client.ClientGame
import net.npg.abattle.common.model.client.ClientLinks
import net.npg.abattle.common.model.client.ClientPlayer
import net.npg.abattle.common.model.impl.LinkImpl
import net.npg.abattle.common.utils.FieldLoop
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.CommandUpdateNotifier
import net.npg.abattle.communication.command.commands.BoardUpdateCommand
import net.npg.abattle.communication.command.data.CellUpdateData
import net.npg.abattle.communication.command.data.LinkUpdateData
import net.npg.abattle.client.ClientConstants

class BoardUpdateCommandProcessor implements CommandProcessor<BoardUpdateCommand> {

	ClientGame game

	CommandUpdateNotifier notifier

	new(ClientGame game, CommandUpdateNotifier notifier) {
		Validate.notNulls(game, notifier)
		this.game = game
		this.notifier = notifier
	}

	override execute(BoardUpdateCommand command, int destination) {
		Validate.notNull(command)
		if(command.game != game.id) {
			ClientConstants.LOG.error("Received an update for a different game:"+command.game+" My game is:"+game.id);
			return Optional.absent;
		}
		val cellUpdates = command.boardUpdate.cellUpdates
		val board = game.board as ClientBoard
		if (!isUpdateValid(board, cellUpdates)) {
			throw new ClientException("Server send wrong board update")
		}
		updateBoard(board, cellUpdates)
		updateLinks(board, command.boardUpdate.linkUpdates)
		notifier.receivedCommand(command)
		Optional.absent
	}

	private def updateLinks(ClientBoard board, LinkUpdateData[] links) {
		val newLinks = new HashSet<Integer>(links.map[id])
		val linksComponent = board.links
		linksComponent.preserverLinks(newLinks)
		links.filter[!linksComponent.hasLink(it.id)].forEach[addLink(board, it)]
	}

	private def addLink(ClientBoard board, LinkUpdateData link) {
		val startCell = board.getCell(link.startCellId)
		val endCell = board.getCell(link.endCellId)
		val clientLink = new LinkImpl(link.id, startCell, endCell)
		board.links.addLink(clientLink)
	}

	private def updateBoard(Board<ClientPlayer, ClientCell, ClientLinks> board, CellUpdateData[][] cellUpdates) {
		FieldLoop.visitAllFields(board.XSize, board.YSize,
			[ x, y |
					updateCell(board.getCellAt(x, y), cellUpdates.get(x).get(y))
			])

	}


	private def updateCell(ClientCell cell, CellUpdateData update) {
		if (update == null) {
			updateVisible(cell, false)
		} else {
			checkId(cell, update.id)
			updateStrength(cell, update.strength)
			updateBattle(cell, update.battle)
			updateOwner(cell, update.owner)
			updateVisible(cell, true)
		}
	}



	private def checkId(ClientCell cell, int id) {
		if (cell.id != id) {
			throw new IllegalArgumentException("Ids dont match:" + cell.id + "!=" + id)
		}
	}

	private def updateVisible(ClientCell cell, boolean visible) {
		if (cell.visible == visible) {
			return;
		}
		cell.setVisible(visible)
	}

	private def updateOwner(ClientCell cell, int newOwnerId) {
		if(cell.owner == null) {
			cell.owner = null
		}
		if (newOwnerId == 0) {
			if (!cell.owner.present) {
				return
			}
			cell.setOwner(null)
		} else {
			if (!cell.owner.present) {
				cell.setOwner(getPlayer(newOwnerId))
			} else if (cell.owner.get.id == newOwnerId) {
				return
			}
			cell.setOwner(getPlayer(newOwnerId))
		}
	}

	private def getPlayer(int playerId) {
		val player = game.players.findFirst[id == playerId]
		if (player == null) {
			throw new ClientException("Server send wrong player:" + playerId)
		}
		return player
	}

	private def updateBattle(ClientCell cell, boolean newBattle) {
		if (cell.hasBattle == newBattle) {
			return
		}
		cell.setBattle(newBattle)
	}

	private def updateStrength(ClientCell cell, int newStrength) {
		if (cell.strength == newStrength) {
			return
		}
		cell.setStrength(newStrength)
	}

	private def isUpdateValid(Board<ClientPlayer, ClientCell, ClientLinks> board, CellUpdateData[][] cellUpdates) {
		if (board.XSize == cellUpdates.length) {
			return board.YSize == cellUpdates.get(0).length
		}
		return false;
	}

}
