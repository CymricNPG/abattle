package net.npg.abattle.server.game.impl.fog

import net.npg.abattle.common.hex.Directions
import net.npg.abattle.common.model.client.ClientCell
import net.npg.abattle.server.model.ServerBoard
import net.npg.abattle.server.model.ServerPlayer
import static net.npg.abattle.common.utils.Controls.*
class SimpleFog implements Fog {

	protected static final	String NAME = "simplefog"

	override getName() {
		NAME
	}

	override isVisible(ClientCell[][] clientBoard, ServerBoard board,ServerPlayer player, ClientCell cell) {
		val x = cell.boardCoordinate.x
		val y = cell.boardCoordinate.y
		for (direction : Directions.cachedValues) {

			val newX = x + direction.getXadd();
			val newY = y + direction.getYadd(x);
			continueif(isOutsideBoard(board,newX, newY))
			val testCell = clientBoard.get(newX).get(newY)
			if(testCell.getOwner().isPresent() && player.getId() == testCell.getOwner().get().getId()) {
				return true
			}
		}
		return false
	}

	def boolean isOutsideBoard( ServerBoard board, int newX,  int newY) {
		return newX < 0 || newY < 0 || newX >= board.getXSize() || newY >= board.getYSize();
	}
}