/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.impl;

import net.npg.abattle.common.model.Actor;
import net.npg.abattle.common.utils.Validate;


/**
 * @author spatzenegger
 * 
 */
public class ActorImpl extends IDElementImpl implements Actor {

	private final String name;

	public ActorImpl(final String name) {
		super();
		Validate.notBlank(name);
		this.name = name;
	}

	public ActorImpl(final String name, final int id) {
		super(id);
		Validate.notBlank(name);
		this.name = name;
	}

	@Override
	public String getName() {
		assert name != null;
		return name;
	}

}
