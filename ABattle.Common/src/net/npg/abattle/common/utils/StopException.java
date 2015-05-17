/**
 *
 */
package net.npg.abattle.common.utils;

/**
 * @author cymric
 *
 */
public class StopException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -4160086433763573965L;

	public StopException(final Throwable stoppedBy) {
		super(stoppedBy);
	}

	public StopException() {
		super("Application stopped");
	}

}
