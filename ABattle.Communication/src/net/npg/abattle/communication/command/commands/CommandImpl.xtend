package net.npg.abattle.communication.command.commands

import net.npg.abattle.common.utils.TransferData
import net.npg.abattle.communication.command.GameCommand

@TransferData
class CommandImpl implements GameCommand {

	public boolean dropable

	public int game

	override getGame() {
		game
	}

	override isDropable() {
		dropable
	}

}