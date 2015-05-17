package net.npg.abattle.communication.network.impl

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import static net.npg.abattle.common.utils.Controls.*
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.ReceiveHandler

class KyroListenerAdapter extends Listener {

	ReceiveHandler handler

	new(ReceiveHandler handler) {
		Validate.notNull(handler)
		this.handler = handler
	}

	override received(Connection connection, Object object) {
		returnif(object == null)
		returnif(connection == null)
		if(handler.canHandle(object)) {
			handler.handle(object, connection)
		}
	}
}
