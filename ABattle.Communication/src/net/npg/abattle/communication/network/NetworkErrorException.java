/**
 * 
 */
package net.npg.abattle.communication.network;

/**
 * @author Cymric
 * 
 */
public class NetworkErrorException extends Exception {

	private static final long serialVersionUID = 1L;

	public NetworkErrorException(final String detailMessage) {
		super(detailMessage);
	}

}
