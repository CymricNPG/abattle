package net.npg.abattle.server.logic

import net.npg.abattle.common.utils.FieldLoop
import net.npg.abattle.server.model.ServerGame
import net.npg.abattle.server.model.ServerPlayer

import static net.npg.abattle.common.utils.Controls.*
import net.npg.abattle.server.model.ServerCell
import net.npg.abattle.common.utils.LongHolder

class ComputeWinSituation {

	def static run(ServerGame game) {
		cleanPlayerStrength(game)
		val totalStrength = new LongHolder
		FieldLoop.visitAllFields(game.board.XSize, game.board.YSize, [x, y|calcStrength(game.board.getCellAt(x,y), totalStrength)])

		val winStrength = (totalStrength.value as float * game.gameConfiguration.configuration.winCondition /100.0) as int
		val winPlayer = game.players.findFirst[strength  >= winStrength]
		returnif(winPlayer == null)
		game.stopGame
	}

	def static calcStrength(ServerCell cell, LongHolder totalStrength) {
		returnifNot(cell.owner.present)
		(cell.owner.get as ServerPlayer).strength = cell.owner.get.strength + cell.strength
		totalStrength.add(cell.strength)
	}

	private static def cleanPlayerStrength(ServerGame game) {
		for (ServerPlayer player : game.players) {
			player.strength = 0
		}
	}

}
