/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.model.impl;

import net.npg.abattle.common.model.Color;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.impl.PlayerImpl;
import net.npg.abattle.server.model.ServerPlayer;

/**
 * The Class ServerPlayerImpl.
 * 
 * @author cymric
 */
public class ServerPlayerImpl extends PlayerImpl implements ServerPlayer {

	private final boolean computer;
	private int strength;

	/**
	 * Instantiates a new server player impl.
	 * 
	 * @param name the name
	 * @param color the color
	 */
	public ServerPlayerImpl(final String name, final Color color, final boolean isComputer) {
		super(name, color);
		this.computer = isComputer;
		strength = 0;
	}

	/**
	 * Instantiates a new server player impl.
	 * 
	 * @param name the name
	 * @param id the id
	 * @param color the color
	 */
	public ServerPlayerImpl(final String name, final int id, final Color color, final boolean isComputer) {
		super(name, id, color);
		this.computer = isComputer;
		strength = 0;
	}

	/**
	 * Accept.
	 * 
	 * @param visitor the visitor
	 */
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
		final ServerPlayerImpl other = (ServerPlayerImpl) obj;
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
	public boolean isComputer() {
		return computer;
	}

	@Override
	public int getStrength() {
		return this.strength;
	}

	@Override
	public void setStrength(final int strength) {
		this.strength = strength;
	}

	@Override
	public String toString() {
		return "ServerPlayerImpl [computer=" + computer + ", strength=" + strength + ", isComputer()=" + isComputer() + ", getStrength()=" + getStrength()
				+ ", getName()=" + getName() + ", getCreationTime()=" + getCreationTime() + ", getId()=" + getId() + "]";
	}

}
