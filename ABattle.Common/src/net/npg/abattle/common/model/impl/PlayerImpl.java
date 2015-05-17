/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.impl;

import net.npg.abattle.common.model.Color;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.utils.Validate;

/**
 * @author cymric
 * 
 */
public abstract class PlayerImpl extends ActorImpl implements Player {

	private Color color;

	public PlayerImpl(final String name, final Color color) {
		super(name);
		Validate.notNulls(color);
		this.color = color;
	}

	public PlayerImpl(final String name, final int id, final Color color) {
		super(name, id);
		Validate.notNulls(color);
		this.color = color;
	}

	@Override
	public Color getColor() {
		assert color != null;
		return color;
	}

	public void setColor(final Color color) {
		Validate.notNulls(color);
		this.color = color;
	}
}
