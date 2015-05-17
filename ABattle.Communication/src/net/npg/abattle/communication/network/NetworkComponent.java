/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.communication.network;

import net.npg.abattle.common.component.Component;

/**
 * @author spatzenegger
 * 
 */
public interface NetworkComponent extends Component {

	NetworkClient createClient();

	NetworkServer createServer();

	void removeServerHandlers();

	boolean isServerRunning();
}
