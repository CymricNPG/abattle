/**
 *
 */
package net.npg.abattle.server.game.impl.fog;

import net.npg.abattle.common.configuration.impl.NamedClass;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerPlayer;

/**
 * @author cymric
 *
 */
public interface Fog extends NamedClass {

	boolean isVisible(ClientCell[][] clientBoard, ServerBoard board, ServerPlayer player, ClientCell cell);

}
