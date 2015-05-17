package net.npg.abattle.communication.network.impl

import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Server
import com.esotericsoftware.kryonet.rmi.ObjectSpace
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.CommunicationConstants

class MyServer extends Server {
	
	private final ObjectSpace objectSpace
	
	new(ObjectSpace objectSpace) {
		Validate.notNull(objectSpace)
		this.objectSpace = objectSpace
		addListener(new RemoveConnectionListener(objectSpace))
	}
	
	override Connection newConnection() {
		val connection = new MyConnection
		objectSpace.addConnection(connection)
		connection
	}
	
	override start() {
		CommunicationConstants.LOG.debug("Start Server")
		super.start
	}
}
