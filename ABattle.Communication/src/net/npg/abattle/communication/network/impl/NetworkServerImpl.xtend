package net.npg.abattle.communication.network.impl

import com.esotericsoftware.kryonet.Listener
import com.esotericsoftware.kryonet.Server
import com.esotericsoftware.kryonet.rmi.ObjectSpace
import net.npg.abattle.common.configuration.NetworkConfigurationData
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.common.utils.impl.DisposeableImpl
import net.npg.abattle.communication.CommunicationConstants
import net.npg.abattle.communication.command.ReceiveHandler
import net.npg.abattle.communication.network.NetworkListener
import net.npg.abattle.communication.network.NetworkServer
import net.npg.abattle.communication.network.NetworkService
import net.npg.abattle.common.utils.MyMap
import net.npg.abattle.common.utils.MyHashMap
import java.util.Set

class NetworkServerImpl extends DisposeableImpl implements NetworkServer, NetworkListener {

	private final Server server

	private final ObjectSpace objectSpace

	private final NetworkConfigurationData configuration

	private volatile long sentBytes
	private volatile long lastLog
	private MyMap<Integer, NetworkService> serviceMap

	private Set<Listener> listeners

	new(NetworkConfigurationData configuration) {
		Validate.notNull(configuration)
		this.configuration = configuration
		objectSpace = new ObjectSpace
		server = new MyServer(objectSpace)
		ObjectSpace.registerClasses(server.kryo)
		SerializerHelper.initializeKryo(server.kryo)
		sentBytes = 0L
		lastLog = System.currentTimeMillis
		serviceMap = new MyHashMap()
		listeners = newHashSet
	}

	override dispose() {
		server.stop
		objectSpace.close
	}

	override getService(int id) {
		return serviceMap.get(id)
	}

	override registerService(NetworkService service, int id) {
		Validate.notNull(service);
		objectSpace.register(id, service)
		serviceMap.put(id, service)
	}

	override start() {
		server.start
		server.bind(configuration.port, configuration.port)
	}

	override sendTo(Object object, int clientId) {
		var count = 0
		var connection = server.connections.filter[it instanceof MyConnection].filter[(it as MyConnection).clientId.present].findFirst[
			(it as MyConnection).clientId.get == clientId]

		if(connection == null) {
			return false
		}

		if(CommunicationConstants.DEBUG_ENABLED) {
			CommunicationConstants.LOG.debug("Send object to:" + clientId)
		}
		sentBytes += connection.sendTCP(object) // register class at: SerializerHelper
		count ++

		if(count == 0) {
			CommunicationConstants.LOG.error(clientId + " failed to send object:" + object)
			return false
		}
		logSentBytes
		return true
	}

	def logSentBytes() {
		val timeDiff = System.currentTimeMillis - lastLog
		if(timeDiff > 5000L) {
			val kb_s = sentBytes / 5.0 / 1024
			sentBytes = 0L
			lastLog = System.currentTimeMillis
			CommunicationConstants.LOG.debug("Sent KiloBytes per Second:" + kb_s as int)
		}
	}

	@SuppressWarnings("rawtypes")
	synchronized override addHandler(ReceiveHandler handler) {
		Validate.notNull(handler)
		val listener = new KyroListenerAdapter(handler)
		listeners.add(listener)
		server.addListener(listener)
	}

	synchronized override removeHandlers() {
		listeners.forEach[server.removeListener(it)]
		listeners.clear
	}

}
