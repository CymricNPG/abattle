/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.error;

import net.npg.abattle.common.component.Component;
import net.npg.abattle.common.component.Reusable;

/**
 * Handling asynchron error messages (e.g. from threads or the network)
 *
 * @author spatzenegger
 *
 */
public interface ErrorComponent extends Component, Reusable {

	public void addError(Exception e);

	public void addError(String message, boolean fatal);

	public void registerErrorHandler(ErrorHandler handler);

	public void removeErrorHandler(ErrorHandler handler);
}
