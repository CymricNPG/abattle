package net.npg.abattle.server.logic

import net.npg.abattle.server.model.ServerBoard
import net.npg.abattle.server.model.ServerGame

class SimpleGameLogic {
	def static run(ServerGame game) {
		val board = game.board as ServerBoard

		new SimpleDistributeArmies().getInstance(board, game.gameConfiguration).run

		new ComputerAI(game).run
		ComputeWinSituation.run(game)
	}

}
