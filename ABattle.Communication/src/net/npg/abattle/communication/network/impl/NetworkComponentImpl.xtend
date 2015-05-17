package net.npg.abattle.communication.network.impl

import java.util.Set
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.component.ComponentType
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.configuration.NetworkConfigurationData
import net.npg.abattle.common.utils.Disposeable
import net.npg.abattle.common.utils.impl.DisposeableImpl
import net.npg.abattle.communication.network.NetworkClient
import net.npg.abattle.communication.network.NetworkComponent
import net.npg.abattle.communication.network.NetworkServer
import static extension net.npg.abattle.common.utils.IterableExtensions.*
import net.npg.abattle.communication.CommunicationConstants

@ComponentType
@SuppressWarnings("rawtypes")
class NetworkComponentImpl extends DisposeableImpl implements NetworkComponent {

	final NetworkConfigurationData configuration

	final Set<Disposeable> clients

	NetworkServer server

	new() {
		this.configuration = ComponentLookup.instance.getComponent(ConfigurationComponent).networkConfiguration
		this.clients = newHashSet
	}

	synchronized override dispose() {
		super.dispose();
		clients.filter[!disposed].forEach[dispose]
		clients.clear
		if(server != null && !server.disposed) {
			server.dispose
		}
		server = null
	}

	synchronized override NetworkClient createClient() {
		val client = new NetworkClientImpl(configuration)
		clients += client
		clients.removeConditional[disposed]
		client
	}

	synchronized  override void removeServerHandlers() {
		if(server != null && !server.disposed) {
			server.removeHandlers();
		}
	}

	synchronized  override public NetworkServer createServer() {
		if(server != null && !server.disposed) {
			return server
		}
		server = new NetworkServerImpl(configuration)
		try {
		server.start
		} catch(Exception e) {
			CommunicationConstants.LOG.error(e.message,e)
			server.dispose
			server = null
			throw e
		}
		server
	}

	override isServerRunning() {
		server != null  && !server.disposed
	}

}
