/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.error;

/**
 * The Class NotAvailableException.
 * 
 * @author Cymric
 */
public class NotAvailableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new not available exception.
	 */
	public NotAvailableException() {
		super("Implementation of this function is not available.");
	}

}
