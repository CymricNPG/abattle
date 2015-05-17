package net.npg.abattle.server.game.impl.terrain

import net.npg.abattle.common.IDGenerator
import net.npg.abattle.common.model.CellTypes
import net.npg.abattle.common.model.CheckModelElement
import net.npg.abattle.common.utils.FieldLoop
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.server.model.impl.ServerBoardImpl
import net.npg.abattle.server.model.impl.ServerCellImpl
import org.eclipse.xtext.xbase.lib.Functions.Function2

final class BoardHelper {
	static def fillBoard(ServerBoardImpl board, IntPoint size,CheckModelElement checker, Function2<Integer,Integer,Integer> heightFunction) {
		FieldLoop.visitAllFields(size.x, size.y,
			[ x, y |
				val id = IDGenerator.generateId();
				val IntPoint boardCoordinate = IntPoint.from(x, y);
				val int height = heightFunction.apply(x,y)
				val CellTypes cellType = CellTypes.PLAIN;
				val ServerCellImpl cell = new ServerCellImpl(id, boardCoordinate, height, cellType, checker);
				cell.setBattle(false);
				cell.setStrength(0);
				cell.setOwner(null);
				board.cellAt = cell
			])
	}
}