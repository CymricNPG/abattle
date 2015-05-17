/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.client.impl;

import net.npg.abattle.common.model.Color;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.model.impl.PlayerImpl;

/**
 * @author cymric
 * 
 */
public class ClientPlayerImpl extends PlayerImpl implements ClientPlayer {

	private int strength;

	public ClientPlayerImpl(final String name, final int id, final Color color) {
		super(name, id, color);
	}

	@Override
	public void accept(final ModelVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ClientPlayerImpl other = (ClientPlayerImpl) obj;
		if (getId() != other.getId()) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public int getStrength() {
		return strength;
	}

	@Override
	public void setStrength(final int strength) {
		this.strength = strength;
	}
}
