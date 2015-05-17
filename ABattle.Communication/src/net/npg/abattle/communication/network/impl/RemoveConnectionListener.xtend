package net.npg.abattle.communication.network.impl

import com.esotericsoftware.kryonet.Listener
import com.esotericsoftware.kryonet.Connection
import static net.npg.abattle.common.utils.Controls.*
import net.npg.abattle.common.utils.Validate
import com.esotericsoftware.kryonet.rmi.ObjectSpace

class RemoveConnectionListener extends Listener {

	private final ObjectSpace objectSpace

	new(ObjectSpace objectSpace) {
		Validate.notNull(objectSpace)
		this.objectSpace = objectSpace

	}

	override void disconnected(Connection connection) {
		returnif(connection == null)
		objectSpace.removeConnection(connection);
	}
}
