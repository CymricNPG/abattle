/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.error.impl;

import java.util.HashSet;
import java.util.Set;

import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.component.Component;
import net.npg.abattle.common.error.ErrorComponent;
import net.npg.abattle.common.error.ErrorHandler;
import net.npg.abattle.common.error.ErrorInfo;
import net.npg.abattle.common.utils.impl.DisposeableImpl;

/**
 * @author spatzenegger
 */
public class ErrorComponentImpl extends DisposeableImpl implements ErrorComponent {

	private final Set<ErrorHandler> errorHandlers;

	public ErrorComponentImpl() {
		this.errorHandlers = new HashSet<ErrorHandler>();
	}

	@Override
	public synchronized void addError(final Exception e) {
		try {
			if (e == null) {
				sendToAllHandlers(new ErrorInfoImp("Unhandled Error", true, e));
			} else {
				sendToAllHandlers(new ErrorInfoImp(e.getMessage(), true, e));
			}
		} catch (final Exception t) {
			CommonConstants.LOG.error(t.getMessage(), t);
		}
	}

	@Override
	public Class<? extends Component> getInterface() {
		return ErrorComponent.class;
	}

	@Override
	public synchronized void registerErrorHandler(final ErrorHandler handler) {
		assert errorHandlers != null;
		errorHandlers.add(handler);
	}

	private synchronized void sendToAllHandlers(final ErrorInfo message) {
		assert message != null;
		assert errorHandlers != null;
		for (final ErrorHandler handler : errorHandlers) {
			try {
				handler.handleError(message);
			} catch (final Exception e) {
				CommonConstants.LOG.error(e.getMessage(), e);
				errorHandlers.remove(handler);
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		errorHandlers.clear();
	}

	@Override
	public synchronized void removeErrorHandler(final ErrorHandler handler) {
		errorHandlers.remove(handler);
	}

	@Override
	public void addError(final String message, final boolean fatal) {
		sendToAllHandlers(new ErrorInfoImp(message, fatal, null));
	}
}
