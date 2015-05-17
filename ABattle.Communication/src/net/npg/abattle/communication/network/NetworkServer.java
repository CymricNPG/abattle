/**
 *
 */
package net.npg.abattle.communication.network;

import net.npg.abattle.common.utils.Disposeable;

/**
 * @author Cymric
 * 
 */
public interface NetworkServer extends NetworkListener, Disposeable {

	void registerService(final NetworkService service, final int id);

	<N extends NetworkService> N getService(int id);

	void start();

	boolean sendTo(Object object, int clientId);

	void removeHandlers();

}
