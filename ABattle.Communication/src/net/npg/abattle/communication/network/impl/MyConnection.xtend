package net.npg.abattle.communication.network.impl

import com.esotericsoftware.kryonet.Connection
import com.google.common.base.Optional

class MyConnection extends Connection {
	
	private Optional<Integer> clientId
	
	new() {
		super()
		clientId = Optional.absent
	}
	
	def setClientId(int clientId) {
		this.clientId = Optional.of(clientId)
	}
	
	def getClientId() {
		this.clientId
	}
	
}
