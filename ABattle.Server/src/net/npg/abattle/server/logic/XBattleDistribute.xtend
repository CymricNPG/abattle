package net.npg.abattle.server.logic

import java.util.Collections
import java.util.List
import java.util.Random
import net.npg.abattle.common.hex.Directions
import net.npg.abattle.common.model.GameConfiguration
import net.npg.abattle.common.utils.FieldLoop
import net.npg.abattle.common.utils.IntHolder
import net.npg.abattle.server.model.ServerBoard
import net.npg.abattle.server.model.ServerCell
import net.npg.abattle.server.model.ServerPlayer

import static net.npg.abattle.common.utils.Controls.*
import net.npg.abattle.common.configuration.GameConfigurationData

class XBattleDistribute implements Logic {

	private final ServerBoard board

	private final Random rand

	private final GameConfigurationData configuration

	new() {
		configuration = null
		board = null
		rand = null
	}

	new(ServerBoard board, GameConfiguration configuration) {
		this.board = board
		this.configuration = configuration.configuration
		rand = new Random(System.currentTimeMillis)
	}

	override run() {
		FieldLoop.visitAllFields(board.XSize, board.YSize, [x, y|board.getCellAt(x, y).battle = false])
		val cells = shuffleCells()
		resetFight(cells)
		for (cell : cells) {
			LogicHelper.updateCellGrowth(cell, configuration)
			updateCellFights(cell)
			updateCellMovement(cell)
		}
	}

	def resetFight(List<ServerCell> cells) {
		cells.forEach[it.battle = false]
	}

	private def updateCellMovement(ServerCell cell) {
		returnif(cell.strength == 0)
		returnif(!cell.owner.present)
		val destinationCells = findFriendlyCells(cell)
		returnif(destinationCells.empty)
		destinationCells.forEach[moveArmies(cell, it)]
	}

	private def moveArmies(ServerCell source, ServerCell destination) {
		val maxMoving = LogicHelper.calcMaxMovement(source, destination, configuration)
		val destinationSpace = configuration.maxCellStrength - destination.strength
		val reallyMoving = Math.min(Math.min(maxMoving, source.strength), destinationSpace) as int
		returnif(reallyMoving <= 0)
		destination.strength = destination.strength + reallyMoving
		destination.owner = source.owner.get
		source.strength = source.strength - reallyMoving
		destination.battle = false
	}

	private def findFriendlyCells(ServerCell cell) {
		val cells = board.links.getOutgoingLinks(cell.owner.get, cell).filter[(it.owner.present && it.owner.get == cell.owner.get) || !it.owner.present].toList
		Collections.shuffle(cells)
		cells
	}

	private def updateCellFights(ServerCell cell) {
		returnif(cell.strength == 0)
		returnif(!cell.owner.present)
		val linkedCells = findEnemyLinkedCells(cell)
		returnif(linkedCells.empty)
		linkedCells.forEach[doFight(cell, it)]

	}

	private def doFight(ServerCell source, ServerCell destination) {
		returnif(source.strength == 0)

		val damageFactor = calcDamageFactor(source, destination)
		val attackingArmies = LogicHelper.calcMaxMovement(source, destination, configuration) as int
		source.strength = source.strength - attackingArmies
		val newDestinationStrength = destination.strength - attackingArmies as double * damageFactor
		if(newDestinationStrength < 1.0) {
			val newOwnerStrength = Math.max(1.0, Math.abs(attackingArmies - destination.strength))
			destination.battle = false
			removeLinks(destination)
			swapOwner(source.owner.get, destination, newOwnerStrength)
		} else {
			destination.strength = newDestinationStrength as int
			destination.battle = true
		}
	}

	private def removeLinks(ServerCell cell) {
		returnif(!cell.owner.present)
		val links = board.links.getOutgoingLinks(cell.owner.get, cell)
		links.forEach[board.links.toggleOutgoingLink(cell, it, cell.owner.get)]
	}

	private def swapOwner(ServerPlayer newOwner, ServerCell destination, double newStrength) {
		destination.owner = newOwner
		destination.strength = newStrength as int
	}

	private def findEnemyLinkedCells(ServerCell cell) {
		val cells = board.links.getOutgoingLinks(cell.owner.get, cell).filter[it.owner.present && it.owner.get != cell.owner.get].toList
		Collections.shuffle(cells)
		cells
	}

	def calcDamageFactor(ServerCell source, ServerCell destination) {
		val strength = new IntHolder(0)

		// supporting cells for the attack
		getIncomingLinks(source.owner.get, destination).forEach[strength.value += it.strength]
		strength.value as double / (destination.strength as double ) * 0.5f

	}

	def getIncomingLinks(ServerPlayer player, ServerCell destination) {
		Directions.cachedValues.map[board.getAdjacentCell(destination, it)].filter[board.links.hasLink(it, destination, player)]
	}

	private def shuffleCells() {
		val cells = newArrayList()
		FieldLoop.visitAllFields(board.XSize, board.YSize, [x, y|cells.add(board.getCellAt(x, y))])
		for (var i = 0; i < cells.size; i++) {
			val swapPos = rand.nextInt(cells.size)
			val old = cells.get(i)
			cells.set(i, cells.get(swapPos))
			cells.set(swapPos, old)
		}
		cells
	}

	protected final static String NAME = "distribute.random"

	override getName() {
		NAME
	}

	override getInstance(ServerBoard board, GameConfiguration configuration) {
		return new XBattleDistribute(board, configuration)
	}

}
