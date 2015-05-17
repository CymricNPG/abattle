/**
 * 
 */
package net.npg.abattle.server.game.impl.fog;

import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerPlayer;

/**
 * @author cymric
 *
 */
public class NoFog implements Fog {

	protected static final String NAME = "nofog";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isVisible(final ClientCell[][] clientBoard, final ServerBoard board, final ServerPlayer player, final ClientCell cell) {
		return true;
	}
}
