/**
 *
 */
package net.npg.abattle.common.error.impl;

import net.npg.abattle.common.error.ErrorInfo;
import net.npg.abattle.common.utils.Validate;

import com.google.common.base.Optional;

/**
 * @author cymric
 *
 */
public class ErrorInfoImp implements ErrorInfo {

	private final String message;
	private final boolean fatal;
	private final Optional<Exception> exception;

	public ErrorInfoImp(final String message, final boolean fatal, final Exception exception) {
		Validate.notNull(message);
		this.message = message;
		this.fatal = fatal;
		this.exception = Optional.fromNullable(exception);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Optional<Exception> getException() {
		return exception;
	}

	@Override
	public boolean isFatal() {
		return fatal;
	}

}
