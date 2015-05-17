package net.npg.abattle.common.error;

import com.google.common.base.Optional;

/**
 * @author cymric
 *
 */
public interface ErrorInfo {

	String getMessage();

	Optional<Exception> getException();

	/**
	 * if true -> stop all current activities
	 */
	boolean isFatal();
}
