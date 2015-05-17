/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.impl;

import net.npg.abattle.common.IDGenerator;
import net.npg.abattle.common.model.IDElement;

/**
 * The Class IDElementImpl.
 * 
 * @author spatzenegger
 */
public class IDElementImpl implements IDElement {

	private final long creationTime;
	private final int id;

	/**
	 * Instantiates a new iD element impl.
	 */
	public IDElementImpl() {
		super();
		this.id = IDGenerator.generateId();
		creationTime = System.currentTimeMillis();
	}

	/**
	 * Instantiates a new iD element impl.
	 * 
	 * @param id the id
	 */
	public IDElementImpl(final int id) {
		super();
		this.id = id;
		creationTime = System.currentTimeMillis();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final IDElementImpl other = (IDElementImpl) obj;
		return id == other.id;
	}

	@Override
	public long getCreationTime() {
		return creationTime;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
}
