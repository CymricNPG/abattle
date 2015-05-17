package net.npg.abattle.server.logic

import java.util.ArrayList
import java.util.List
import net.npg.abattle.common.model.GameConfiguration
import net.npg.abattle.common.model.Player
import net.npg.abattle.common.utils.FieldLoop
import net.npg.abattle.server.model.ServerBoard
import net.npg.abattle.server.model.ServerCell

import static net.npg.abattle.common.utils.Controls.*
import java.util.Collections
import net.npg.abattle.common.utils.Validate

class SimpleDistributeArmies implements Logic {

	private final GameConfiguration configuration

	private final ServerBoard board

	new() {
		configuration = null
		board = null
	}

	private new(ServerBoard board, GameConfiguration configuration) {
		Validate.notNulls(board, configuration)
		this.board = board
		this.configuration = configuration
	}

	override run() {
		Validate.notNulls(board, configuration)
		FieldLoop.visitAllFields(board.XSize, board.YSize, [x, y|board.getCellAt(x, y).battle = false])
		FieldLoop.visitAllFields(board.XSize, board.YSize, [x, y|visitCell(x, y)])
	}

	private def visitCell(int x, int y) {
		val cell = board.getCellAt(x, y)
		doCellMarch(cell)
		LogicHelper.updateCellGrowth(cell, configuration.configuration)
	}

	private def doCellMarch(ServerCell cell) {
		val maxMoveToList = calcMaxToMoveArmies(cell)
		moveArmies(cell, maxMoveToList)
	}

	private def moveArmies(ServerCell cell, List<SimpleMoveDistribution> maxMoveToList) {
		var armyMoved = false
		do {
			armyMoved = false
			for (SimpleMoveDistribution maxMoveTo : maxMoveToList) {
				breakif(cell.strength == 0 || maxMoveTo.maxMoveArmiesTo == 0)
				if(maxMoveTo.hasFight) {
					armyMoved = doFight(cell, maxMoveTo)
				} else {
					armyMoved = doMarch(cell, maxMoveTo)
				}
				if(armyMoved) {
					changeOwner(maxMoveTo, cell.owner.get)
				}
			}
		} while(armyMoved)

	}

	private def doMarch(ServerCell cell, SimpleMoveDistribution maxMoveTo) {
		if(cell.strength > 0 && maxMoveTo.maxMoveArmiesTo > 0 && maxMoveTo.destinationCell.strength < configuration.configuration.maxCellStrength) {
			doSingleMarch(cell, maxMoveTo)
			true
		} else {
			false
		}
	}

	private def doFight(ServerCell cell, SimpleMoveDistribution maxMoveTo) {
		if(cell.strength > 0 && maxMoveTo.maxMoveArmiesTo > 0) {
			if(maxMoveTo.destinationCell.strength == 0) {
				doSingleMarch(cell, maxMoveTo)
				maxMoveTo.hasFight = false
				removeLinks(maxMoveTo.destinationCell)
				maxMoveTo.destinationCell.battle = false
				maxMoveTo.destinationCell.owner = if(cell.owner.isPresent) cell.owner.get else null
			} else {
				doSingleFight(cell, maxMoveTo)
			}
			true
		} else {
			false
		}
	}

	private def doSingleMarch(ServerCell cell, SimpleMoveDistribution maxMoveTo) {
		cell.addStrength(-1)
		maxMoveTo.destinationCell.addStrength(1)
		maxMoveTo.maxMoveArmiesTo--
	}

	private def doSingleFight(ServerCell cell, SimpleMoveDistribution maxMoveTo) {
		cell.addStrength(-1)
		maxMoveTo.destinationCell.addStrength(-1)
		maxMoveTo.maxMoveArmiesTo--
	}

	private def changeOwner(SimpleMoveDistribution maxMoveTo, Player player) {
		val destinationCell = maxMoveTo.destinationCell
		if(destinationCell.strength == 0 && maxMoveTo.hasFight) {
			maxMoveTo.hasFight = false
			removeLinks(destinationCell)
			destinationCell.owner = null
			destinationCell.battle = false
		} else if(!maxMoveTo.hasFight && destinationCell.strength > 0 && !destinationCell.isOwner(player)) {
			destinationCell.owner = player
			destinationCell.battle = false
		} else if(maxMoveTo.hasFight && !destinationCell.isOwner(player)) {
			destinationCell.battle = true
		}
	}

	private def List<SimpleMoveDistribution> calcMaxToMoveArmies(ServerCell source) {
		if(!source.owner.present) {
			return Collections.emptyList
		}
		val directions = board.getLinks().getOutgoingLinks(source.owner.get, source)
		val maxMoveToList = new ArrayList<SimpleMoveDistribution>(6);
		for (ServerCell destination : directions) {
			val gradient = LogicHelper.calcMaxMovement(source, destination, configuration.configuration)
			val hasFight = destination.owner != null && source.owner != destination.owner
			val space = configuration.configuration.getMaxCellStrength() - (if(hasFight) 0 else destination.strength)
			val maxMoveArmiesTo = Math.min(gradient, space)  as int
			if(maxMoveArmiesTo > 0) {
				maxMoveToList.add(new SimpleMoveDistribution(maxMoveArmiesTo, destination, hasFight))
			}
		}
		return maxMoveToList
	}

	private def removeLinks(ServerCell cell) {
		returnif(!cell.owner.present)
		val links = board.links.getOutgoingLinks(cell.owner.get, cell)
		links.forEach[board.links.toggleOutgoingLink(cell, it, cell.owner.get)]
	}

	override getName() {
		NAME
	}

	override getInstance(ServerBoard board, GameConfiguration configuration) {
		return new SimpleDistributeArmies(board, configuration)
	}

	protected final static String NAME = "distribute.simple"

}
