package net.npg.abattle.client.view.boardscene

import net.npg.abattle.common.hex.Hex
import net.npg.abattle.common.hex.HexBase
import net.npg.abattle.common.model.client.ClientGame
import net.npg.abattle.common.utils.FloatPoint
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.common.utils.LongHolder

class ComplexBarShape extends Shape {

	ClientGame game

	Hex startHex

	Hex endHex

	new(ClientGame game, HexBase hexBase) {
		super()
		this.game = game
		this.startHex = new Hex(IntPoint.from(1, game.board.YSize - 1), hexBase);
		this.endHex = new Hex( IntPoint.from(game.board.XSize - 2,  game.board.YSize - 1), hexBase);
	}

	override getHex() {
		return startHex
	}

	def FloatPoint getStartCoordinate() {
		return new FloatPoint(startHex.centerX, startHex.bottom+0.3)
	}

	def FloatPoint getEndCoordinate() {
		return new FloatPoint(endHex.centerX, startHex.bottom+0.2)
	}

	override isDrawable() {
		true
	}

	override accept(SceneRenderer visitor) {
		visitor.visit(this)
	}

	def getSizes() {
		val maxStrength = new LongHolder
		game.players.forEach[maxStrength.add(strength)]
		if(maxStrength.value == 0) {
			maxStrength.value = 1L
		}
		game.players.map[strength as float / maxStrength.toFloat].toList
	}

	def getColors() {
		game.players.map[color]
	}

}
