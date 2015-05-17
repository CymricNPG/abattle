/**
 *
 */
package net.npg.abattle.communication.network;

import net.npg.abattle.communication.command.ReceiveHandler;

/**
 * @author Cymric
 * 
 */
@SuppressWarnings("rawtypes")
public interface NetworkListener {

	void addHandler(ReceiveHandler handler);

}
