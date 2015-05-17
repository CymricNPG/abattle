/**
 *
 */
package net.npg.abattle.communication.network;

import java.net.InetAddress;

import net.npg.abattle.common.utils.Disposeable;
import net.npg.abattle.communication.network.data.NetworkGameInfo;
import net.npg.abattle.communication.service.ServerService;

import com.google.common.base.Optional;

/**
 * @author spatzenegger
 * 
 */
public interface NetworkClient extends NetworkListener, Disposeable {

	/**
	 * discover and query host for game
	 */
	Optional<NetworkGameInfo> discoverHostAndGame();

	void connectToLocalhost();

	void connectTo(InetAddress address);

	/**
	 * 
	 * tries to locate server service
	 * 
	 * @deprecated only for tests needed
	 * @return true if found
	 */
	@Deprecated
	boolean discoverHost();

	void clearFoundHost();

	/**
	 * @return if dicover was successful, contains the game
	 * 
	 * 
	 */
	Optional<NetworkGameInfo> getFoundGame();

	/**
	 * @return if dicover was successful, contains the game
	 * 
	 */
	Optional<ServerService> getServerService();

	/**
	 * connection is dead
	 */
	boolean isDead();

	/**
	 * send a object to the server
	 * 
	 */
	void send(Object object);

	/**
	 * do the login on the server, always needed, to map the kryonet connection on the server to the correct client id
	 * 
	 * @param name name of the user
	 */
	void doLogin(String name);

	void ping();
}
