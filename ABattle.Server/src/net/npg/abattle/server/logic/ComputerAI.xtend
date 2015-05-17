package net.npg.abattle.server.logic

import java.util.Random
import net.npg.abattle.common.hex.Directions
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.server.model.ServerBoard
import net.npg.abattle.server.model.ServerCell
import net.npg.abattle.server.model.ServerGame
import net.npg.abattle.server.model.ServerPlayer

class ComputerAI implements Runnable {

	ServerGame game

	ServerBoard board

	static Random rand = new Random(12L)

	new(ServerGame game) {
		Validate.notNulls(game)
		this.game = game
		this.board = game.board as ServerBoard
	}

	override run() {
		for (player : game.players.filter[computer]) {
			val cell = board.getCellAt(randomCoordinate)
			if (cell.isOwner(player)) {
				makeLink(cell, player)
			}
		}
	}

	def makeLink(ServerCell cell, ServerPlayer player) {
		for (direction : Directions.cachedValues) {
			val endCell = board.getAdjacentCell(cell, direction)
			if (endCell != null && !board.links.hasLink(cell, endCell, player)) {
				board.links.toggleOutgoingLink(cell, endCell, player)
				return
			}
		}
	}

	private def randomCoordinate() {
		val x = rand.nextInt(board.XSize)
		val y = rand.nextInt(board.YSize)
		return IntPoint.from(x, y)
	}

}
