package net.npg.abattle.communication.network.impl

import com.google.common.base.Optional
import net.npg.abattle.common.error.BaseException
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.CommunicationConstants
import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.GameCommand
import net.npg.abattle.communication.network.NetworkClient

/**
 * sends all messages to the server, only one client can exists in a application
 * if any error happens, an exception is thrown!
 */
class NetworkClientProcessor implements CommandProcessor<GameCommand> {

	NetworkClient client

	new(NetworkClient client) {
		Validate.notNull(client)
		this.client = client
	}

	override execute(GameCommand command, int destination) {
		if(client.dead) {
			throw new BaseException(CommunicationConstants.NETWORK_SERVER_UNREACHABLE, "Server communication is dead.")
		}
		try {
			client.send(command)
			Optional.absent
		} catch(Exception e) {
			if(e instanceof BaseException) {
				throw e
			} else {
				throw new BaseException(CommunicationConstants.NETWORK_SERVER_UNREACHABLE, "Server communication failed.", e)
			}
		}
	}
}
