package net.npg.abattle.communication.command.impl

import java.util.List
import net.npg.abattle.communication.command.CommandType
import net.npg.abattle.communication.command.GameCommand

@Data package class CommandStore {
	CommandType commandType
	GameCommand command
	List<Integer> destination
}
