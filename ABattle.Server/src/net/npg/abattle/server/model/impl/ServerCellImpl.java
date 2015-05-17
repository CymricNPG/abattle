/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.model.impl;

import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.impl.CellImpl;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.server.model.ServerCell;

/**
 * @author cymric
 * 
 */
public class ServerCellImpl extends CellImpl implements ServerCell {

	public ServerCellImpl(final int id, final IntPoint boardCoordinate, final int height, final CellTypes cellType, final CheckModelElement checker) {
		super(id, boardCoordinate, height, cellType, checker);
	}

	@Override
	public void accept(final ModelVisitor visitor) {
		Validate.notNull(visitor);
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
		final ServerCellImpl other = (ServerCellImpl) obj;
		if (getId() != other.getId()) {
			return false;
		}
		if (!getBoardCoordinate().equals(other.getBoardCoordinate())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public void addStrength(final int addStrnegth) {
		this.setStrength(this.getStrength() + addStrnegth);
	}
}
