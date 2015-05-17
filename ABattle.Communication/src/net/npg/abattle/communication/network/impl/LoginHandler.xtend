package net.npg.abattle.communication.network.impl

import net.npg.abattle.communication.command.commands.LoginCommand
import net.npg.abattle.communication.command.ReceiveHandler
import net.npg.abattle.communication.service.ServerService
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.commands.LoginResultCommand
import com.esotericsoftware.kryonet.Connection
import static net.npg.abattle.common.utils.Controls.*

/**
 * connects a connection with a client id, registered at the server, waiting for the login from the client
 */
class LoginHandler implements ReceiveHandler<LoginCommand> {

	ServerService service

	new(ServerService service) {
		Validate.notNull(service)
		this.service = service
	}

	override handle(LoginCommand object, Connection connection) {
		returnif(object == null)
		returnif(!(connection instanceof MyConnection))
		val result = service.login(object.name)
		if(result.success) {
			(connection as MyConnection).setClientId(result.clientInfo.id)
		}
		connection.sendTCP(new LoginResultCommand(result))
	}
	
	override canHandle(Object object) {
		object instanceof LoginCommand
	}

}
