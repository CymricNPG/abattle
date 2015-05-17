/**
 *
 */
package net.npg.abattle.server.model.clientfacade;

import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.server.model.ServerPlayer;

import com.google.common.base.Optional;

/**
 * @author cymric
 * 
 */
public interface PlayersFacade {

	Optional<ClientPlayer> getPlayer(ServerPlayer player);
}
