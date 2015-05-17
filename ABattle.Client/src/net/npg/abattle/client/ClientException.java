/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client;

/**
 * The Class ClientException.
 * 
 * @author spatzenegger
 */
public class ClientException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new client exception.
	 */
	public ClientException() {
	}

	/**
	 * Instantiates a new client exception.
	 * 
	 * @param message the message
	 */
	public ClientException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new client exception.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public ClientException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new client exception.
	 * 
	 * @param cause the cause
	 */
	public ClientException(final Throwable cause) {
		super(cause);
	}

}
