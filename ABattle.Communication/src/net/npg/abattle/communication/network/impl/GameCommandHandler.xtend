package net.npg.abattle.communication.network.impl

import com.esotericsoftware.kryonet.Connection
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandQueue
import net.npg.abattle.communication.command.GameCommand
import net.npg.abattle.communication.command.ReceiveHandler
import java.util.Collections

class GameCommandHandler implements ReceiveHandler<GameCommand> {

	CommandQueue queue

	new(CommandQueue queue) {
		Validate.notNulls(queue)
		this.queue = queue
	}

	override handle(GameCommand command, Connection connection) {
		Validate.notNulls(command)
		queue.addCommand(command, queue.handledType, Collections.emptyList)
	}
	
	override canHandle(Object object) {
		object instanceof GameCommand
	}


}
