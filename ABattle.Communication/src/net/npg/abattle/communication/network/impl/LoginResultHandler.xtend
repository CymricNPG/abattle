package net.npg.abattle.communication.network.impl

import net.npg.abattle.common.model.client.ClientIdRegister
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.ReceiveHandler
import net.npg.abattle.communication.command.commands.LoginResultCommand
import com.esotericsoftware.kryonet.Connection

/**
 * receive a login result
 */
class LoginResultHandler implements ReceiveHandler<LoginResultCommand> {

	ClientIdRegister register

	new(ClientIdRegister register) {
		Validate.notNull(register)
		this.register = register
	}

	override handle(LoginResultCommand result, Connection connection) {
		if(result.result.success) {
			val id = result.result.clientInfo.id
			register.setClientId(id)
			Thread.currentThread.name = "Client:"+id
		} else {
			register.setFailed(result.result.errorMessage)
		}
	}

	override canHandle(Object object) {
		object instanceof LoginResultCommand
	}

}
