package net.npg.abattle.client.lan.impl

import net.npg.abattle.common.model.BoardCreator
import net.npg.abattle.common.model.client.ClientBoard
import java.util.Collection
import net.npg.abattle.common.model.Player
import net.npg.abattle.common.error.BaseException
import net.npg.abattle.common.utils.IntPoint
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.common.model.CheckModelElement
import net.npg.abattle.common.model.client.impl.ClientBoardImpl

class EmptyBoardCreator implements BoardCreator<ClientBoard> {

	IntPoint size

	CheckModelElement checker

	ClientBoardImpl board

	new(IntPoint size, CheckModelElement checker) {
		Validate.notNull(size)
		Validate.notNull(checker)
		this.size = size
		this.checker = checker
		this.board = new ClientBoardImpl(size.x, size.y)
	}

	override getBoard() {
		return board
	}

	override run(Collection<? extends Player> players) throws BaseException {
		//nothing to do
	}

}
