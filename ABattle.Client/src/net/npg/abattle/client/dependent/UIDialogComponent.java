/**
 *
 */
package net.npg.abattle.client.dependent;

import net.npg.abattle.common.component.Component;
import net.npg.abattle.common.component.Reusable;

/**
 * @author cymric
 *
 */
public interface UIDialogComponent extends Component, Reusable {
	RequestHandler getRequestHandler();

	MultiplayerScreen getMultiplayerScreen();

}
