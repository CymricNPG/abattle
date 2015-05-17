package net.npg.abattle.client.commands

import com.google.common.base.Optional
import net.npg.abattle.client.ClientConstants
import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.commands.ServerReplyCommand

class ServerReplyProcessor implements CommandProcessor<ServerReplyCommand>{


	override execute(ServerReplyCommand command,int destination) {
		ClientConstants.LOG.info("Message from server:"+command.result.errorMessage) // TODO
		Optional.absent
	}
	
}