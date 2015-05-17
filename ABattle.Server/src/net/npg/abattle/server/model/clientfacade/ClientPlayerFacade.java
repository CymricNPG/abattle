/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.model.clientfacade;

import net.npg.abattle.common.model.Color;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.model.impl.IDElementImpl;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.server.model.ServerPlayer;

/**
 * @author Cymric
 * 
 */
public class ClientPlayerFacade extends IDElementImpl implements ClientPlayer {

	private final ServerPlayer player;

	public ClientPlayerFacade(final ServerPlayer player) {
		super(player.getId());
		Validate.notNull(player);
		this.player = player;
	}

	@Override
	public void accept(final ModelVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Color getColor() {
		return player.getColor();
	}

	@Override
	public String getName() {
		return player.getName();
	}

	@Override
	public int getStrength() {
		return player.getStrength();
	}

	@Override
	public void setStrength(final int strength) {
		throw new RuntimeException("Facade shouldnt be called.");
	}

	@Override
	public void setColor(final Color color) {
		throw new RuntimeException("Facade shouldnt be called.");
	}

}
