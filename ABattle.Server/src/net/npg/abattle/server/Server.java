/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server;

import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.communication.network.impl.NetworkComponentImpl;

/**
 * Starts the Server. The main purpose of this class is to initialize all Components and start the Network Server, which
 * waits for incoming game requests!
 * 
 * @author spatzenegger
 * 
 */
public abstract class Server {

	/**
	 * Inits the base components.
	 */
	protected void initBaseComponents() {
		final ComponentLookup componentLookup = ComponentLookup.getInstance();
		componentLookup.registerComponent(new NetworkComponentImpl());
	}

	/**
	 * Run the server.
	 * 
	 * @throws ServerException the server exception
	 */
	public void run() throws BaseException {
		initBaseComponents();
		startPreGameServer();
	}

	/**
	 * Start pre game server.
	 * 
	 * @throws ServerException the server exception
	 */
	abstract protected void startPreGameServer() throws BaseException;

	public void stop() throws BaseException {
		stopGameServer();
	}

	abstract protected void stopGameServer();

}
