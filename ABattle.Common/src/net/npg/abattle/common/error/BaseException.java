/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.error;

/**
 * @author Cymric
 * 
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;

	private final ExceptionCode code;

	/**
	 * Instantiates a new server exception.
	 * 
	 * @param code the code
	 */
	public BaseException(final ExceptionCode code) {
		super(code.getMessage());
		this.code = code;
	}

	/**
	 * Instantiates a new server exception.
	 * 
	 * @param code the code
	 * @param message the message
	 */
	public BaseException(final ExceptionCode code, final String message) {
		super(code.getMessage() + " : " + message);
		this.code = code;
	}

	/**
	 * Instantiates a new server exception.
	 * 
	 * @param code the code
	 * @param message the message
	 * @param cause the cause
	 */
	public BaseException(final ExceptionCode code, final String message, final Throwable cause) {
		super(code.getMessage() + " : " + message, cause);
		this.code = code;
	}

	public BaseException(final ExceptionCode code, final Throwable cause) {
		super(code.getMessage(), cause);
		this.code = code;
	}

	public ExceptionCode getCode() {
		return code;
	}
}
