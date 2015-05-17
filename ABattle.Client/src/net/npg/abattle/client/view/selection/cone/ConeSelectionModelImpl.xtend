package net.npg.abattle.client.view.selection.cone

import net.npg.abattle.client.view.selection.twocells.SelectionModelImpl
import net.npg.abattle.common.hex.HexBase
import net.npg.abattle.common.model.client.ClientBoard
import net.npg.abattle.common.utils.FloatPoint

import static net.npg.abattle.common.utils.Controls.*
import net.npg.abattle.common.model.client.ClientPlayer

public class ConeSelectionModelImpl extends SelectionModelImpl {

	HexBase hexBase

	ClientBoard board

	new(ClientBoard board, HexBase hexBase, ClientPlayer localPlayer) {
		super(board, hexBase,localPlayer)
		this.hexBase = hexBase;
		this.board = board
	}

	synchronized override dragSelection(FloatPoint screenCoordinate) {
		returnif(!inSelectionMode)
		if(hexBase.getCellByPoint(screenCoordinate).equals(startCursor.boardCoordinate)) {
			endCursor.hide
			return
		}
		val direction = hexBase.getDirections(startCursor.worldCoordinate, screenCoordinate)
		val endBoardCoordinate = direction.getAdjacentCoordinateTo(startCursor.boardCoordinate)
		if(endCursor.setBoardCoordinate(endBoardCoordinate)) {
			endCursor.show
		} else {
			endCursor.hide
		}
	}

	synchronized override void endSelection(FloatPoint coordinate) {
		if(hexBase.getCellByPoint(coordinate).equals(startCursor.boardCoordinate)) {
			endCursor.worldCoordinate = coordinate
		}
		resetSelection();
	}

}
