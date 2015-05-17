/**
 *
 */
package net.npg.abattle.server.model;

import net.npg.abattle.common.model.Board;

/**
 * @author cymric
 * 
 */
public interface BoardInitializedNotifier {

	public void boardCreated(Board<ServerPlayer, ServerCell, ServerLinks> board);
}
