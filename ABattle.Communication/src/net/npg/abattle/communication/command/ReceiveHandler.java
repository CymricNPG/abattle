/**
 * 
 */
package net.npg.abattle.communication.command;

import com.esotericsoftware.kryonet.Connection;

/**
 * 
 * @author Cymric
 * 
 */
public interface ReceiveHandler<T> {

	boolean canHandle(Object object);

	void handle(T object, Connection connection);
}
