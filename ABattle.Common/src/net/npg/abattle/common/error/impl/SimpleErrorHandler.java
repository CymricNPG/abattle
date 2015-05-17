/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.error.impl;

import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.error.ErrorHandler;
import net.npg.abattle.common.error.ErrorInfo;

/**
 * @author spatzenegger
 *
 */
public class SimpleErrorHandler implements ErrorHandler {

	@Override
	public void handleError(final ErrorInfo message) {
		if (message.getException().isPresent()) {
			CommonConstants.LOG.error(message.getMessage(), message.getException());
		} else {
			CommonConstants.LOG.error(message.getMessage());
		}
	}

}
